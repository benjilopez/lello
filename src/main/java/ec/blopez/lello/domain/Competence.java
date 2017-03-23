package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ec.blopez.lello.Configurations;
import ec.blopez.lello.crawler.esco.domain.CompetenceType;
import ec.blopez.lello.crawler.esco.domain.LexicalValue;
import ec.blopez.lello.rest.ResponseKeys;
import org.elasticsearch.Build;
import org.hibernate.hql.internal.ast.tree.ComponentJoin;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
public class Competence {

    @JsonProperty(ResponseKeys.ID)
    private String uri;

    @JsonProperty(ResponseKeys.CODE)
    private String id;

    @JsonProperty(ResponseKeys.EXTERNAL_URL)
    private String externalUri;

    @JsonProperty(ResponseKeys.EXTERNAL_TYPES)
    private List<String> types;

    @JsonProperty(ResponseKeys.EXTERNAL_ID)
    private String identifier;

    @JsonProperty(ResponseKeys.STATUS)
    private String status;

    @JsonProperty(ResponseKeys.TOP)
    private boolean topConcept;

    @JsonProperty(ResponseKeys.NAME)
    private Map<String, String> preferredTerm;

    @JsonProperty(ResponseKeys.PARENTS)
    private List<String> parents;

    @JsonProperty(ResponseKeys.CHILDREN)
    private List<String> children;

    @JsonProperty(ResponseKeys.RELATED)
    private List<Relationship> related;

    @JsonProperty(ResponseKeys.FRAMEWORK)
    private String framework;

    @JsonProperty(ResponseKeys.NOTATION)
    private String notation;

    @JsonProperty(ResponseKeys.ISCO_GROUPS)
    private List<ISCOGroup> groups;

    @JsonProperty(ResponseKeys.OTHER_NAME)
    private Map<String, String> simpleNonPreferredTerm;

    @JsonProperty(ResponseKeys.DEFINITION)
    private Map<String, String> definition;

    @JsonProperty(ResponseKeys.HAS_AWARDING_BODY)
    private List<String> hasAwardingBody;

    @JsonProperty(ResponseKeys.TYPE)
    private String type;

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

    public List<String> getParents() {
        return parents;
    }

    public void setParents(final List<String> parents){
        this.parents = parents;
    }


    public void addParent(final String parent){
        if(parent == null) return;
        if(parents == null) parents = Lists.newArrayList();
        for(String savedParent : parents) if(savedParent.equals(parent)) return;
        parents.add(parent);
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(final List<String> children){
        this.children = children;
    }

    public void addChild(final String child){
        if(child == null) return;
        if(children == null) children = Lists.newArrayList();
        for(String savedChild : children) if(savedChild.equals(child)) return;
        children.add(child);
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

    public List<ISCOGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<ISCOGroup> groups) {
        this.groups = groups;
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
        private List<ISCOGroup> groups;
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

        public Builder setGroups(List<ISCOGroup> groups) {
            this.groups = groups;
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
            competence.setGroups(groups);
            competence.setDefinition(definition);
            competence.setHasAwardingBody(hasAwardingBody);
            competence.setType(type);
            return competence;
        }
    }
}
