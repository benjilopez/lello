package ec.blopez.lello.services;

import java.util.List;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
public interface CompetenceService<T> {

    T get(final String id, final Class c);

    List<T> get(final List<String> ids, final Class c);

    List<T> get(final Class c);

    T update(final String id, final T competence, final Class c);

    T delete(final String id, final Class c);

    T create(final T competence);

    List<T> search(final String query);

}
