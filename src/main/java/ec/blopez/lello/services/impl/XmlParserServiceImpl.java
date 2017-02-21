package ec.blopez.lello.services.impl;

import com.google.common.collect.Maps;
import ec.blopez.lello.domain.*;
import ec.blopez.lello.enums.XMLType;
import ec.blopez.lello.services.XmlParserService;
import ec.blopez.lello.xml.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Benjamin Lopez on 31/01/2017.
 */
@Service
public class XmlParserServiceImpl implements XmlParserService {

    private final String escoSkillsPath;

    private final static Logger LOG = LoggerFactory.getLogger(XmlParserServiceImpl.class);

    private final static  Map<String, Competence> MAP_BY_URI = Maps.newHashMap();

    @Autowired
    public XmlParserServiceImpl(@Value("${esco.files.skills.production}") final String escoSkillsPathProduction,
                                @Value("${esco.files.skills.development}") final String escoSkillsPathDevelopment,
                                @Value("${lello.environment}") final String environment){
        this.escoSkillsPath = "PRODUCTION".equals(environment)? escoSkillsPathProduction : escoSkillsPathDevelopment;
        load();
    }

    @Override
    public Map<String, Competence> load(){
        final File mainFolder = new File(escoSkillsPath);
        if(mainFolder.isDirectory() && mainFolder.exists()) {
            boolean isFirstFile = true;
            for(File file : mainFolder.listFiles()) {
                LOG.info("Parsing XML File: " + file.getAbsolutePath());
                try {
                    final JAXBContext jc = JAXBContext.newInstance(XMLParserMainClass.class);
                    final Unmarshaller unmarshaller = jc.createUnmarshaller();
                    final XMLParserMainClass xmlFile = (XMLParserMainClass) unmarshaller.unmarshal(file);
                    switch(XMLType.fromString(xmlFile.getTitle())){
                        case SKILL:
                            parseSkills(xmlFile.getThesaurusConcepts());
                            break;
                        default:
                            continue;
                    }
                    if(isFirstFile) {
                        for (Relationship relationship : xmlFile.getRelationships()) {
                            final Competence child = MAP_BY_URI.get(relationship.getChildUri());
                            final Competence parent = MAP_BY_URI.get(relationship.getParentUri());
                            if ((child == null) || (parent == null)) continue;
                            final String parentIdentifier = parent.getIdentifier();
                            final String parentUri = parent.getUri();
                            final String childIdentifier = child.getIdentifier();
                            final String childUri = child.getUri();
                            if (childIdentifier != null) parent.addChildIdentifier(childIdentifier);
                            if (childUri != null) parent.addChildUri(childUri);
                            if (parentIdentifier != null) child.addParentIdentifier(parentIdentifier);
                            if (parentUri != null) child.addParentUri(parentUri);
                        }
                        isFirstFile = false;
                    }

                } catch (JAXBException e) {
                    LOG.error("Error parsing file: " + file.getAbsolutePath(), e);
                }
                LOG.info("Parsing XML File Finished: " + file.getAbsolutePath());
            }
        }
        return MAP_BY_URI;
    }

    private void parseSkills(final List<ThesaurusConcept> thesaurusConcepts){
        for (ThesaurusConcept concept : thesaurusConcepts) {
            final Skill skill = concept.toSkill();
            final Competence competenceInDB = MAP_BY_URI.get(concept.getUri());
            if(competenceInDB == null){
                MAP_BY_URI.put(concept.getUri(), skill);
            }
            else {
                competenceInDB.addPreferredTerm(skill.getPreferredTerm());
                if(competenceInDB instanceof Skill) {
                    ((Skill) competenceInDB).addSimpleNonPreferredTerm(skill.getSimpleNonPreferredTerm());
                }
            }
        }
    }

    @Override
    public Map<String, Competence> getMap(){
        return MAP_BY_URI;
    }

}
