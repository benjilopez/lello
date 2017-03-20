package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ec.blopez.lello.Configurations;
import ec.blopez.lello.enums.CompetenceType;
import ec.blopez.lello.xml.domain.LexicalValue;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
public abstract class Esco {

    private static long COUNTER = 1;

    @JsonProperty("externalUri")
    private String externalUri;

    @JsonProperty("types")
    private List<String> types;

    @JsonProperty("externalId")
    private String identifier;

    @JsonProperty("status")
    private String status;

    @JsonProperty("topConcept")
    private boolean topConcept;

    @JsonProperty("preferredTerm")
    private Map<String, String> preferredTerm;

    @JsonProperty("code")
    private String id;

    @JsonProperty("@id")
    private String uri;

    @JsonIgnore
    private Map<String, Esco> parents;

    @JsonIgnore
    private Map<String, Esco> children;

    @JsonProperty("related")
    private List<Relationship> related;

    protected Esco(){
        id = "LELLO:" + COUNTER;
        COUNTER++;
        final StringBuilder builder = new StringBuilder(Configurations.URL);
        builder.append(Configurations.PATH);
        builder.append(getType().getUrl());
        builder.append("/");
        builder.append(id);
        uri = builder.toString();
    }

    @JsonProperty("competenceType")
    public abstract CompetenceType getType();

    @JsonProperty("parents")
    public List<String> getParenUris(){
        if(parents == null) return null;
        final List<String> result = Lists.newArrayList();
        for(Esco esco : parents.values()) result.add(esco.getUri());
        return result;
    }

    @JsonProperty("children")
    public List<String> getChildrenUris(){
        if(children == null) return null;
        final List<String> result = Lists.newArrayList();
        for(Esco esco : children.values()) result.add(esco.getUri());
        return result;
    }

    public String getExternalUri() {
        return externalUri;
    }

    public void setExternalUri(String externalUri) {
        this.externalUri = externalUri;
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

    public Map<String, Esco> getParents() {
        return parents;
    }

    public void setParents(final Map<String, Esco> parents) {
        this.parents = parents;
    }

    public void addParent(final Esco parent){
        if(parent == null) return;
        if(parents == null) parents = Maps.newHashMap();
        parents.computeIfAbsent(parent.getId(), k -> parent);
    }

    public Map<String, Esco> getChildren() {
        return children;
    }

    public void setChildren(final Map<String, Esco> children) {
        this.children = children;
    }

    public void addChild(final Esco child){
        if(child == null) return;
        if(children == null) children = Maps.newHashMap();
        children.computeIfAbsent(child.getId(), k -> child);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Esco that = (Esco) o;

        if (topConcept != that.topConcept) return false;
        if (externalUri != null ? !externalUri.equals(that.externalUri) : that.externalUri != null) return false;
        if (types != null ? !types.equals(that.types) : that.types != null) return false;
        if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (preferredTerm != null ? !preferredTerm.equals(that.preferredTerm) : that.preferredTerm != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;
        if (parents != null ? !parents.equals(that.parents) : that.parents != null) return false;
        if (children != null ? !children.equals(that.children) : that.children != null) return false;
        return related != null ? related.equals(that.related) : that.related == null;
    }

    @Override
    public int hashCode() {
        int result = externalUri != null ? externalUri.hashCode() : 0;
        result = 31 * result + (types != null ? types.hashCode() : 0);
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (topConcept ? 1 : 0);
        result = 31 * result + (preferredTerm != null ? preferredTerm.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        result = 31 * result + (parents != null ? parents.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        result = 31 * result + (related != null ? related.hashCode() : 0);
        return result;
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
                if (!(object instanceof Esco)) throw new ParseException("Illegal class type", 0);
                final Esco esco = (Esco) object;
                esco.setExternalUri(uri);
                esco.setTypes(types);
                esco.setIdentifier(identifier);
                esco.setStatus(status);
                esco.setTopConcept(topConcept);
                esco.setPreferredTerm(preferredTerm);
                return (E) esco;
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
