package ec.blopez.lello.xml.domain;

import com.google.common.collect.Lists;
import ec.blopez.lello.domain.Skill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ThesaurusConcept {

    @XmlElement(name="uri")
    private String uri;

    @XmlElement(name="type")
    private List<String> types;

    @XmlElement(name="identifier", namespace="http://purl.org/dc/elements/1.1/")
    private String identifier;

    @XmlElement(name="status")
    private String status;

    @XmlElement(name="topConcept")
    private boolean topConcept;

    @XmlElementWrapper(name="PreferredTerm")
    @XmlElement(name="lexicalValue")
    private List<LexicalValue> preferredTerm;

    @XmlElementWrapper(name="SimpleNonPreferredTerm")
    @XmlElement(name="lexicalValue")
    private List<LexicalValue> simpleNonPreferredTerm;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isTopConcept() {
        return topConcept;
    }

    public void setTopConcept(boolean topConcept) {
        this.topConcept = topConcept;
    }

    public List<LexicalValue> getPreferredTerm() {
        return preferredTerm;
    }

    public void setPreferredTerm(final List<LexicalValue> preferredTerm) {
        this.preferredTerm = preferredTerm;
    }

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

        ThesaurusConcept that = (ThesaurusConcept) o;

        if (topConcept != that.topConcept) return false;
        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;
        if (types != null ? !types.equals(that.types) : that.types != null) return false;
        if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        return preferredTerm != null ? preferredTerm.equals(that.preferredTerm) : that.preferredTerm == null;
    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (types != null ? types.hashCode() : 0);
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (topConcept ? 1 : 0);
        result = 31 * result + (preferredTerm != null ? preferredTerm.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ThesaurusConcept{" +
                "uri='" + uri + '\'' +
                ", types=" + types +
                ", identifier='" + identifier + '\'' +
                ", status='" + status + '\'' +
                ", topConcept=" + topConcept +
                ", preferredTerm=" + preferredTerm +
                '}';
    }

    public Skill toSkill(){
        final Skill.Builder builder = new Skill.Builder();
        final List<ec.blopez.lello.domain.LexicalValue> simpleNonPreferredTerms = Lists.newArrayList();
        final List<ec.blopez.lello.domain.LexicalValue> preferredTerms = Lists.newArrayList();
        if(simpleNonPreferredTerm != null) for(LexicalValue value : simpleNonPreferredTerm) simpleNonPreferredTerms.add(value.toLexicalValue());
        if(preferredTerm != null) for(LexicalValue value : preferredTerm) preferredTerms.add(value.toLexicalValue());
        builder .setSimpleNonPreferredTerm(simpleNonPreferredTerms)
                .setPreferredTerm(preferredTerms)
                .setTopConcept(topConcept)
                .setStatus(status)
                .setIdentifier(identifier)
                .setTypes(types)
                .setUri(uri);
        return builder.build();
    }
}
