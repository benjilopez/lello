package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.exceptions.DatabaseActionException;
import ec.blopez.lello.services.CompetenceService;
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
public class CompetenceServiceImpl<T> implements CompetenceService<T> {

    private final static Logger LOG = LoggerFactory.getLogger(CompetenceServiceImpl.class);


    @Autowired
    private DatabaseService databaseService;

    @Override
    public T get(final String id, final Class c) {
        final Competence competence = databaseService.get(id);
        if(c.isInstance(competence)) return (T) competence;
        return null;
    }

    @Override
    public List<T> get(final List<String> ids, final Class c) {
        final List<T> result = Lists.newArrayList();
        for(String id : ids){
            final T competence = get(id, c);
            if(competence != null) result.add(competence);
        }
        return result;
    }

    @Override
    public List<T> get(final Class c) {
        final List<T> result = Lists.newArrayList();
        final List<Competence> competences = databaseService.get();
        for(Competence competence : competences){
            if(c.isInstance(competence)) result.add((T) competence);
        }
        return result;
    }

    @Override
    public T update(final String id, final T competence, final Class c) {
        if((competence == null) || (get(id, c) == null) || !(competence instanceof Competence)) return null;
        final Competence toUpdate = (Competence) competence;
        toUpdate.setId(id);
        try {
            return (T) databaseService.update(toUpdate);
        } catch (final DatabaseActionException e) {
            LOG.error("Error trying to update competence in the database.", e);
        }
        return null;
    }

    @Override
    public T delete(final String id, final Class c) {
        try {
            final T toDelete = get(id, c);
            if(toDelete == null) return null;
            return (T) databaseService.delete(id);
        } catch (DatabaseActionException e) {
            LOG.error("Error trying to delete competence from the database", e);
        }

        return null;

    }

    @Override
    public T create(final T competence) {
        try {
            if((competence == null) || !(competence instanceof Competence)) return null;
            return (T) databaseService.create((Competence) competence);
        } catch (DatabaseActionException e) {
            LOG.error("Error trying to create new competence in the database.", e);
        }
        return null;
    }

    @Override
    public List<T> search(final String query) {
        final List<T> result = Lists.newArrayList();
        return result;
    }
}
