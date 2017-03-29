package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.blopez.lello.enums.RelationshipType;
import ec.blopez.lello.utils.JSONKeys;

import java.util.Map;

/**
 * Created by benjilopez on 22/02/2017.
 */
public class Relationship {

    @JsonIgnore
    private String sourceId;

    @JsonIgnore
    private String sourceExternaUrl;

    @JsonProperty(JSONKeys.CODE)
    private String code;

    @JsonProperty(JSONKeys.ID)
    private String id;

    @JsonProperty(JSONKeys.EXTERNAL_URL)
    private String externalUrl;

    @JsonProperty(JSONKeys.TYPE)
    public RelationshipType type;

    @JsonProperty(JSONKeys.DEFINITION)
    private Map<String, String> message;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceExternaUrl() {
        return sourceExternaUrl;
    }

    public void setSourceExternaUrl(String sourceExternaUrl) {
        this.sourceExternaUrl = sourceExternaUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }

    public RelationshipType getType() {
        return type;
    }

    public void setType(RelationshipType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Relationship that = (Relationship) o;

        if (sourceId != null ? !sourceId.equals(that.sourceId) : that.sourceId != null) return false;
        if (sourceExternaUrl != null ? !sourceExternaUrl.equals(that.sourceExternaUrl) : that.sourceExternaUrl != null)
            return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (externalUrl != null ? !externalUrl.equals(that.externalUrl) : that.externalUrl != null) return false;
        if (type != that.type) return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = sourceId != null ? sourceId.hashCode() : 0;
        result = 31 * result + (sourceExternaUrl != null ? sourceExternaUrl.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (externalUrl != null ? externalUrl.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "sourceId='" + sourceId + '\'' +
                ", sourceExternaUrl='" + sourceExternaUrl + '\'' +
                ", code='" + code + '\'' +
                ", id='" + id + '\'' +
                ", externalUrl='" + externalUrl + '\'' +
                ", type=" + type +
                ", message=" + message +
                '}';
    }
}
