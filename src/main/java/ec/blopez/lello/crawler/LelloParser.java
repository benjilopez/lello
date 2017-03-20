package ec.blopez.lello.crawler;

import ec.blopez.lello.domain.Competence;
import edu.uci.ics.crawler4j.parser.HtmlParseData;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created by benjilopez on 31/01/2017.
 */
public interface LelloParser<T> {

    List<Competence> parse(final URL url);

    List<Competence> parse(final File folder);

    List<Competence> parse(T xmlFile);

    List<Competence> parse(final HtmlParseData htmlParseDate);
}
