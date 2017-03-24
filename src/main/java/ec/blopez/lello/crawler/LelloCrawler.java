package ec.blopez.lello.crawler;

import com.google.common.collect.Lists;
import ec.blopez.lello.crawler.esco.ESCOParser;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
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
        return (referringPage == null) && !FILTERS.matcher(href).matches();
    }

    @Override
    public void visit(final Page page) {
        final String url = page.getWebURL().getURL();
        boolean isDataParsed = false;
        for (LelloParser lelloParser : lelloParserList) {
            try {
                ParserResult result = null;
                if (page.getParseData() instanceof TextParseData) result = lelloParser.parse(new URL(url));
                if (page.getParseData() instanceof HtmlParseData) result = lelloParser.parse(
                        (HtmlParseData) page.getParseData());
                if(result != null){
                    for(Competence competence : result.getCompetences()) competenceService.create(competence);
                    for(Relationship relationship : result.getRelationships()) competenceService.create(relationship);
                }
                isDataParsed = true;
            } catch (final Exception e) {
                LOG.error("Error parsing URL: " + url);
            }
        }
        if(isDataParsed) crawlerDBService.markedAsCrawled(url);
    }
}
