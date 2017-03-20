package ec.blopez.lello.crawler.esco.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by benjilopez on 21/02/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class HasAwardingBody {

    @XmlElement(name="uri")
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HasAwardingBody that = (HasAwardingBody) o;

        return uri != null ? uri.equals(that.uri) : that.uri == null;
    }

    @Override
    public int hashCode() {
        return uri != null ? uri.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "HasAwardingBody{" +
                "uri='" + uri + '\'' +
                '}';
    }
}
