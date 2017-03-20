package ec.blopez.lello.crawler;

import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.services.CompetenceService;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by benjilopez on 23/02/2017.
 */
@Component
public class LelloCrawlerFactory implements CrawlController.WebCrawlerFactory {


    @Autowired
    private CompetenceService competenceService;

    @Override
    public WebCrawler newInstance() throws Exception {
        return new LelloCrawler(competenceService);
    }
}
