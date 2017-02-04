package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * Created by benjilopez on 31/01/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Skill extends Competence {

    @JsonProperty("simpleNonPreferredTerm")
    @XmlElementWrapper(name="SimpleNonPreferredTerm")
    @XmlElement(name="lexicalValue")
    private List<LexicalValue> simpleNonPreferredTerm;

    public List<LexicalValue> getSimpleNonPreferredTerm() {
        return simpleNonPreferredTerm;
    }

    public void setSimpleNonPreferredTerm(final List<LexicalValue> simpleNonPreferredTerm) {
        this.simpleNonPreferredTerm = simpleNonPreferredTerm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Skill skill = (Skill) o;

        return simpleNonPreferredTerm != null ? simpleNonPreferredTerm.equals(skill.simpleNonPreferredTerm) : skill.simpleNonPreferredTerm == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (simpleNonPreferredTerm != null ? simpleNonPreferredTerm.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "simpleNonPreferredTerm=" + simpleNonPreferredTerm +
                '}';
    }
}
