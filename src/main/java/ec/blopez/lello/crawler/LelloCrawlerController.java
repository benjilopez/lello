package ec.blopez.lello.crawler;

import ec.blopez.lello.Configurations;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.File;

/**
 * Created by benjilopez on 22/02/2017.
 */
@Controller
public class LelloCrawlerController extends CrawlController {

    private final static Logger LOG = LoggerFactory.getLogger(LelloCrawlerController.class);
    private final static int THREAD_COUNT = 7;

    private final LelloCrawlerFactory factory;

    @Autowired
    public LelloCrawlerController(final LelloCrawlConfig config, final LelloPageFetcher pageFetcher,
                                  final RobotstxtServer robotstxtServer, final LelloCrawlerFactory factory) throws Exception {
        super(config, pageFetcher, robotstxtServer);
        this.factory = factory;
    }

    //Run every 10 Minutes - Initial delay: 10 seconds
    @Scheduled(fixedRate = 600000, initialDelay = 10000)
    public void start(){
        try {
            LOG.info("Init Crawler");
            final File file = new File(Configurations.CRAWLER_PATH);
            if(!file.exists()) file.mkdirs();

            addSeed("https://ec.europa.eu/esco/resources/edcat/storage/77/7706bac4-2662-4f9c-a82e-8e3d41ac7e4e.xml");
            addSeed("https://ec.europa.eu/esco/resources/edcat/storage/0b/0bb0e952-20b0-4705-b160-7d8e7c2e73bd.xml");
            addSeed("https://ec.europa.eu/esco/resources/edcat/storage/9f/9fc9a1d1-ab65-49ba-b73e-bd9ba5bd42a1.xml");

            start(factory, THREAD_COUNT);
            LOG.info("Crawler Finished");
        } catch(Exception e){
            LOG.error("Error running the Crawler", e);
        }
    }
}
