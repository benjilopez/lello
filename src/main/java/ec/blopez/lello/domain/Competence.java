package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ec.blopez.lello.crawler.esco.domain.LexicalValue;
import ec.blopez.lello.utils.JSONKeys;
import java.util.List;
import java.util.Map;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
public class Competence {

    @JsonProperty(JSONKeys.ID)
    private String uri;

    @JsonProperty(JSONKeys.CODE)
    private String id;

    @JsonProperty(JSONKeys.NAME)
    private Map<String, String> preferredTerm;

    @JsonProperty(JSONKeys.OTHER_NAME)
    private Map<String, String> simpleNonPreferredTerm;

    @JsonProperty(JSONKeys.DEFINITION)
    private Map<String, String> definition;

    @JsonProperty(JSONKeys.FRAMEWORK)
    private String framework;

    @JsonProperty(JSONKeys.EXTERNAL_URL)
    private String externalUri;

    @JsonProperty(JSONKeys.EXTERNAL_TYPES)
    private List<String> types;

    @JsonProperty(JSONKeys.EXTERNAL_ID)
    private String identifier;

    @JsonProperty(JSONKeys.STATUS)
    private String status;

    @JsonProperty(JSONKeys.TOP)
    private boolean topConcept;

    @JsonProperty(JSONKeys.RELATED)
    private List<Relationship> related;

    @JsonProperty(JSONKeys.NOTATION)
    private String notation;

    @JsonProperty(JSONKeys.HAS_AWARDING_BODY)
    private List<String> hasAwardingBody;

    @JsonProperty(JSONKeys.TYPE)
    private String type;

    @JsonGetter
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

    public List<Relationship> getRelated() {
        return related;
    }

    public void setRelated(List<Relationship> related) {
        this.related = related;
    }

