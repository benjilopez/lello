package ec.blopez.lello.services;

import ec.blopez.lello.domain.CrawlerSite;

import java.util.List;

/**
 * Created by Benjamin Lopez on 21/03/2017.
 */
public interface CrawlerDBService {

    List<CrawlerSite> getNotCrawledSites();

    int markedAsCrawled(final String url);

}
