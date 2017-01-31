package ec.blopez.lello.services;

import ec.blopez.lello.domain.Competence;

import java.util.List;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
public interface CompetencesService {

    Competence get(final String id);

    List<Competence> get(final List<String> ids);

    List<Competence> get();

    Competence update(final String id, final Competence competence);

    Competence delete(final String id);

    Competence create(final Competence competence);

    List<Competence> search(final String query);

}
