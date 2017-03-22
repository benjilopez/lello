package ec.blopez.lello.services.impl;

import ec.blopez.lello.domain.CrawlerSite;
import ec.blopez.lello.services.CrawlerDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Benjamin Lopez on 15/03/2017.
 */
@Service
public class CrawlerDBServiceImpl implements CrawlerDBService {

    @Autowired
    private JdbcTemplate mysqlService;

    private RowMapper<CrawlerSite> map() {
        return (rs, rowNum) -> {
            final CrawlerSite.Builder builder = new CrawlerSite.Builder();
            builder .setId(rs.getLong("id"))
                    .setUrl(rs.getString("url"))
                    .setLastScanned(rs.getDate("last_scanned"))
                    .setCreated(rs.getDate("created"))
                    .setLastModified(rs.getDate("last_modified"));
            return builder.build();
        };
    }

    @Override
    public List<CrawlerSite> getNotCrawledSites() {
        final String query = "SELECT * FROM `lello`.`crawler_sites` " +
                "                     WHERE `last_scanned` IS NULL" +
                "                  ORDER BY `created`";
        return mysqlService.query(query, map());
    }

    @Override
    public int markedAsCrawled(final String url){
        final String query = "UPDATE `lello`.`crawler_sites`" +
                "                SET `last_scanned` = NOW()," +
                "                    `last_modified` = NOW()" +
                "              WHERE `last_scanned` IS NULL" +
                "                AND `url` = ?";
        return mysqlService.update(query, new Object[]{url});
    }
}
