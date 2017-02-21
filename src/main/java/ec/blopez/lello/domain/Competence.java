package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ec.blopez.lello.xml.domain.LexicalValue;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
public abstract class Competence {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("types")
    private List<String> types;

    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("status")
    private String status;

    @JsonProperty("topConcept")
    private boolean topConcept;

    @JsonProperty("preferredTerm")
    private Map<String, String> preferredTerm;

    @JsonProperty("parentsIdentifiers")
    private List<String> parentsIdentifiers;

    @JsonProperty("childrenIdentifiers")
    private List<String> childrenIdentifiers;

    @JsonProperty("parentsUris")
    private List<String> parentsUris;

    @JsonProperty("childrenUris")
    private List<String> childrenUris;

    @JsonProperty("related")
    private List<Relationship> related;

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

    public Map<String, String> getPreferredTerm() {
        return preferredTerm;
    }

    public void setPreferredTerm(final Map<String, String> preferredTerm) {
        this.preferredTerm = preferredTerm;
    }

    public void addPreferredTerm(final LexicalValue preferredTerm){
        if(preferredTerm == null) return;
        if(this.preferredTerm == null) this.preferredTerm = Maps.newHashMap();
        this.preferredTerm.computeIfAbsent(preferredTerm.getLang(), k -> preferredTerm.getValue());
    }

    public void addPreferredTerm(final Map<String, String> preferredTerms){
        if(preferredTerms == null) return;
        for(Map.Entry<String, String> entry : preferredTerms.entrySet()) this.preferredTerm.computeIfAbsent(entry.getKey(), k -> entry.getValue());
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

    public List<Relationship> getRelated() {
        return related;
    }

    public void setRelated(List<Relationship> related) {
        this.related = related;
    }

    public void addRelated(final Relationship relatedUri){
        if(related == null) related = Lists.newArrayList();
        related.add(relatedUri);
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

    public static abstract class Builder<T, E>{
        private String uri;
        private List<String> types;
        private String identifier;
        private String status;
        private boolean topConcept;
        private Map<String, String> preferredTerm;

        public T setUri(String uri) {
            this.uri = uri;
            return (T) this;
        }

        public T setTypes(List<String> types) {
            this.types = types;
            return (T) this;
        }

        public T setIdentifier(String identifier) {
            this.identifier = identifier;
            return (T) this;
        }

        public T setStatus(String status) {
            this.status = status;
            return (T) this;
        }

        public T setTopConcept(boolean topConcept) {
            this.topConcept = topConcept;
            return (T) this;
        }

        public T setPreferredTerm(Map<String, String> preferredTerm) {
            this.preferredTerm = preferredTerm;
            return (T) this;
        }

        public E build(final Class c) throws ParseException {
            try{
                final Object object = c.newInstance();
                if (!(object instanceof Competence)) throw new ParseException("Illegal class type", 0);
                final Competence competence = (Competence) object;
                competence.setUri(uri);
                competence.setTypes(types);
                competence.setIdentifier(identifier);
                competence.setStatus(status);
                competence.setTopConcept(topConcept);
                competence.setPreferredTerm(preferredTerm);
                return (E) competence ;
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
