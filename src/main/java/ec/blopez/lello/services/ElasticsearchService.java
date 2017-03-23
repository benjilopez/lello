package ec.blopez.lello.services;

import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.domain.CompetenceSearchResult;
import ec.blopez.lello.exceptions.DatabaseActionException;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * Created by Benjamin Lopez on 31/01/2017.
 */
public interface ElasticsearchService {

    Competence get(final String id);

    Competence getFromExternalURL(final String url);

    CompetenceSearchResult get(final int limit, final int offset);

    Competence update(final Competence competence) throws DatabaseActionException;

    Competence delete(final String id) throws DatabaseActionException;

    Competence create(final Competence competence) throws DatabaseActionException;

    CompetenceSearchResult search(final QueryBuilder query, final int limit, final int offset);
}
