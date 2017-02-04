package ec.blopez.lello.domain;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Benjamin Lopez on 03/02/2017.
 */
@XmlRootElement(name="EscoInterchange")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLParserMainClass {

    @XmlElement(name="Thesaurus")
    private Thesaurus thesaurus;

    @XmlElement(name="Export")
    private Export export;

    public List<Skill> getSkills() {
        if(thesaurus == null) return Lists.newArrayList();
        return thesaurus.getSkills();
    }

    public Export getExport() {
        return export;
    }

    public void setExport(Export export) {
        this.export = export;
    }

    public List<Relationship> getRelationships() {
        if(thesaurus == null) return Lists.newArrayList();
        return thesaurus.getRelationships();
    }

}
