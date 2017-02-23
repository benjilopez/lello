package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.enums.DataBaseAction;
import ec.blopez.lello.exceptions.DatabaseActionException;
import ec.blopez.lello.services.DatabaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Benjamin Lopez on 31/01/2017.
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {

    private Map<String, Competence> competences = Maps.newHashMap();

    @Override
    public Competence get(final String id) {
        return competences.get(id);
    }

    @Override
    public List<Competence> get() {
        return Lists.newArrayList(competences.values());
    }

    @Override
    public Competence update(final Competence competence) throws DatabaseActionException {
        final Map<String, Competence> map = getMapForAction(competence, DataBaseAction.UPDATE);
        if(!map.containsKey(competence.getId())) throw new DatabaseActionException("Database Update: No entry in the Database with identifier " + competence.getIdentifier());
        map.put(competence.getIdentifier(), competence);
        return competence;
    }

    @Override
    public Competence delete(final String id) throws DatabaseActionException {
        final Map<String, Competence> map = competences;
        if(!map.containsKey(id)) throw new DatabaseActionException("Database Delete: No entry in the Database with identifier " + id);
        return competences.remove(id);
    }

    @Override
    public Competence create(final Competence competence) throws DatabaseActionException{
        final Map<String, Competence> map = getMapForAction(competence, DataBaseAction.CREATE);
        map.put(competence.getId(), competence);
        return competence;
    }

    @Override
    public List<Competence> search(final String query) {
        return null;
    }

    private Map<String, Competence> getMapForAction(final Competence competence, final DataBaseAction action) throws DatabaseActionException{
        final String value = action.getValue();
        if(competence == null) throw new DatabaseActionException("Database " + value +": Entry to create is null");
        final String id = competence.getId();
        if(id == null) throw new DatabaseActionException("Database " + value + ": Missing entry's identifier");
        return competences;
    }
}
