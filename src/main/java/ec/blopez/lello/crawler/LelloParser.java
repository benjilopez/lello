package ec.blopez.lello.crawler;

import ec.blopez.lello.crawler.esco.ESCOParser;
import ec.blopez.lello.crawler.esco.domain.ESCOXmlRootElement;
import ec.blopez.lello.domain.ParserResult;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Date;

/**
 * Created by benjilopez on 31/01/2017.
 */
public abstract class LelloParser<T> {


    public ParserResult parseXML(final String url, final String content){
        final Date startDate = new Date();
        final ParserResult result;
        getLog().info("Parsing Content for URL: " + url.toString());
        try {
            final JAXBContext jc = JAXBContext.newInstance(getXmlRootClass());
            final Unmarshaller unmarshaller = jc.createUnmarshaller();
            result = parse((T) unmarshaller.unmarshal(new StringReader(content)));
        } catch (JAXBException e) {
            getLog().error("Error parsing URL: " + url, e);
            return null;
        }
        final Long timeDifference = (new Date()).getTime() - startDate.getTime();
        getLog().info("Parsing URL Finished: " + url + " in " + timeDifference + " ms");
        return result;
    }

    abstract protected ParserResult parse(T xmlFile);

    public ParserResult parseHTML(final HtmlParseData htmlParseDate) {
        return null;
    };

    abstract protected Logger getLog();

    abstract protected Class getXmlRootClass();
}
