package ec.blopez.lello.crawler;

import ec.blopez.lello.domain.ParserResult;
import edu.uci.ics.crawler4j.parser.HtmlParseData;

import java.io.File;
import java.net.URL;

/**
 * Created by benjilopez on 31/01/2017.
 */
public interface LelloParser<T> {

    ParserResult parse(final URL url);

    ParserResult parse(final File folder);

    ParserResult parse(T xmlFile);

    ParserResult parse(final HtmlParseData htmlParseDate);
}
