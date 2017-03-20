package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by benjilopez on 22/02/2017.
 */
public class Relationship {

    @JsonIgnore
    private Esco esco;

    @JsonProperty("description")
    private Map<String, String> message;

    @JsonProperty("uri")
    public String getUri() {
        if(esco == null) return null;
        return esco.getUri();
    }

    public Esco getEsco() {
        return esco;
    }

    public void setEsco(Esco esco) {
        this.esco = esco;
    }

    @JsonIgnore
    public Map<String, String> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }
}
