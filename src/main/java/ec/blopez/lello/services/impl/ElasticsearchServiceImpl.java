package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.enums.DataBaseAction;
import ec.blopez.lello.exceptions.DatabaseActionException;
import ec.blopez.lello.services.ElasticsearchService;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * Created by Benjamin Lopez on 31/01/2017.
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private final static Logger LOG = LoggerFactory.getLogger(ControlServiceImpl.class);

    private Map<String, Competence> competences = Maps.newHashMap();

    private TransportClient client;

    @Autowired
    public ElasticsearchServiceImpl(@Value("${elasticsearch.host}") final String host,
                                    @Value("${elasticsearch.port}") final int port,
                                    @Value("${elasticsearch.cluster.name}") final String clusterName){
        try {
            final Settings settings = Settings.builder().put("cluster.name", clusterName)
                    //.put("client.transport.sniff", true)
                    .build();
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        } catch (UnknownHostException e) {
            LOG.error("Error creating Bean for connection to Elasticsearch.", e);
        }
    }

    @Override
    public Competence get(final String id) {
        return competences.get(id);
    }

    @Override
    public Competence getFromExternalURL(final String url) {
        if(url == null) return null;
        for(Competence competence : competences.values()){
            if(url.equals(competence.getExternalUri())) return competence;
        }
        return null;
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
