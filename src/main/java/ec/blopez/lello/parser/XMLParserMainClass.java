package ec.blopez.lello.parser;

import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.domain.Skill;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Benjamin Lopez on 03/02/2017.
 */
@XmlRootElement(name="EscoInterchange")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLParserMainClass {

    @XmlElementWrapper(name="Thesaurus")
    @XmlElement(name="ThesaurusConcept")
    private List<Skill> skills;

    @XmlElement(name="Export")
    private Export export;

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public Export getExport() {
        return export;
    }

    public void setExport(Export export) {
        this.export = export;
    }
}
