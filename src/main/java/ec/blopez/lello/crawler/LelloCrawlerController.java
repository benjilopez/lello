package ec.blopez.lello.crawler;

import ec.blopez.lello.Configurations;
import ec.blopez.lello.domain.CrawlerSite;
import ec.blopez.lello.services.CrawlerDBService;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.List;

/**
 * Created by benjilopez on 22/02/2017.
 */
@Controller
public class LelloCrawlerController extends CrawlController {

    private final static Logger LOG = LoggerFactory.getLogger(LelloCrawlerController.class);
    private final static int THREAD_COUNT = 7;

    private final LelloCrawlerFactory factory;

    private final CrawlerDBService crawlerDBService;

    @Autowired
    public LelloCrawlerController(final LelloCrawlConfig config, final LelloPageFetcher pageFetcher,
                                  final RobotstxtServer robotstxtServer, final LelloCrawlerFactory factory,
                                  final CrawlerDBService crawlerDBService) throws Exception {
        super(config, pageFetcher, robotstxtServer);
        this.factory = factory;
        this.crawlerDBService = crawlerDBService;
    }

    //Run every 10 Minutes - Initial delay: 10 seconds
    @Scheduled(fixedRate = 600000, initialDelay = 10000)
    public void start(){
        try {
            LOG.info("Initiating Crawler");
            final File file = new File(Configurations.CRAWLER_PATH);
            if(!file.exists()) file.mkdirs();

            final List<CrawlerSite> sites = crawlerDBService.getNotCrawledSites();
            if(sites != null){
                for(CrawlerSite site : sites) addSeed(site.getUrl());
            }

            start(factory, THREAD_COUNT);
            LOG.info("Crawler Finished");
        } catch(Exception e){
            LOG.error("Error running the Crawler", e);
        }
    }
}
