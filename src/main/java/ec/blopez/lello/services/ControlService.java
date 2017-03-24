package ec.blopez.lello.services;

import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.domain.Relationship;

import java.util.List;

/**
 * Created by Benjamin Lopez on 04/03/2017.
 */
public interface ControlService {

    Competence checkDouble(final Competence competence);

    Relationship checkRelated(final Competence competence);

    Relationship checkDouble(final Competence competence, final Relationship relationship);
}
