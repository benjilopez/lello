package ec.blopez.lello.crawler.esco.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Benjamin Lopez on 04/02/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Relationship {

    @XmlElement(name="role")
    private String role;

    @XmlElement(name="isHierRelConcept")
    private String childUri;

    @XmlElement(name="hasHierRelConcept")
    private String parentUri;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getChildUri() {
        return childUri;
    }

    public void setChildUri(String childUri) {
        this.childUri = childUri;
    }

    public String getParentUri() {
        return parentUri;
    }

    public void setParentUri(String parentUri) {
        this.parentUri = parentUri;
    }
}
