package ec.blopez.lello.crawler;

import ec.blopez.lello.Configurations;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by benjilopez on 23/02/2017.
 */
@Component
public class LelloCrawlConfig extends CrawlConfig {

    @Autowired
    public LelloCrawlConfig(final Configurations configurations){
        super();
        setCrawlStorageFolder(configurations.CRAWLER_PATH);
    }
}
