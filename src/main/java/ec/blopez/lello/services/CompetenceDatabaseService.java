package ec.blopez.lello.services;

import ec.blopez.lello.domain.Competence;

/**
 * Created by Benjamin Lopez on 31/01/2017.
 */
public interface CompetenceDatabaseService {

    public Competence get(final String id);
}
