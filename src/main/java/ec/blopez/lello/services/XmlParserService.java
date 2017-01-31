package ec.blopez.lello.services;

import ec.blopez.lello.domain.Competence;

import java.util.Map;

/**
 * Created by benjilopez on 31/01/2017.
 */
public interface XmlParserService {

    Map<String, Competence> load();
}
