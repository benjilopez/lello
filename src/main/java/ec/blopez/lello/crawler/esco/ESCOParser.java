package ec.blopez.lello.crawler.esco;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ec.blopez.lello.crawler.LelloParser;
import ec.blopez.lello.domain.*;
import ec.blopez.lello.crawler.esco.domain.CompetenceType;
import ec.blopez.lello.crawler.esco.domain.*;
import ec.blopez.lello.crawler.esco.domain.Relationship;
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
    public List<Competence> parse(final URL url){
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
    public List<Competence> parse(final File folder){
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
    public List<Competence> parse(final ESCOXmlRootElement xmlFile){
        final Map<String, Competence> escoMap = Maps.newHashMap();
        if(xmlFile.getThesauruses() != null) {
            for (Thesaurus thesaurus : xmlFile.getThesauruses()) {
                final Map<String, Competence> result = parse(thesaurus.getThesaurusConcepts(), CompetenceType.fromString(thesaurus.getTitle()));
                if (result.size() > 0) escoMap.putAll(result);
                else continue;
                if (thesaurus.getRelationships() != null) {
                    for (Relationship relationship : thesaurus.getRelationships()) {
                        final Competence child = escoMap.get(relationship.getChildUri());
                        final Competence parent = escoMap.get(relationship.getParentUri());
                        if ((child == null) || (parent == null)) continue;
                        child.addParent(parent);
                        parent.addChild(child);
                    }
                }
            }
        }

        if(xmlFile.getRelationships() != null){
            for(AssociativeRelationship relationship : xmlFile.getRelationships()){
                final Competence competence1 = escoMap.get(relationship.getIsRelatedConcept());
                final Competence competence2 = escoMap.get(relationship.getHasRelatedConcept());
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
        return Lists.newArrayList(escoMap.values());
    }

    @Override
    public List<Competence> parse(final HtmlParseData htmlParseDate) {
        return null;
    }

    private Map<String, Competence> parse(final List<ThesaurusConcept> concepts, final CompetenceType type){
        final Map<String, Competence> escoMap = Maps.newHashMap();
        for (ThesaurusConcept concept : concepts) {
            switch (type){
                case OCCUPATION:
                    escoMap.put(concept.getUri(), concept.toOccupation());
                    break;
                case QUALIFICATION:
                    escoMap.put(concept.getUri(), concept.toQualification());
                    break;
                case SKILL:
                    escoMap.put(concept.getUri(), concept.toSkill());
                    break;
            }
        }
        return escoMap;
    }

}
