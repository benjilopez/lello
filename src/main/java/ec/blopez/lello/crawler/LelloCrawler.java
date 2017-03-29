package ec.blopez.lello.crawler;

import com.google.common.collect.Lists;
import ec.blopez.lello.crawler.esco.ESCOParser;
import ec.blopez.lello.crawler.esco.domain.ESCOXmlRootElement;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.domain.ParserResult;
import ec.blopez.lello.domain.Relationship;
import ec.blopez.lello.services.CompetenceService;
import ec.blopez.lello.services.CrawlerDBService;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.TextParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by benjilopez on 22/02/2017.
 */
public class LelloCrawler extends WebCrawler {

    private final static Logger LOG = LoggerFactory.getLogger(LelloCrawler.class);
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz))$");
    final CompetenceService competenceService;
    final CrawlerDBService crawlerDBService;
    final List<LelloParser> lelloParserList = Lists.newArrayList();

    public LelloCrawler(final CompetenceService competenceService, final CrawlerDBService crawlerDBService){
        this.competenceService = competenceService;
        this.crawlerDBService = crawlerDBService;
        lelloParserList.add(new ESCOParser());
    }

    @Override
    public boolean shouldVisit(final Page referringPage, final WebURL url) {
        final String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches();
    }

    @Override
    public void visit(final Page page) {
        int competenceCounter = 0;
        int relationshipCounter = 0;
        final String url = page.getWebURL().getURL();
        final Date started = new Date();
        LOG.info("Starting visit for Crawler " + getMyId() + " with URL: " + url);
        boolean isDataParsed = false;
        for (LelloParser lelloParser : lelloParserList) {
            try {
                ParserResult result = null;
                if (ContentType.TEXT_XML.getMimeType().equals(page.getContentType())){
                    result = lelloParser.parseXML(url, ((TextParseData) page.getParseData()).getTextContent());
                }
                if (page.getParseData() instanceof HtmlParseData) result = lelloParser.parseHTML(
                        (HtmlParseData) page.getParseData());
                if(result != null){

                    for(Competence competence : result.getCompetences()){
                        if(competenceService.create(competence) != null) competenceCounter++;
                    }
                    for(Relationship relationship : result.getRelationships()) {
                        if(competenceService.create(relationship)) relationshipCounter ++;
                    }
                }
                isDataParsed = true;
            } catch (final Exception e) {
                LOG.error("Error parsing URL: " + url);
            }
        }
        if(isDataParsed) crawlerDBService.markedAsCrawled(url);
        final Long timeDifference = (new Date()).getTime() - started.getTime();
        LOG.info("Visit for Crawler " + getMyId() + " with URL: " + url + " finished in " + timeDifference + " ms. " +
                "Competences Added: " + competenceCounter + ", Relationships Added: " + relationshipCounter);
    }
}
