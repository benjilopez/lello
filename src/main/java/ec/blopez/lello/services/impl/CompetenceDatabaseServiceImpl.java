package ec.blopez.lello.services.impl;

import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.services.CompetenceDatabaseService;
import ec.blopez.lello.services.XmlParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (defaultValues == null) defaultValues = xmlParserService.load();
        return defaultValues.get(id);
    }
}
