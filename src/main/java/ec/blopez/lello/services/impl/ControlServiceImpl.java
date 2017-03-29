package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.domain.Relationship;
import ec.blopez.lello.services.ControlService;
import ec.blopez.lello.services.ElasticsearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Benjamin Lopez on 04/03/2017.
 */
@Service
public class ControlServiceImpl implements ControlService {

    private final static Logger LOG = LoggerFactory.getLogger(ControlServiceImpl.class);

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Override
    public Competence checkDouble(final Competence competence) {
        if(competence == null) return null;
        Competence savedCompetence = null;
        if(competence.getExternalUri() != null) savedCompetence = elasticsearchService.getFromExternalURL(competence.getExternalUri());
        if(savedCompetence == null) return null;
        savedCompetence.setPreferredTerm(mergeLanguages(savedCompetence.getPreferredTerm(), competence.getPreferredTerm()));
        savedCompetence.setSimpleNonPreferredTerm(mergeLanguages(savedCompetence.getSimpleNonPreferredTerm(), competence.getSimpleNonPreferredTerm()));
        savedCompetence.setDefinition(mergeLanguages(savedCompetence.getDefinition(), competence.getDefinition()));
        return savedCompetence;
    }

    @Override
    public List<Relationship> checkRelated(final Competence competence) {
        return null;
    }

    @Override
    public Relationship checkDouble(final Competence competence, final Relationship relationship){
        final List<Relationship> savedRelationships = (competence.getRelated() != null) ? competence.getRelated()
                : Lists.newArrayList();
        for(Relationship savedRelationship : savedRelationships){
            if (    existsAndIsEqual(savedRelationship.getId(), relationship.getId()) ||
                    existsAndIsEqual(savedRelationship.getCode(), relationship.getCode()) ||
                    existsAndIsEqual(savedRelationship.getExternalUrl(), relationship.getExternalUrl())){
                savedRelationship.setMessage(mergeLanguages(savedRelationship.getMessage(), relationship.getMessage()));
                return savedRelationship;
            }
        }
        return null;
    }

    private Map<String, String> mergeLanguages(final Map<String, String> savedData, final Map<String, String> newData){
        if(newData != null){
            if((savedData == null) || (savedData.size() == 0)){
                return newData;
            } else {
                for(Map.Entry<String, String> entry : newData.entrySet()){
                    savedData.putIfAbsent(entry.getKey(), entry.getValue());
                }
            }
        }
        return savedData;
    }

    private boolean existsAndIsEqual(final String source, final String newValue){
        return (source != null) && (newValue != null) && source.equals(newValue);
    }
}
