package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.exceptions.DatabaseActionException;
import ec.blopez.lello.services.CompetenceService;
import ec.blopez.lello.services.ControlService;
import ec.blopez.lello.services.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
@Service
public class CompetenceServiceImpl<T> implements CompetenceService {

    private final static Logger LOG = LoggerFactory.getLogger(CompetenceServiceImpl.class);


    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private ControlService controlService;

    @Override
    public Competence get(final String id) {
        if (id == null) return null;
        return databaseService.get(id);
    }

    @Override
    public List<Competence> get(final List<String> ids) {
        if(ids == null) return null;
        final List<Competence> result = Lists.newArrayList();
        for(String id : ids){
            final Competence competence = get(id);
            if(competence != null) result.add(competence);
        }
        return result;
    }

    @Override
    public List<Competence> get() {
        return databaseService.get();
    }

    @Override
    public Competence update(final String id, final Competence competence) {
        if((competence == null) || (id == null) || (get(id) == null)) return null;
        competence.setId(id);
        try {
            return databaseService.update(competence);
        } catch (final DatabaseActionException e) {
            LOG.error("Error trying to update competence in the database.", e);
        }
        return null;
    }

    @Override
    public Competence delete(final String id) {
        try {
            if(id == null) return null;
            final Competence toDelete = get(id);
            if(toDelete == null) return null;
            return databaseService.delete(id);
        } catch (DatabaseActionException e) {
            LOG.error("Error trying to delete competence from the database", e);
        }

        return null;

    }

    @Override
    public Competence create(final Competence competence) {
        try {
            if(competence == null) return null;
            final Competence competenceToUpdate = controlService.checkDouble(competence);
            if(competenceToUpdate != null){
                return databaseService.update(competenceToUpdate);
            } else {
                return databaseService.create(competence);
            }
        } catch (DatabaseActionException e) {
            LOG.error("Error trying to create new competence in the database.", e);
        }
        return null;
    }

    @Override
    public List<Competence> search(final String query) {
        final List<Competence> result = Lists.newArrayList();
        return result;
    }
}
