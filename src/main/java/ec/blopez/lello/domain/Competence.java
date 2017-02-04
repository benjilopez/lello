package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Competence {

    @JsonProperty("uri")
    @XmlElement(name="uri")
    private String uri;

    @JsonProperty("types")
    @XmlElement(name="type")
    private List<String> types;

    @JsonProperty("identifier")
    @XmlElement(name="identifier", namespace="http://purl.org/dc/elements/1.1/")
    private String identifier;

    @JsonProperty("status")
    @XmlElement(name="status")
    private String status;

    @JsonProperty("topConcept")
    @XmlElement(name="topConcept")
    private boolean topConcept;

    @JsonProperty("preferredTerm")
    @XmlElementWrapper(name="PreferredTerm")
    @XmlElement(name="lexicalValue")
    private List<LexicalValue> preferredTerm;

    @JsonProperty("parentsIdentifiers")
    private List<String> parentsIdentifiers;

    @JsonProperty("childrenIdentifiers")
    private List<String> childrenIdentifiers;

    @JsonProperty("parentsUris")
    private List<String> parentsUris;

    @JsonProperty("childrenUris")
    private List<String> childrenUris;

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

    public void setPreferredTerm(List<LexicalValue> preferredTerm) {
        this.preferredTerm = preferredTerm;
    }

    public List<String> getParentsIdentifiers() {
        return parentsIdentifiers;
    }

    public void setParentsIdentifiers(List<String> parentsIdentifiers) {
        this.parentsIdentifiers = parentsIdentifiers;
    }

    public void addParentIdentifier(final String parentIdentifier){
        if(parentsIdentifiers == null) parentsIdentifiers = Lists.newArrayList();
        parentsIdentifiers.add(parentIdentifier);
    }

    public List<String> getChildrenIdentifiers() {
        return childrenIdentifiers;
    }

    public void setChildrenIdentifiers(List<String> childrenIdentifiers) {
        this.childrenIdentifiers = childrenIdentifiers;
    }

    public void addChildIdentifier(final String childIdentifier){
        if(childrenIdentifiers == null) childrenIdentifiers = Lists.newArrayList();
        childrenIdentifiers.add(childIdentifier);
    }

    public List<String> getParentsUris() {
        return parentsUris;
    }

    public void setParentsUris(List<String> parentsUris) {
        this.parentsUris = parentsUris;
    }

    public void addParentUri(final String parentUri){
        if(parentsUris == null) parentsUris = Lists.newArrayList();
        parentsUris.add(parentUri);
    }

    public List<String> getChildrenUris() {
        return childrenUris;
    }

    public void setChildrenUris(List<String> childrenUris) {
        this.childrenUris = childrenUris;
    }

    public void addChildUri(final String childUri){
        if(childrenUris == null) childrenUris = Lists.newArrayList();
        childrenUris.add(childUri);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Competence that = (Competence) o;

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
        return "Competence{" +
                "uri='" + uri + '\'' +
                ", types=" + types +
                ", identifier='" + identifier + '\'' +
                ", status='" + status + '\'' +
                ", topConcept=" + topConcept +
                ", preferredTerm=" + preferredTerm +
                '}';
    }
}
