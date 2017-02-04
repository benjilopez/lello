package ec.blopez.lello.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Benjamin Lopez on 03/02/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Export {

    @XmlAttribute(name="uri")
    private String uri;

    @XmlAttribute(name="identifier", namespace="http://purl.org/dc/elements/1.1/")
    private String identifier;

    @XmlAttribute(name="version")
    private String version;

    @XmlAttribute(name="date")
    private String date;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
