package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.services.CompetenceDatabaseService;
import ec.blopez.lello.services.XmlParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Benjamin Lopez on 31/01/2017.
 */
@Service
public class CompetenceDatabaseServiceImpl implements CompetenceDatabaseService {

    private Map<String, Competence> defaultValues;

    @Autowired
    private XmlParserService xmlParserService;


    @Override
    public Competence get(final String id) {
        return getMap().get(id);
    }

    @Override
    public List<Competence> get(final List<String> ids) {
        List<Competence> result = Lists.newArrayList();
        for(String id : ids){
            final Competence competence = getMap().get(id);
            if(competence != null) result.add(competence);
        }
        return result;
    }

    @Override
    public List<Competence> get() {
        return Lists.newArrayList(getMap().values());
    }

    private Map<String, Competence> getMap(){
        if (defaultValues == null) defaultValues = xmlParserService.load();
        return defaultValues;
    }
}
