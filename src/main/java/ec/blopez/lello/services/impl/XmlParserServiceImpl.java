package ec.blopez.lello.services.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ec.blopez.lello.domain.*;
import ec.blopez.lello.services.XmlParserService;
import ec.blopez.lello.utils.XMLAttributes;
import ec.blopez.lello.utils.XMLKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Benjamin Lopez on 31/01/2017.
 */
@Service
public class XmlParserServiceImpl implements XmlParserService {

    private final String escoSkillsPath;

    private final static Logger LOG = LoggerFactory.getLogger(XmlParserServiceImpl.class);

    @Autowired
    public XmlParserServiceImpl(@Value("${esco.files.skills.production}") final String escoSkillsPathProduction,
                                @Value("${esco.files.skills.development}") final String escoSkillsPathDevelopment,
                                @Value("${lello.environment}") final String environment){
        this.escoSkillsPath = "PRODUCTION".equals(environment)? escoSkillsPathProduction : escoSkillsPathDevelopment;
    }

    @Override
    public Map<String, Competence> load2(){
        final Map<String, Competence> result = Maps.newHashMap();
        final Map<String, Competence> mapByUri = Maps.newHashMap();
        try {
            final JAXBContext jc = JAXBContext.newInstance(XMLParserMainClass.class);
            final Unmarshaller unmarshaller = jc.createUnmarshaller();
            final XMLParserMainClass xmlFile = (XMLParserMainClass) unmarshaller.unmarshal(new File(escoSkillsPath));
            for(Competence competence : xmlFile.getSkills()){
                result.put(competence.getIdentifier(), competence);
                mapByUri.put(competence.getUri(), competence);
            }
            for(Relationship relationship : xmlFile.getRelationships()){
                final Competence child = mapByUri.get(relationship.getChildUri());
                final Competence parent = mapByUri.get(relationship.getParentUri());
                if((child == null) || (parent == null)) continue;
                final String parentIdentifier = parent.getIdentifier();
                final String parentUri = parent.getUri();
                final String childIdentifier = child.getIdentifier();
                final String childUri = child.getUri();
                if(childIdentifier != null)     parent.addChildIdentifier(childIdentifier);
                if(childUri != null)            parent.addChildUri(childUri);
                if(parentIdentifier != null)    child.addParentIdentifier(parentIdentifier);
                if(parentUri != null)           child.addParentUri(parentUri);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Competence> load() {
        try {
            final File inputFile = new File(escoSkillsPath);
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            LOG.info("Parsing and normalizing XML file: " + escoSkillsPath);
            final Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            LOG.info("Parsing and normalization of file finished.");
            LOG.info("Parsing each entry.");
            final NodeList exportNode = doc.getElementsByTagName(XMLKeys.EXPORT);
            final NodeList thesaurusNode = doc.getElementsByTagName(XMLKeys.THESAURUS);
            final Element thesaurusElement = (Element) thesaurusNode.item(0);
            final NodeList thesaurusConcepts = thesaurusElement.getElementsByTagName(XMLKeys.THESAURUS_CONCEPT);
            final Map<String, Competence> result = Maps.newHashMap();
            for(int i = 0; i < thesaurusConcepts.getLength(); i++){
                final Competence competence = parseSkill((Element) thesaurusConcepts.item(i));
                result.put(competence.getIdentifier(), competence);
            }
            return result;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOG.error("Error trying to load default XML File: " + escoSkillsPath, e);
        }
        return null;
    }

    private Skill parseSkill(final Element el){
        final Skill skill = new Skill();
        skill.setIdentifier(getFirstValue(el, XMLKeys.IDENTIFIER));
        skill.setUri(getFirstValue(el, XMLKeys.URI));
        skill.setStatus(getFirstValue(el, XMLKeys.STATUS));
        skill.setTopConcept(Boolean.parseBoolean(getFirstValue(el, XMLKeys.TOP_CONCEPT)));

        final List<String> types = Lists.newArrayList();
        final NodeList typesNode = el.getElementsByTagName(XMLKeys.TYPE);
        for(int i = 0; i < typesNode.getLength(); i++) types.add(typesNode.item(i).getTextContent());
        skill.setTypes(types);


        final NodeList preferredTermNode = el.getElementsByTagName(XMLKeys.PREFERRED_TERM);
        if((preferredTermNode != null) && (preferredTermNode.getLength() > 0)) {
            final Element preferredTermElement = (Element) preferredTermNode.item(0);
            //skill.setPreferredTerm(getLexicalValues(preferredTermElement.getElementsByTagName(XMLKeys.LEXICAL_VALUE)));
        }

        final NodeList simpleNonPreferrredTermNode = el.getElementsByTagName(XMLKeys.SIMPLE_NON_PREFERRED_TERM);
        if((simpleNonPreferrredTermNode != null) && (simpleNonPreferrredTermNode.getLength() > 0)) {
            final Element simpleNonPreferrredTermElement = (Element) simpleNonPreferrredTermNode.item(0);
            skill.setSimpleNonPreferredTerm(getLexicalValues(simpleNonPreferrredTermElement.getElementsByTagName(XMLKeys.LEXICAL_VALUE)));
        }

        return skill;
    }

    private List<LexicalValue> getLexicalValues(final NodeList node){
        final List<LexicalValue> lexicalValues = Lists.newArrayList();
        if(node == null) return lexicalValues;
        for(int i = 0; i < node.getLength(); i ++){
            final Element lexicalValueElement = (Element) node.item(i);
            final LexicalValue lexicalValue = new LexicalValue();
            lexicalValue.setLang(lexicalValueElement.getAttribute(XMLAttributes.LANGUAGE));
            lexicalValue.setValue(lexicalValueElement.getTextContent());
            lexicalValues.add(lexicalValue);
        }
        return lexicalValues;
    }

    private String getFirstValue(final Element element, final String xmlKey){
        final NodeList node = element.getElementsByTagName(xmlKey);
        return ((node != null) && (node.getLength() > 0))? node.item(0).getTextContent() : null;
    }
}
