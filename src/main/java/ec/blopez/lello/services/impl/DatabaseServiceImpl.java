package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ec.blopez.lello.domain.Esco;
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

    private Map<String, Esco> competences = Maps.newHashMap();

    @Override
    public Esco get(final String id) {
        return competences.get(id);
    }

    @Override
    public List<Esco> get() {
        return Lists.newArrayList(competences.values());
    }

    @Override
    public Esco update(final Esco esco) throws DatabaseActionException {
        final Map<String, Esco> map = getMapForAction(esco, DataBaseAction.UPDATE);
        if(!map.containsKey(esco.getId())) throw new DatabaseActionException("Database Update: No entry in the Database with identifier " + esco.getIdentifier());
        map.put(esco.getIdentifier(), esco);
        return esco;
    }

    @Override
    public Esco delete(final String id) throws DatabaseActionException {
        final Map<String, Esco> map = competences;
        if(!map.containsKey(id)) throw new DatabaseActionException("Database Delete: No entry in the Database with identifier " + id);
        return competences.remove(id);
    }

    @Override
    public Esco create(final Esco esco) throws DatabaseActionException{
        final Map<String, Esco> map = getMapForAction(esco, DataBaseAction.CREATE);
        map.put(esco.getId(), esco);
        return esco;
    }

    @Override
    public List<Esco> search(final String query) {
        return null;
    }

    private Map<String, Esco> getMapForAction(final Esco esco, final DataBaseAction action) throws DatabaseActionException{
        final String value = action.getValue();
        if(esco == null) throw new DatabaseActionException("Database " + value +": Entry to create is null");
        final String id = esco.getId();
        if(id == null) throw new DatabaseActionException("Database " + value + ": Missing entry's identifier");
        return competences;
    }
}
