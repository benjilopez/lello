package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
public class Competence {

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
    private List<LexicalValue> preferredTerm;

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
