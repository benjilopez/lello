package ec.blopez.lello.crawler;

import com.google.common.collect.Lists;
import ec.blopez.lello.crawler.esco.ESCOParser;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.services.CompetenceService;
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
    final CompetenceService<Competence> competenceService;
    final List<LelloParser> lelloParserList = Lists.newArrayList();

    public LelloCrawler(final CompetenceService competenceService){
        this.competenceService = competenceService;
        lelloParserList.add(new ESCOParser());
    }

    @Override
    public boolean shouldVisit(final Page referringPage, final WebURL url) {
        final String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches();
    }

    @Override
    public void visit(final Page page) {
        final String url = page.getWebURL().getURL();
        for (LelloParser lelloParser : lelloParserList) {
            try {
                List<Competence> competences = null;
                if (page.getParseData() instanceof TextParseData) competences = lelloParser.parse(new URL(url));
                if (page.getParseData() instanceof HtmlParseData) competences = lelloParser.parse(
                        (HtmlParseData) page.getParseData());
                if(competences != null){
                    for(Competence competence : competences) competenceService.create(competence);
                }
            } catch (final Exception e) {
                LOG.error("Error parsing URL: " + url);
            }
        }
    }
}