package ec.blopez.lello.services;

import ec.blopez.lello.domain.Esco;
import ec.blopez.lello.exceptions.DatabaseActionException;

import java.util.List;

/**
 * Created by Benjamin Lopez on 31/01/2017.
 */
public interface DatabaseService {

    Esco get(final String id);

    List<Esco> get();

    Esco update(final Esco esco) throws DatabaseActionException;

    Esco delete(final String id) throws DatabaseActionException;

    Esco create(final Esco esco) throws DatabaseActionException;

    List<Esco> search(final String query);
}
