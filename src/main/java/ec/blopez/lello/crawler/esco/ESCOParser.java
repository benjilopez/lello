package ec.blopez.lello.crawler.esco;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ec.blopez.lello.crawler.LelloParser;
import ec.blopez.lello.domain.*;
import ec.blopez.lello.crawler.esco.domain.CompetenceType;
import ec.blopez.lello.crawler.esco.domain.*;
import ec.blopez.lello.crawler.esco.domain.Relationship;
import ec.blopez.lello.enums.RelationshipType;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class ESCOParser implements LelloParser <ESCOXmlRootElement>{

    private final static Logger LOG = LoggerFactory.getLogger(ESCOParser.class);

    @Override
    public ParserResult parse(final URL url){
        LOG.info("Parsing URL: " + url.toString());
        try {
            final JAXBContext jc = JAXBContext.newInstance(ESCOXmlRootElement.class);
            final Unmarshaller unmarshaller = jc.createUnmarshaller();
            return parse((ESCOXmlRootElement) unmarshaller.unmarshal(url));
        } catch (JAXBException e) {
            LOG.error("Error parsing URL: " + url.toString(), e);
        }
        LOG.info("Parsing URL Finished: " + url.toString());
        return null;
    }

    @Override
    public ParserResult parse(final File folder){
        if(folder.isDirectory() && folder.exists()) {
            for(File file : folder.listFiles()) {
                LOG.info("Parsing XML File: " + file.getAbsolutePath());
                try {
                    final JAXBContext jc = JAXBContext.newInstance(ESCOXmlRootElement.class);
                    final Unmarshaller unmarshaller = jc.createUnmarshaller();
                    return parse((ESCOXmlRootElement) unmarshaller.unmarshal(file));
                } catch (JAXBException e) {
                    LOG.error("Error parsing file: " + file.getAbsolutePath(), e);
                }
                LOG.info("Parsing XML File Finished: " + file.getAbsolutePath());
            }
        }
        return null;
    }

    @Override
    public ParserResult parse(final ESCOXmlRootElement xmlFile){
        final List<Competence> competences = Lists.newArrayList();
        final List<ec.blopez.lello.domain.Relationship> relationships = Lists.newArrayList();
        if(xmlFile.getThesauruses() != null) {
            for (Thesaurus thesaurus : xmlFile.getThesauruses()) {
                final List<Competence> result = parse(thesaurus.getThesaurusConcepts(), CompetenceType.fromString(thesaurus.getTitle()));
                if (result.size() > 0) competences.addAll(result);
                else continue;
                if (thesaurus.getRelationships() != null) {
                    for (Relationship relationship : thesaurus.getRelationships()) {
                        final ec.blopez.lello.domain.Relationship cRelationship = new ec.blopez.lello.domain.Relationship();
                        cRelationship.setType(RelationshipType.CHILD);
                        cRelationship.setSourceExternaUrl(relationship.getParentUri());
                        cRelationship.setExternalUrl(relationship.getChildUri());
                        relationships.add(cRelationship);
                    }
                }
            }
        }

        if(xmlFile.getRelationships() != null){
            for(AssociativeRelationship relationship : xmlFile.getRelationships()){
                final ec.blopez.lello.domain.Relationship cRelationship = new ec.blopez.lello.domain.Relationship();
                cRelationship.setType(RelationshipType.RELATED);
                cRelationship.setSourceExternaUrl(relationship.getHasRelatedConcept());
                cRelationship.setExternalUrl(relationship.getIsRelatedConcept());
                final LexicalValue description = relationship.getDescription();
                cRelationship.setMessage(description.toDomain());
                relationships.add(cRelationship);
            }
        }
        return new ParserResult(competences, relationships);
    }

    @Override
    public ParserResult parse(final HtmlParseData htmlParseDate) {
        return null;
    }

    private List<Competence> parse(final List<ThesaurusConcept> concepts, final CompetenceType type){
        final List<Competence> competences = Lists.newArrayList();
        for (ThesaurusConcept concept : concepts) {
            switch (type){
                case OCCUPATION:
                    competences.add(concept.toCompetence(CompetenceType.OCCUPATION));
                    break;
                case QUALIFICATION:
                    competences.add(concept.toCompetence(CompetenceType.QUALIFICATION));
                    break;
                case SKILL:
                    competences.add(concept.toCompetence(CompetenceType.SKILL));
                    break;
            }
        }
        return competences;
    }

}
