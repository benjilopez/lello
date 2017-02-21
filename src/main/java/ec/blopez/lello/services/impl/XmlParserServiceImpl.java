package ec.blopez.lello.services.impl;

import com.google.common.collect.Maps;
import ec.blopez.lello.domain.*;
import ec.blopez.lello.enums.XMLType;
import ec.blopez.lello.services.XmlParserService;
import ec.blopez.lello.xml.domain.*;
import ec.blopez.lello.xml.domain.Relationship;
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
    private final String relationshipsPath;

    private final static Logger LOG = LoggerFactory.getLogger(XmlParserServiceImpl.class);

    private final static  Map<String, Competence> MAP_BY_URI = Maps.newHashMap();

    @Autowired
    public XmlParserServiceImpl(@Value("${esco.files.skills.production}") final String skillsProd,
                                @Value("${esco.files.skills.development}") final String skillsDev,
                                @Value("${esco.files.occupations.production}") final String occupationsProd,
                                @Value("${esco.files.occupations.development}") final String occupationsDev,
                                @Value("${esco.files.qualifications.production}") final String qualificationsProd,
                                @Value("${esco.files.qualifications.development}") final String qualificationsDev,
                                @Value("${esco.files.relationships.production}") final String relationshipsProd,
                                @Value("${esco.files.relationships.development}") final String relationshipsDev,
                                @Value("${lello.environment}") final String environment){
        this.skillsPath = "PRODUCTION".equals(environment)? skillsProd : skillsDev;
        this.qualificationsPath = "PRODUCTION".equals(environment)? qualificationsProd : qualificationsDev;
        this.occupationsPath = "PRODUCTION".equals(environment)? occupationsProd : occupationsDev;
        this.relationshipsPath = "PRODUCTION".equals(environment)? relationshipsProd : relationshipsDev;
        load();
    }

    @Override
    public Map<String, Competence> load(){
        parseFolder(new File(skillsPath));
        parseFolder(new File(qualificationsPath));
        parseFolder(new File(occupationsPath));
        parseFolder(new File(relationshipsPath));
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
                    if(xmlFile.getThesauruses() != null) {
                        for (Thesaurus thesaurus : xmlFile.getThesauruses()) {
                            if (!parse(thesaurus.getThesaurusConcepts(), XMLType.fromString(thesaurus.getTitle())))
                                continue;
                            if (isFirstFile && (thesaurus.getRelationships() != null)) {
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
                    }

                    if(xmlFile.getRelationships() != null){
                        for(AssociativeRelationship relationship : xmlFile.getRelationships()){
                            final Competence competence1 = MAP_BY_URI.get(relationship.getIsRelatedConcept());
                            final Competence competence2 = MAP_BY_URI.get(relationship.getHasRelatedConcept());
                            final LexicalValue description = relationship.getDescription();
                            if ((competence1 == null) || (competence2 == null) || (description == null)) continue;
                            final ec.blopez.lello.domain.Relationship result1 = new ec.blopez.lello.domain.Relationship();
                            final ec.blopez.lello.domain.Relationship result2 = new ec.blopez.lello.domain.Relationship();
                            final Map<String, String> descriptionMap = description.toDomain();
                            result1.setUri(competence2.getUri());
                            result2.setUri(competence1.getUri());
                            result1.setIdentifier(competence2.getIdentifier());
                            result2.setIdentifier(competence1.getIdentifier());
                            result1.setMessage(descriptionMap);
                            result2.setMessage(descriptionMap);
                            competence1.addRelated(result1);
                            competence2.addRelated(result2);
                        }
                    }

                } catch (JAXBException e) {
                    LOG.error("Error parsing file: " + file.getAbsolutePath(), e);
                }
                LOG.info("Parsing XML File Finished: " + file.getAbsolutePath());
            }
        }
    }

    private boolean parse(final List<ThesaurusConcept> concepts, final XMLType type){
        for (ThesaurusConcept concept : concepts) {
            final Competence competenceInDB = MAP_BY_URI.get(concept.getUri());

            final Competence competence;
            switch (type){
                case OCCUPATION:
                    competence = concept.toOccupation();
                    break;
                case QUALIFICATION:
                    competence = concept.toQualification();
                    break;
                case SKILL:
                    competence = concept.toSkill();
                    break;
                default:
                    return false;
            }
            if(competenceInDB == null){
                MAP_BY_URI.put(concept.getUri(), competence);
            } else {
                competenceInDB.addPreferredTerm(competence.getPreferredTerm());
                if(competenceInDB instanceof Skill) {
                    ((Skill) competenceInDB).addSimpleNonPreferredTerm(((Skill) competence).getSimpleNonPreferredTerm());
                }
                if(competenceInDB instanceof Qualification) {
                    ((Qualification) competenceInDB).addDefinitions(((Qualification) competence).getDefinition());
                }
            }
        }
        return true;
    }

    @Override
    public Map<String, Competence> getMap(){
        return MAP_BY_URI;
    }

}
