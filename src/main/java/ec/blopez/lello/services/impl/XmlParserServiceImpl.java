package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.domain.LexicalValue;
import ec.blopez.lello.domain.Skill;
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
    public XmlParserServiceImpl(@Value("${esco.files.skills}") final String escoSkillsPath){
        this.escoSkillsPath = escoSkillsPath;
        load();
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
            LOG.info("Parsing and normalization finished");
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
        final Element preferredTermElement = (Element) preferredTermNode.item(0);
        final NodeList lexicalValuesNode = preferredTermElement.getElementsByTagName(XMLKeys.LEXICAL_VALUE);
        skill.setPreferredTerm(getLexicalValues(lexicalValuesNode));

        final NodeList simpleNonPreferrredTermNode = el.getElementsByTagName(XMLKeys.SIMPLE_NON_PREFERRED_TERM);
        final Element simpleNonPreferrredTermElement = (Element) simpleNonPreferrredTermNode.item(0);
        final NodeList lexicalValuesNodeNonPreferred = simpleNonPreferrredTermElement.getElementsByTagName(XMLKeys.LEXICAL_VALUE);
        skill.setSimpleNonPreferredTerm(getLexicalValues(lexicalValuesNodeNonPreferred));

        return skill;
    }

    private List<LexicalValue> getLexicalValues(final NodeList node){
        final List<LexicalValue> lexicalValues = Lists.newArrayList();
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
        return element.getElementsByTagName(xmlKey).item(0).getTextContent();
    }
}
