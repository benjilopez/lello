package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.enums.DataBaseAction;
import ec.blopez.lello.exceptions.DatabaseActionException;
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
    private Map<String, Competence> mapByUri;

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

    @Override
    public Competence update(final Competence competence) throws DatabaseActionException {
        final Map<String, Competence> map = getMapForAction(competence, DataBaseAction.UPDATE);
        if(!map.containsKey(competence.getIdentifier())) throw new DatabaseActionException("Database Update: No entry in the Database with identifier " + competence.getIdentifier());
        map.put(competence.getIdentifier(), competence);
        return competence;
    }

    @Override
    public Competence delete(final String id) throws DatabaseActionException {
        final Map<String, Competence> map = getMap();
        if(!map.containsKey(id)) throw new DatabaseActionException("Database Delete: No entry in the Database with identifier " + id);
        return getMap().remove(id);
    }

    @Override
    public Competence create(final Competence competence) throws DatabaseActionException{
        final Map<String, Competence> map = getMapForAction(competence, DataBaseAction.CREATE);
        map.put(competence.getIdentifier(), competence);
        return competence;
    }

    @Override
    public List<Competence> search(final String query) {
        return null;
    }

    private Map<String, Competence> getMapForAction(final Competence competence, final DataBaseAction action) throws DatabaseActionException{
        final String value = action.getValue();
        if(competence == null) throw new DatabaseActionException("Database " + value +": Entry to create is null");
        final String id = competence.getIdentifier();
        if(id == null) throw new DatabaseActionException("Database " + value + ": Missing entry's identifier");
        return getMap();
    }

    private Map<String, Competence> getMap(){
        if (mapByUri == null){
            mapByUri = xmlParserService.getMap();
            defaultValues = Maps.newHashMap();
            for(Competence competence : mapByUri.values()){
                if(competence.getIdentifier() != null) defaultValues.put(competence.getId(), competence);
            }
        }
        return defaultValues;
    }
}
