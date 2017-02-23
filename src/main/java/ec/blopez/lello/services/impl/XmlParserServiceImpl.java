package ec.blopez.lello.services.impl;

import com.google.common.collect.Maps;
import ec.blopez.lello.domain.*;
import ec.blopez.lello.enums.CompetenceType;
import ec.blopez.lello.exceptions.DatabaseActionException;
import ec.blopez.lello.services.DatabaseService;
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
import java.net.URL;
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
    private DatabaseService databaseService;

    private final static Logger LOG = LoggerFactory.getLogger(XmlParserServiceImpl.class);

    private final static  Map<String, Competence> MAP_BY_URI = Maps.newHashMap();

    @Autowired
    public XmlParserServiceImpl(final DatabaseService databaseService,
                                @Value("${esco.files.skills.production}") final String skillsProd,
                                @Value("${esco.files.skills.development}") final String skillsDev,
                                @Value("${esco.files.occupations.production}") final String occupationsProd,
                                @Value("${esco.files.occupations.development}") final String occupationsDev,
                                @Value("${esco.files.qualifications.production}") final String qualificationsProd,
                                @Value("${esco.files.qualifications.development}") final String qualificationsDev,
                                @Value("${esco.files.relationships.production}") final String relationshipsProd,
                                @Value("${esco.files.relationships.development}") final String relationshipsDev,
                                @Value("${lello.environment}") final String environment){
        this.databaseService = databaseService;
        this.skillsPath = "PRODUCTION".equals(environment)? skillsProd : skillsDev;
        this.qualificationsPath = "PRODUCTION".equals(environment)? qualificationsProd : qualificationsDev;
        this.occupationsPath = "PRODUCTION".equals(environment)? occupationsProd : occupationsDev;
        this.relationshipsPath = "PRODUCTION".equals(environment)? relationshipsProd : relationshipsDev;
        //load();
    }

    private void load(){
        parse(new File(skillsPath));
        parse(new File(qualificationsPath));
        parse(new File(occupationsPath));
        parse(new File(relationshipsPath));
    }

    @Override
    public void parse(final URL url){
        LOG.info("Parsing URL: " + url.toString());
        try {
            final JAXBContext jc = JAXBContext.newInstance(XMLParserMainClass.class);
            final Unmarshaller unmarshaller = jc.createUnmarshaller();
            parse((XMLParserMainClass) unmarshaller.unmarshal(url));
        } catch (JAXBException e) {
            LOG.error("Error parsing URL: " + url.toString(), e);
        }
        LOG.info("Parsing URL Finished: " + url.toString());
    }

    @Override
    public void parse(final File folder){
        if(folder.isDirectory() && folder.exists()) {
            for(File file : folder.listFiles()) {
                LOG.info("Parsing XML File: " + file.getAbsolutePath());
                try {
                    final JAXBContext jc = JAXBContext.newInstance(XMLParserMainClass.class);
                    final Unmarshaller unmarshaller = jc.createUnmarshaller();
                    parse((XMLParserMainClass) unmarshaller.unmarshal(file));
                } catch (JAXBException e) {
                    LOG.error("Error parsing file: " + file.getAbsolutePath(), e);
                }
                LOG.info("Parsing XML File Finished: " + file.getAbsolutePath());
            }
        }
    }

    @Override
    public void parse(final XMLParserMainClass xmlFile){
        if(xmlFile.getThesauruses() != null) {
            for (Thesaurus thesaurus : xmlFile.getThesauruses()) {
                if (!parse(thesaurus.getThesaurusConcepts(), CompetenceType.fromString(thesaurus.getTitle())))
                    continue;
                if (thesaurus.getRelationships() != null) {
                    for (Relationship relationship : thesaurus.getRelationships()) {
                        final Competence child = MAP_BY_URI.get(relationship.getChildUri());
                        final Competence parent = MAP_BY_URI.get(relationship.getParentUri());
                        if ((child == null) || (parent == null)) continue;
                        child.addParent(parent);
                        parent.addChild(child);
                    }
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
                result1.setCompetence(competence2);
                result2.setCompetence(competence1);
                result1.setMessage(descriptionMap);
                result2.setMessage(descriptionMap);
                competence1.addRelated(result1);
                competence2.addRelated(result2);
            }
        }
    }

    private boolean parse(final List<ThesaurusConcept> concepts, final CompetenceType type){
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
                try {
                    MAP_BY_URI.put(concept.getUri(), competence);
                    databaseService.create(competence);
                } catch (DatabaseActionException e) {
                    LOG.error("Error saving competence in the database: " + competence);
                }
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

}
