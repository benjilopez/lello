package ec.blopez.lello.services.impl;

import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.services.XmlParserService;
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
    public List<Competence> load() {
        try {
            final File inputFile = new File(escoSkillsPath);
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            final NodeList exportNode = doc.getElementsByTagName(XMLKeys.EXPORT);
            final NodeList thesaurusNode = doc.getElementsByTagName(XMLKeys.THESAURUS);
            final Element thesaurusElement = (Element) thesaurusNode.item(0);
            final NodeList thesaurusConcepts = thesaurusElement.getElementsByTagName(XMLKeys.THESAURUS_CONCEPT);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOG.error("Error trying to load default XML File", e);
        }
        return null;
    }
}
