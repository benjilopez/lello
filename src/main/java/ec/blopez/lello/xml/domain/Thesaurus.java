package ec.blopez.lello.xml.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by Benjamin Lopez on 04/02/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Thesaurus {

    @XmlElement(name="title", namespace="http://purl.org/dc/elements/1.1/")
    private String title;

    @XmlElement(name="HierarchicalRelationship")
    private List<Relationship> relationships;

    @XmlElement(name="ThesaurusConcept")
    private List<ThesaurusConcept> thesaurusConcepts;

    public String getTitle(){
        return title;
    }

    public void setTitle(final String title){
        this.title = title;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

    public List<ThesaurusConcept> getThesaurusConcepts() {
        return thesaurusConcepts;
    }

    public void setThesaurusConcepts(List<ThesaurusConcept> thesaurusConcepts) {
        this.thesaurusConcepts = thesaurusConcepts;
    }
}
