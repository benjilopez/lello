package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ec.blopez.lello.Configurations;
import ec.blopez.lello.crawler.esco.domain.CompetenceType;
import ec.blopez.lello.crawler.esco.domain.LexicalValue;
import org.elasticsearch.Build;
import org.hibernate.hql.internal.ast.tree.ComponentJoin;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
public class Competence {

    private static long COUNTER = 1;

    @JsonProperty("@id")
    private String uri;

    @JsonProperty("code")
    private String id;

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

    @JsonIgnore
    private Map<String, Competence> parents;

    @JsonIgnore
    private Map<String, Competence> children;

    @JsonProperty("related")
    private List<Relationship> related;

    private String framework;

    @JsonProperty("notation")
    private String notation;

    @JsonProperty("memberOfISCOGroup")
    private List<ISCOGroup> groups;

    @JsonProperty("simpleNonPreferredTerm")
    private Map<String, String> simpleNonPreferredTerm;

    @JsonProperty("definition")
    private Map<String, String> definition;

    @JsonProperty("hasAwardingBody")
    private List<String> hasAwardingBody;

    @JsonProperty("competenceType")
    private CompetenceType type;

    protected Competence(){
        id = "LELLO:" + COUNTER;
        COUNTER++;
        final StringBuilder builder = new StringBuilder(Configurations.URL);
        builder.append(Configurations.PATH);
        builder.append("competences/");
        builder.append(id);
        uri = builder.toString();
    }


    @JsonProperty("parents")
    public List<String> getParenUris(){
        if(parents == null) return null;
        final List<String> result = Lists.newArrayList();
        for(Competence competence : parents.values()) result.add(competence.getUri());
        return result;
    }

    @JsonProperty("children")
    public List<String> getChildrenUris(){
        if(children == null) return null;
        final List<String> result = Lists.newArrayList();
        for(Competence competence : children.values()) result.add(competence.getUri());
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

    public Map<String, Competence> getParents() {
        return parents;
    }

    public void setParents(final Map<String, Competence> parents) {
        this.parents = parents;
    }

    public void addParent(final Competence parent){
        if(parent == null) return;
        if(parents == null) parents = Maps.newHashMap();
        parents.computeIfAbsent(parent.getId(), k -> parent);
    }

    public Map<String, Competence> getChildren() {
        return children;
    }

    public void setChildren(final Map<String, Competence> children) {
        this.children = children;
    }

    public void addChild(final Competence child){
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

    public CompetenceType getType(){
        return type;
    }

    public void setType(final CompetenceType type){
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
        private CompetenceType type;

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

        public Builder setType(final CompetenceType type){
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
