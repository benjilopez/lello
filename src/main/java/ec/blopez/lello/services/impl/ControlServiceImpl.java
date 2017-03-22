package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import ec.blopez.lello.domain.Competence;
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

    private Map<String, String> mergeLanguages(Map<String, String> savedDate, Map<String, String> newData){
        if(newData != null){
            if((savedDate == null) || (savedDate.size() == 0)){
                return newData;
            } else{
                for(Map.Entry<String, String> entry : newData.entrySet()){
                    savedDate.putIfAbsent(entry.getKey(), entry.getValue());
                }
            }
        }
        return savedDate;
    }

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
    public List<Competence> checkRelated(final Competence competence) {
        return Lists.newArrayList();
    }
}
