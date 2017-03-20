package ec.blopez.lello.crawler.esco.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by benjilopez on 21/02/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AssociativeRelationship {

    @XmlElement(name="role")
    private String role;

    @XmlElement(name="isRelatedConcept")
    private String isRelatedConcept;

    @XmlElement(name="hasRelatedConcept")
    private String hasRelatedConcept;

    @XmlElement(name="description")
    private LexicalValue description;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIsRelatedConcept() {
        return isRelatedConcept;
    }

    public void setIsRelatedConcept(String isRelatedConcept) {
        this.isRelatedConcept = isRelatedConcept;
    }

    public String getHasRelatedConcept() {
        return hasRelatedConcept;
    }

    public void setHasRelatedConcept(String hasRelatedConcept) {
        this.hasRelatedConcept = hasRelatedConcept;
    }

    public LexicalValue getDescription() {
        return description;
    }

    public void setDescription(LexicalValue description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssociativeRelationship that = (AssociativeRelationship) o;

        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        if (isRelatedConcept != null ? !isRelatedConcept.equals(that.isRelatedConcept) : that.isRelatedConcept != null)
            return false;
        if (hasRelatedConcept != null ? !hasRelatedConcept.equals(that.hasRelatedConcept) : that.hasRelatedConcept != null)
            return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = role != null ? role.hashCode() : 0;
        result = 31 * result + (isRelatedConcept != null ? isRelatedConcept.hashCode() : 0);
        result = 31 * result + (hasRelatedConcept != null ? hasRelatedConcept.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AssociativeRelationship{" +
                "role='" + role + '\'' +
                ", isRelatedConcept='" + isRelatedConcept + '\'' +
                ", hasRelatedConcept='" + hasRelatedConcept + '\'' +
                ", description=" + description +
                '}';
    }
}
