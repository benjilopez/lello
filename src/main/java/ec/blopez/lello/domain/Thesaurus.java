package ec.blopez.lello.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by Benjamin Lopez on 04/02/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Thesaurus {

    @XmlElement(name="HierarchicalRelationship")
    private List<Relationship> relationships;

    @XmlElement(name="ThesaurusConcept")
    private List<Skill> skills;

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
