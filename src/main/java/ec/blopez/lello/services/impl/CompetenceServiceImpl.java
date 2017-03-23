package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import ec.blopez.lello.Configurations;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.domain.CompetenceSearchResult;
import ec.blopez.lello.exceptions.DatabaseActionException;
import ec.blopez.lello.rest.ResponseKeys;
import ec.blopez.lello.services.CompetenceService;
import ec.blopez.lello.services.ControlService;
import ec.blopez.lello.services.ElasticsearchService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
@Service
public class CompetenceServiceImpl implements CompetenceService {

    private final static Logger LOG = LoggerFactory.getLogger(CompetenceServiceImpl.class);


    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private ControlService controlService;

    @Override
    public Competence get(final String id) {
        if (id == null) return null;
        return elasticsearchService.get(id);
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

    private QueryBuilder getTermBuilder(final String term, final Object value){
        return (value == null) ? null : QueryBuilders.termQuery(term, value.toString());
    }

    @Override
    public CompetenceSearchResult get(final int limit, final int offset, final String type, final String framework, final Boolean top) {
        if((type == null) && (top == null)) return elasticsearchService.get(limit, offset);
        final QueryBuilder typeBuilder = getTermBuilder(ResponseKeys.TYPE, type);
        final QueryBuilder topBuilder = getTermBuilder(ResponseKeys.TOP, top);
        final QueryBuilder frameworkBuilder = getTermBuilder(ResponseKeys.FRAMEWORK, framework);
        final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if(typeBuilder != null) boolQuery.must(typeBuilder);
        if(topBuilder != null) boolQuery.must(topBuilder);
        if(frameworkBuilder != null) boolQuery.must(frameworkBuilder);
        return elasticsearchService.search(boolQuery, limit, offset);
    }

    @Override
    public Competence update(final String id, final Competence competence) {
        if((competence == null) || (id == null) || (get(id) == null)) return null;
        competence.setId(id);
        try {
            return elasticsearchService.update(competence);
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
            return elasticsearchService.delete(id);
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
                return elasticsearchService.update(competenceToUpdate);
            } else {
                final UUID id = UUID.randomUUID();
                final String uri = Configurations.URL + Configurations.PATH + "competences/" + id.toString();
                competence.setId(id.toString());
                competence.setUri(uri);
                return elasticsearchService.create(competence);
            }
        } catch (DatabaseActionException e) {
            LOG.error("Error trying to create new competence in the database.", e);
        }
        return null;
    }

    @Override
    public CompetenceSearchResult search(final String query, final int limit, final int offset) {
        QueryBuilder builder = QueryBuilders.matchAllQuery();
        return elasticsearchService.search(builder, limit, offset);
    }
}
