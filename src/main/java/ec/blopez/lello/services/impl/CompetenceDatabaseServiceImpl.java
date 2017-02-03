package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.enums.DataBaseAction;
import ec.blopez.lello.exceptions.DatabaseActionException;
import ec.blopez.lello.services.CompetenceDatabaseService;
import ec.blopez.lello.services.XmlParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
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
    public Competence get(@NotNull final String id) {
        return getMap().get(id);
    }

    @Override
    public List<Competence> get(@NotNull final List<String> ids) {
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

    @Override
    public Competence update(@NotNull final Competence competence) throws DatabaseActionException {
        final Map<String, Competence> map = getMapForAction(competence, DataBaseAction.UPDATE);
        if(!map.containsKey(competence.getIdentifier())) throw new DatabaseActionException("Database Update: No entry in the Database with identifier " + competence.getIdentifier());
        map.put(competence.getIdentifier(), competence);
        return competence;
    }

    @Override
    public Competence delete(@NotNull final String id) throws DatabaseActionException {
        final Map<String, Competence> map = getMap();
        if(!map.containsKey(id)) throw new DatabaseActionException("Database Delete: No entry in the Database with identifier " + id);
        return getMap().remove(id);
    }

    @Override
    public Competence create(@NotNull final Competence competence) throws DatabaseActionException{
        final Map<String, Competence> map = getMapForAction(competence, DataBaseAction.CREATE);
        map.put(competence.getIdentifier(), competence);
        return competence;
    }

    @Override
    public List<Competence> search(final String query) {
        return null;
    }

    private Map<String, Competence> getMapForAction(@NotNull final Competence competence, @NotNull final DataBaseAction action) throws DatabaseActionException{
        final String value = action.getValue();
        if(competence == null) throw new DatabaseActionException("Database " + value +": Entry to create is null");
        final String id = competence.getIdentifier();
        if(id == null) throw new DatabaseActionException("Database " + value + ": Missing entry's identifier");
        return getMap();
    }

    private Map<String, Competence> getMap(){
        if (defaultValues == null) defaultValues = xmlParserService.load();
        return defaultValues;
    }
}
