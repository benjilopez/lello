package ec.blopez.lello.crawler.esco.domain;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by benjilopez on 21/02/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ISCOGroup {

    @XmlElement(name="uri")
    private String uri;

    @XmlElement(name="notation")
    private String notation;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ISCOGroup iscoGroup = (ISCOGroup) o;

        if (uri != null ? !uri.equals(iscoGroup.uri) : iscoGroup.uri != null) return false;
        return notation != null ? notation.equals(iscoGroup.notation) : iscoGroup.notation == null;
    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (notation != null ? notation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ISCOGroup{" +
                "uri='" + uri + '\'' +
                ", notation='" + notation + '\'' +
                '}';
    }

}
