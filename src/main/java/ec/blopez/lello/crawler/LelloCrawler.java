package ec.blopez.lello.crawler;

import ec.blopez.lello.services.XmlParserService;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.TextParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by benjilopez on 22/02/2017.
 */
public class LelloCrawler extends WebCrawler {

    private final static Logger LOG = LoggerFactory.getLogger(LelloCrawler.class);
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz))$");
    final XmlParserService xmlParserService;

    public LelloCrawler(final XmlParserService xmlParserService){
        this.xmlParserService = xmlParserService;
    }

    @Override
    public boolean shouldVisit(final Page referringPage, final WebURL url) {
        final String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches();
    }

    @Override
    public void visit(final Page page) {
        final String url = page.getWebURL().getURL();
        if(page.getParseData() instanceof TextParseData){
            try {
                xmlParserService.parse(new URL(url));
            } catch (final Exception e) {
                LOG.error("Error parsing URL: " + url);
            }
        }

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            System.out.println("Text length: " + text.length());
            System.out.println("Html length: " + html.length());
            System.out.println("Number of outgoing links: " + links.size());
        }
    }
}
