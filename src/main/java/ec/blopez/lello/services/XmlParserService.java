package ec.blopez.lello.services;

import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.xml.domain.XMLParserMainClass;

import java.io.File;
import java.net.URL;
import java.util.Map;

/**
 * Created by benjilopez on 31/01/2017.
 */
public interface XmlParserService {

    void parse(final URL url);

    void parse(final File folder);

    void parse(final XMLParserMainClass xmlFile);
}
