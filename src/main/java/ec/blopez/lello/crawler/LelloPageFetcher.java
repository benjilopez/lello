package ec.blopez.lello.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by benjilopez on 23/02/2017.
 */
@Component
public class LelloPageFetcher extends PageFetcher {

    @Autowired
    public LelloPageFetcher(final LelloCrawlConfig config) {
        super(config);
    }
}
