package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.blopez.lello.enums.RelationshipType;
import ec.blopez.lello.rest.ResponseKeys;

import java.util.Map;

/**
 * Created by benjilopez on 22/02/2017.
 */
public class Relationship {

    @JsonIgnore
    private String sourceId;

    @JsonIgnore
    private String sourceExternaUrl;

    @JsonProperty(ResponseKeys.CODE)
    private String code;

    @JsonProperty(ResponseKeys.ID)
    private String id;

    @JsonProperty(ResponseKeys.EXTERNAL_URL)
    private String externalUrl;

    @JsonProperty(ResponseKeys.TYPE)
    public RelationshipType type;

    @JsonProperty(ResponseKeys.DEFINITION)
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
}
