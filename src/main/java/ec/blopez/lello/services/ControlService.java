package ec.blopez.lello.services;

import ec.blopez.lello.domain.Competence;

import java.util.List;

/**
 * Created by Benjamin Lopez on 04/03/2017.
 */
public interface ControlService {

    Competence checkDouble(final Competence competence);

    List<Competence> checkRelated(final Competence competence);
}
