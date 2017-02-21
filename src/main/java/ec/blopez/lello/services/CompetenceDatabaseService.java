package ec.blopez.lello.services;

import ec.blopez.lello.exceptions.DatabaseActionException;
import ec.blopez.lello.domain.Competence;

import java.util.List;

/**
 * Created by Benjamin Lopez on 31/01/2017.
 */
public interface CompetenceDatabaseService {

    Competence get(final String id);

    List<Competence> get(final List<String> ids);

    List<Competence> get();

    Competence update(final Competence competence) throws DatabaseActionException;

    Competence delete(final String id) throws DatabaseActionException;

    Competence create(final Competence competence) throws DatabaseActionException;

    List<Competence> search(final String query);
}
