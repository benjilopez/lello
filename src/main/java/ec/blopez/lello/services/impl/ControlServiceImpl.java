package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.services.ControlService;
import ec.blopez.lello.services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Benjamin Lopez on 04/03/2017.
 */
@Service
public class ControlServiceImpl implements ControlService {

    @Autowired
    private DatabaseService databaseService;

    @Override
    public Competence checkDouble(final Competence competence) {
        return competence;
    }

    @Override
    public List<Competence> checkRelated(final Competence competence) {
        return Lists.newArrayList();
    }
}
