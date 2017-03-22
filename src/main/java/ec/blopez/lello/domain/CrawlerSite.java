package ec.blopez.lello.domain;

import java.util.Date;

/**
 * Created by Benjamin Lopez on 21/03/2017.
 */
public class CrawlerSite {

    private long id;

    private String url;

    private Date lastScanned;

    private Date created;

    private Date lastModified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getLastScanned() {
        return lastScanned;
    }

    public void setLastScanned(Date lastScanned) {
        this.lastScanned = lastScanned;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public static class Builder{
        private long id;
        private String url;
        private Date lastScanned;
        private Date created;
        private Date lastModified;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setLastScanned(Date lastScanned) {
            this.lastScanned = lastScanned;
            return this;
        }

        public Builder setCreated(Date created) {
            this.created = created;
            return this;
        }

        public Builder setLastModified(Date lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public CrawlerSite build(){
            final CrawlerSite result = new CrawlerSite();
            result.setId(id);
            result.setUrl(url);
            result.setLastScanned(lastScanned);
            result.setCreated(created);
            result.setLastModified(lastModified);
            return result;
        }
    }

}
