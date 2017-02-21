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

    private final String skillsPath;
    private final String qualificationsPath;
    private final String occupationsPath;

    private final static Logger LOG = LoggerFactory.getLogger(XmlParserServiceImpl.class);

    private final static  Map<String, Competence> MAP_BY_URI = Maps.newHashMap();

    @Autowired
    public XmlParserServiceImpl(@Value("${esco.files.skills.production}") final String skillsProd,
                                @Value("${esco.files.skills.development}") final String skillsDev,
                                @Value("${esco.files.occupations.production}") final String occupationsProd,
                                @Value("${esco.files.occupations.development}") final String occupationsDev,
                                @Value("${esco.files.qualifications.production}") final String qualificationsProd,
                                @Value("${esco.files.qualifications.development}") final String qualificationsDev,
                                @Value("${lello.environment}") final String environment){
        this.skillsPath = "PRODUCTION".equals(environment)? skillsProd : skillsDev;
        this.qualificationsPath = "PRODUCTION".equals(environment)? qualificationsProd : qualificationsDev;
        this.occupationsPath = "PRODUCTION".equals(environment)? occupationsProd : occupationsDev;
        load();
    }

    @Override
    public Map<String, Competence> load(){
        parseFolder(new File(skillsPath));
        parseFolder(new File(qualificationsPath));
        parseFolder(new File(occupationsPath));
        return MAP_BY_URI;
    }

    private void parseFolder(final File folder){
        if(folder.isDirectory() && folder.exists()) {
            boolean isFirstFile = true;
            for(File file : folder.listFiles()) {
                LOG.info("Parsing XML File: " + file.getAbsolutePath());
                try {
                    final JAXBContext jc = JAXBContext.newInstance(XMLParserMainClass.class);
                    final Unmarshaller unmarshaller = jc.createUnmarshaller();
                    final XMLParserMainClass xmlFile = (XMLParserMainClass) unmarshaller.unmarshal(file);
                    if(xmlFile.getThesauruses() == null){
                        LOG.error("Missing Thesauruses: " + file.getAbsolutePath());
                        continue;
                    }
                    for(Thesaurus thesaurus : xmlFile.getThesauruses()) {
                        switch (XMLType.fromString(thesaurus.getTitle())) {
                            case SKILL:
                                parseSkills(thesaurus.getThesaurusConcepts());
                                break;
                            case QUALIFICATION:
                                parseQualifications(thesaurus.getThesaurusConcepts());
                            default:
                                continue;
                        }
                        if (isFirstFile) {
                            for (Relationship relationship : thesaurus.getRelationships()) {
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
                    }
                } catch (JAXBException e) {
                    LOG.error("Error parsing file: " + file.getAbsolutePath(), e);
                }
                LOG.info("Parsing XML File Finished: " + file.getAbsolutePath());
            }
        }
    }

    private void parseSkills(final List<ThesaurusConcept> concepts){
        for (ThesaurusConcept concept : concepts) {
            final Skill skill = concept.toSkill();
            final Competence competenceInDB = MAP_BY_URI.get(concept.getUri());
            if(competenceInDB == null){
                MAP_BY_URI.put(concept.getUri(), skill);
            } else {
                competenceInDB.addPreferredTerm(skill.getPreferredTerm());
                if(competenceInDB instanceof Skill) {
                    ((Skill) competenceInDB).addSimpleNonPreferredTerm(skill.getSimpleNonPreferredTerm());
                }
            }
        }
    }

    private void parseQualifications(final List<ThesaurusConcept> concepts){
        for(ThesaurusConcept concept : concepts){
            final Qualification qualification = concept.toQualification();
            final Competence competenceInDB = MAP_BY_URI.get(concept.getUri());
            if(competenceInDB == null){
                    MAP_BY_URI.put(concept.getUri(), qualification);
            } else {
                competenceInDB.addPreferredTerm(qualification.getPreferredTerm());
                if(competenceInDB instanceof Qualification) {
                    ((Qualification) competenceInDB).addDefinitions(qualification.getDefinition());
                }
            }
        }
    }

    @Override
    public Map<String, Competence> getMap(){
        return MAP_BY_URI;
    }

}
