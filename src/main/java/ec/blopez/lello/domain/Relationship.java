package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by benjilopez on 22/02/2017.
 */
public class Relationship {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("description")
    private Map<String, String> message;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Relationship that = (Relationship) o;

        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;
        if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "uri='" + uri + '\'' +
                ", identifier='" + identifier + '\'' +
                ", message=" + message +
                '}';
    }
}
