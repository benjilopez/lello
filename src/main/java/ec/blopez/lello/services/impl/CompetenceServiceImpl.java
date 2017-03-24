package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import ec.blopez.lello.Configurations;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.domain.CompetenceSearchResult;
import ec.blopez.lello.domain.Relationship;
import ec.blopez.lello.enums.RelationshipType;
import ec.blopez.lello.exceptions.DatabaseActionException;
import ec.blopez.lello.rest.ResponseKeys;
import ec.blopez.lello.services.CompetenceService;
import ec.blopez.lello.services.ControlService;
import ec.blopez.lello.services.ElasticsearchService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
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

    private BoolQueryBuilder getBoolQueryWithFilters(final String type, final String framework, final Boolean top){
        final QueryBuilder typeBuilder = getTermBuilder(ResponseKeys.TYPE, type);
        final QueryBuilder topBuilder = getTermBuilder(ResponseKeys.TOP, top);
        final QueryBuilder frameworkBuilder = getTermBuilder(ResponseKeys.FRAMEWORK, framework);
        final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if(typeBuilder != null) boolQuery.must(typeBuilder);
        if(topBuilder != null) boolQuery.must(topBuilder);
        if(frameworkBuilder != null) boolQuery.must(frameworkBuilder);
        return boolQuery;
    }

    @Override
    public CompetenceSearchResult get(final int limit, final int offset, final String type, final String framework, final Boolean top) {
        if((type == null) && (top == null) && (framework == null)) return elasticsearchService.get(limit, offset);
        return elasticsearchService.search(getBoolQueryWithFilters(type, framework, top), limit, offset);
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

    private MatchQueryBuilder localeSearch(final String key, final Locale locale, final String query){
        return QueryBuilders.matchQuery(key + "." + locale.getLanguage(), query);
    }

    @Override
    public CompetenceSearchResult search(final String query, final int limit, final int offset, final Locale locale,
                                         final String type, final String framework, final Boolean top) {
        final BoolQueryBuilder boolQuery = getBoolQueryWithFilters(type, framework, top);
        final MatchQueryBuilder nameQuery = localeSearch(ResponseKeys.NAME, locale, query);
        final MatchQueryBuilder otherQuery = localeSearch(ResponseKeys.OTHER_NAME, locale, query);
        final MatchQueryBuilder definitionQuery = localeSearch(ResponseKeys.DEFINITION, locale, query);
        final MatchQueryBuilder externalUrl = QueryBuilders.matchQuery(ResponseKeys.EXTERNAL_URL , query);
        final MatchQueryBuilder externalId = QueryBuilders.matchQuery(ResponseKeys.EXTERNAL_ID , query);
        return elasticsearchService.search(boolQuery.should(nameQuery).should(otherQuery).should(definitionQuery)
                .should(externalUrl).should(externalId), limit, offset);
    }

    @Override
    public boolean create(final Relationship relationship){
        if((relationship == null) || ((relationship.getId() == null) && (relationship.getExternalUrl() == null))) return false;
        final String sourceId = relationship.getSourceId();
        final String sourceExternalUrl = relationship.getSourceExternaUrl();
        final Competence source = (sourceId != null) ? elasticsearchService.get(sourceId) :
                elasticsearchService.getFromExternalURL(sourceExternalUrl);
        if(source == null) return false;
        final Relationship sourceRelationship = controlService.checkDouble(source, relationship);

        try{
            if(sourceRelationship != null) {
                elasticsearchService.update(source);
                return true;
            }

            if(relationship.getType() == RelationshipType.EXTERNAL) {
                source.addRelated(relationship);
                elasticsearchService.update(source);
                return true;
            }

            final String externalUrl = relationship.getExternalUrl();
            final Competence relatedCompetence = elasticsearchService.getFromExternalURL(externalUrl);
            if(relatedCompetence == null) return false;

            final Relationship newRelationship = new Relationship();

            relationship.setId(relatedCompetence.getUri());
            newRelationship.setId(source.getUri());
            relationship.setCode(relatedCompetence.getId());
            newRelationship.setCode(source.getId());
            newRelationship.setExternalUrl(relatedCompetence.getExternalUri());


            switch (relationship.getType()){
                case RELATED:
                    newRelationship.setType(RelationshipType.RELATED);
                    break;
                case CHILD:
                    newRelationship.setType(RelationshipType.PARENT);
                    break;
                case PARENT:
                    newRelationship.setType(RelationshipType.CHILD);
                    break;
            }
            source.addRelated(relationship);
            elasticsearchService.update(source);
            relatedCompetence.addRelated(newRelationship);
            elasticsearchService.update(relatedCompetence);
            return true;

        } catch (DatabaseActionException e) {
            LOG.error("Error trying to update the update the entry in the database;");
            return false;
        }

    }
}
