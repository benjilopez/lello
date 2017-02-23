package ec.blopez.lello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by benjilopez on 22/02/2017.
 */
@Component
public class Configurations {

    public static String URL;

    public static String PATH;

    public static String CRAWLER_PATH;

    @Autowired
    public Configurations(@Value("${lello.url.production}") final String urlProd,
                          @Value("${lello.url.development}") final String urlDev,
                          @Value("${lello.path}") final String path,
                          @Value("${lello.environment}") final String environment,
                          @Value("${lello.crawler.path}") final String crawlerPath){
        URL = "PRODUCTION".equals(environment)? urlProd : urlDev;
        PATH = path;
        CRAWLER_PATH = crawlerPath;
    }
}
