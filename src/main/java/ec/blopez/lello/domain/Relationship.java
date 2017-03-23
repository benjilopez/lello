package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.blopez.lello.enums.RelationshipType;

import java.util.Map;

/**
 * Created by benjilopez on 22/02/2017.
 */
public class Relationship {

    @JsonIgnore
    private Competence competence;

    @JsonProperty("description")
    private Map<String, String> message;

    @JsonProperty("uri")
    public String getUri() {
        if(competence == null) return null;
        return competence.getUri();
    }

    public RelationshipType type;

    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    @JsonIgnore
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
