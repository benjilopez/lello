package ec.blopez.lello.xml.domain;

import com.google.common.collect.Lists;
import ec.blopez.lello.enums.XMLType;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Benjamin Lopez on 03/02/2017.
 */
@XmlRootElement(name="EscoInterchange")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLParserMainClass {

    @XmlElement(name="Thesaurus")
    private List<Thesaurus> thesauruses;

    @XmlElement(name="Export")
    private Export export;

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
}
