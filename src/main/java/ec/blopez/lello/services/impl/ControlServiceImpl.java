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

    @Override
    public Competence checkDouble(final Competence competence) {
        if(competence == null) return null;
        Competence savedCompetence = null;
        if(competence.getExternalUri() != null) savedCompetence = elasticsearchService.getFromExternalURL(competence.getExternalUri());
        if(savedCompetence == null) return null;
        if(!savedCompetence.getExternalUri().equals(competence.getExternalUri())){
            LOG.error("Found entry doesn't match with the competence");
        }
        Map<String, String> preferredTerms = competence.getPreferredTerm();
        if(preferredTerms != null){
            Map<String, String> savedPreferredTerms = savedCompetence.getPreferredTerm();
            if((savedPreferredTerms == null) || (savedPreferredTerms.size() == 0)){
                savedCompetence.setPreferredTerm(preferredTerms);
            } else{
                for(Map.Entry<String, String> entry : preferredTerms.entrySet()){
                    savedPreferredTerms.putIfAbsent(entry.getKey(), entry.getValue());
                }
            }
        }

        return savedCompetence;
    }

    @Override
    public List<Competence> checkRelated(final Competence competence) {
        return Lists.newArrayList();
    }
}
