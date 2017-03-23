package ec.blopez.lello.services;

import ec.blopez.lello.crawler.esco.domain.CompetenceType;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.domain.CompetenceSearchResult;

import java.util.List;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
public interface CompetenceService {

    Competence get(final String id);

    List<Competence> get(final List<String> ids);

    CompetenceSearchResult get(final int limit, final int offset, final String type, final String framework, final Boolean top);

    Competence update(final String id, final Competence competence);

    Competence delete(final String id);

    Competence create(final Competence competence);

    CompetenceSearchResult search(final String query, final int limit, final int offset);

}
