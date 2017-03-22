package ec.blopez.lello.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.exceptions.DatabaseActionException;
import ec.blopez.lello.services.ElasticsearchService;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Benjamin Lopez on 31/01/2017.
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private final static Logger LOG = LoggerFactory.getLogger(ControlServiceImpl.class);

    private TransportClient client;

    final ObjectMapper mapper = new ObjectMapper();

    private String index;

    private String type;

    @Autowired
    public ElasticsearchServiceImpl(@Value("${elasticsearch.host}") final String host,
                                    @Value("${elasticsearch.port}") final int port,
                                    @Value("${elasticsearch.cluster.name}") final String clusterName,
                                    @Value("${elasticsearch.index}") final String index,
                                    @Value("${elasticsearch.type}") final String type){
        try {
            this.index = index;
            this.type = type;
            final Settings settings = Settings.builder().put("cluster.name", clusterName).build();
            this.client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        } catch (UnknownHostException e) {
            LOG.error("Error creating Bean for connection to Elasticsearch.", e);
        }

        if (this.client != null) {
            try {
                client.prepareSearch()
                        .setQuery(QueryBuilders.matchAllQuery()).setIndices(index)
                        .setTypes(type).setSize(1).setFrom(0).execute().get();
            } catch(Exception e){
                //Type isn't declared. Declaring type and not analyzing the externalUri to index
                client.admin().indices().prepareCreate(index)
                        .addMapping(this.type, "{" +
                                "    \"" + this.type + "\":{" +
                                "        \"properties\": {" +
                                "            \"externalUri\": {" +
                                "                \"type\": \"string\"," +
                                "                \"index\" : \"not_analyzed\"" +
                                "            }" +
                                "        }" +
                                "    }" +
                                "}")
                        .get();
            }
        }
    }

    @Override
    public Competence get(final String id) {
        try {
            final GetResponse response = client.prepareGet(index, type, id).get();
            return mapper.readValue(response.getSourceAsBytes(), Competence.class);
        } catch (IOException e) {
            LOG.error("Error getting Competence from Elasticsearch with id: " + id, e);
            return null;
        }
    }

    @Override
    public Competence getFromExternalURL(final String url) {
        if(url == null) return null;
        try {
            final SearchResponse response = client.prepareSearch().setQuery(QueryBuilders
                    .termQuery("externalUri", url)).setIndices(index).setTypes(type).setSize(1).execute().get();
            if(response.getHits().getTotalHits() > 0){
                return map(response.getHits().getAt(0));
            }
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Error trying to get Entry from Elasticsearch using URL: " + url, e);
        }
        return null;
    }

    private Competence map(final SearchHit hit){
        try {
            return mapper.readValue(hit.getSourceAsString(), Competence.class);
        } catch (IOException e) {
            LOG.error("Error parsing Search Hit from Elasticsearch to a competence: " + hit, e);
        }
        return null;
    }

    @Override
    public List<Competence> get(final int limit, final int offset) {
        final List<Competence> competences = Lists.newArrayList();
        try {
            final SearchResponse response = client.prepareSearch()
                    .setQuery(QueryBuilders.matchAllQuery()).setIndices(index)
                    .setTypes(type).setSize(limit).setFrom(offset).execute().get();
            for(SearchHit hit : response.getHits().getHits()){
                final Competence competence = map(hit);
                if(competence != null) competences.add(competence);
            }
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Error trying to get All entries from Elasticsearch: ", e);
        }
        return competences;
    }

    @Override
    public Competence update(final Competence competence) throws DatabaseActionException {
        if(competence == null) throw new DatabaseActionException("Missing competence to update in Elasticsearch ");
        try {
            client.prepareUpdate(index, type, competence.getId())
                    .setDoc(mapper.writeValueAsBytes(competence)).get();
        } catch (JsonProcessingException e) {
            throw new DatabaseActionException("Error trying to update Competence : " + competence.getId());
        }
        return competence;
    }

    @Override
    public Competence delete(final String id) throws DatabaseActionException {
        if(id == null) throw new DatabaseActionException("Missing id to delete from Elasticsearch");
        final Competence competence = get(id);
        if(competence == null) throw new DatabaseActionException("Id Invalid when trying to delete from Elasticsearch: " + id);
        LOG.info("Deleting entry from Elasticsearch: " + id);
        client.prepareDelete(index, type, id).get();
        return competence;
    }

    @Override
    public Competence create(final Competence competence) throws DatabaseActionException{
        if(competence == null) throw new DatabaseActionException("Missing competence to index into Elasticsearch");
        LOG.info("Indexing entry in Elasticsearch: " + competence.getId());
        try {
            client.prepareIndex(index, type, competence.getId())
                    .setSource(mapper.writeValueAsBytes(competence)).get();
            return competence;
        } catch(Exception e){
            throw new DatabaseActionException("Error indexing entry into Elasticsearch: " + competence.getId());
        }
    }

    @Override
    public List<Competence> search(final String query, final int limit, final int offset) {
        return null;
    }
}