    public void addRelated(final Relationship related){
        if(related == null) this.related = Lists.newArrayList();
        this.related.add(related);
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

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public Map<String, String> getSimpleNonPreferredTerm() {
        return simpleNonPreferredTerm;
    }

    public void setSimpleNonPreferredTerm(final Map<String, String> simpleNonPreferredTerm) {
        this.simpleNonPreferredTerm = simpleNonPreferredTerm;
    }

    public void addSimpleNonPreferredTerm(final LexicalValue simpleNonPreferredTerm){
        if(simpleNonPreferredTerm == null) return;
        if(this.simpleNonPreferredTerm == null) this.simpleNonPreferredTerm = Maps.newHashMap();
        this.simpleNonPreferredTerm.computeIfAbsent(simpleNonPreferredTerm.getLang(), k -> simpleNonPreferredTerm.getValue());
    }

    public void addSimpleNonPreferredTerm(final Map<String, String> simpleNonPreferredTerms){
        if(simpleNonPreferredTerms == null) return;
        for(Map.Entry<String, String> entry : simpleNonPreferredTerms.entrySet()) this.simpleNonPreferredTerm.computeIfAbsent(entry.getKey(), k -> entry.getValue());
    }


    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public Map<String, String> getDefinition() {
        return definition;
    }

    public void setDefinition(Map<String, String> definition) {
        this.definition = definition;
    }

    public List<String> getHasAwardingBody() {
        return hasAwardingBody;
    }

    public void setHasAwardingBody(List<String> hasAwardingBody) {
        this.hasAwardingBody = hasAwardingBody;
    }

    public void addDefinitions(final Map<String, String> definitions){
        if(definitions == null) return;
        for(Map.Entry<String, String> entry : definitions.entrySet()){
            this.definition.computeIfAbsent(entry.getKey(), k -> entry.getValue());
        }
    }

    public String getType(){
        return type;
    }

    public void setType(final String type){
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Competence that = (Competence) o;

        if (topConcept != that.topConcept) return false;
        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (preferredTerm != null ? !preferredTerm.equals(that.preferredTerm) : that.preferredTerm != null)
            return false;
        if (simpleNonPreferredTerm != null ? !simpleNonPreferredTerm.equals(that.simpleNonPreferredTerm) : that.simpleNonPreferredTerm != null)
            return false;
        if (definition != null ? !definition.equals(that.definition) : that.definition != null) return false;
        if (framework != null ? !framework.equals(that.framework) : that.framework != null) return false;
        if (externalUri != null ? !externalUri.equals(that.externalUri) : that.externalUri != null) return false;
        if (types != null ? !types.equals(that.types) : that.types != null) return false;
        if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (related != null ? !related.equals(that.related) : that.related != null) return false;
        if (notation != null ? !notation.equals(that.notation) : that.notation != null) return false;
        if (hasAwardingBody != null ? !hasAwardingBody.equals(that.hasAwardingBody) : that.hasAwardingBody != null)
            return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (preferredTerm != null ? preferredTerm.hashCode() : 0);
        result = 31 * result + (simpleNonPreferredTerm != null ? simpleNonPreferredTerm.hashCode() : 0);
        result = 31 * result + (definition != null ? definition.hashCode() : 0);
        result = 31 * result + (framework != null ? framework.hashCode() : 0);
        result = 31 * result + (externalUri != null ? externalUri.hashCode() : 0);
        result = 31 * result + (types != null ? types.hashCode() : 0);
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (topConcept ? 1 : 0);
        result = 31 * result + (related != null ? related.hashCode() : 0);
        result = 31 * result + (notation != null ? notation.hashCode() : 0);
        result = 31 * result + (hasAwardingBody != null ? hasAwardingBody.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Competence{" +
                "uri='" + uri + '\'' +
                ", id='" + id + '\'' +
                ", preferredTerm=" + preferredTerm +
                ", simpleNonPreferredTerm=" + simpleNonPreferredTerm +
                ", definition=" + definition +
                ", framework='" + framework + '\'' +
                ", externalUri='" + externalUri + '\'' +
                ", types=" + types +
                ", identifier='" + identifier + '\'' +
                ", status='" + status + '\'' +
                ", topConcept=" + topConcept +
                ", related=" + related +
                ", notation='" + notation + '\'' +
                ", hasAwardingBody=" + hasAwardingBody +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Builder{
        private String uri;
        private List<String> types;
        private String identifier;
        private String status;
        private boolean topConcept;
        private Map<String, String> preferredTerm;
        private String framework;
        private Map<String, String> simpleNonPreferredTerm;
        private String notation;
        private List<Relationship> relationships;
        private Map<String, String> definition;
        private List<String> hasAwardingBody;
        private String type;

        public Builder setSimpleNonPreferredTerm(Map<String, String> simpleNonPreferredTerm) {
            this.simpleNonPreferredTerm = simpleNonPreferredTerm;
            return this;
        }

        public Builder setNotation(String notation) {
            this.notation = notation;
            return this;
        }

        public Builder setRelationships(List<Relationship> relationships) {
            this.relationships = relationships;
            return this;
        }

        public Builder setUri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder setTypes(List<String> types) {
            this.types = types;
            return this;
        }

        public Builder setIdentifier(String identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setTopConcept(boolean topConcept) {
            this.topConcept = topConcept;
            return this;
        }

        public Builder setPreferredTerm(Map<String, String> preferredTerm) {
            this.preferredTerm = preferredTerm;
            return this;
        }

        public Builder setFramework(final String framework){
            this.framework = framework;
            return this;
        }

        public Builder setDefinition(final Map<String, String> definition) {
            this.definition = definition;
            return this;
        }

        public Builder setHasAwardingBody(final List<String> hasAwardingBody) {
            this.hasAwardingBody = hasAwardingBody;
            return this;
        }

        public Builder setType(final String type){
            this.type = type;
            return this;
        }

        public Competence build(){
            final Competence competence = new Competence();
            competence.setExternalUri(uri);
            competence.setTypes(types);
            competence.setIdentifier(identifier);
            competence.setStatus(status);
            competence.setTopConcept(topConcept);
            competence.setPreferredTerm(preferredTerm);
            competence.setFramework(framework);
            competence.setSimpleNonPreferredTerm(simpleNonPreferredTerm);
            competence.setNotation(notation);
            competence.setRelated(relationships);
            competence.setDefinition(definition);
            competence.setHasAwardingBody(hasAwardingBody);
            competence.setType(type);
            return competence;
        }
    }
}
