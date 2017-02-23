package ec.blopez.lello.crawler;

import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by benjilopez on 23/02/2017.
 */
@Component
public class LelloRobotstxtServer extends RobotstxtServer {

    @Autowired
    public LelloRobotstxtServer(final LelloPageFetcher pageFetcher) {
        super(new RobotstxtConfig(), pageFetcher);
    }
}
