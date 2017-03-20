package ec.blopez.lello.crawler.esco.domain;

import ec.blopez.lello.crawler.LelloElement;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Benjamin Lopez on 03/02/2017.
 */
@XmlRootElement(name="EscoInterchange")
@XmlAccessorType(XmlAccessType.FIELD)
public class ESCOXmlRootElement extends LelloElement{

    @XmlElement(name="Thesaurus")
    private List<Thesaurus> thesauruses;

    @XmlElement(name="Export")
    private Export export;

    @XmlElement(name="AssociativeRelationship")
    private List<AssociativeRelationship> relationships;

    public List<Thesaurus> getThesauruses() {
        return thesauruses;
    }

    public void setThesauruses(List<Thesaurus> thesauruses) {
        this.thesauruses = thesauruses;
    }

    public Export getExport() {
        return export;
    }

    public void setExport(Export export) {
        this.export = export;
    }

    public List<AssociativeRelationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<AssociativeRelationship> relationships) {
        this.relationships = relationships;
    }
}
