package ec.blopez.lello.domain;

import java.util.List;

/**
 * Created by benjilopez on 24/03/2017.
 */
public class ParserResult {

    private List<Competence> competences;

    private List<Relationship> relationships;

    public ParserResult(final List<Competence> competences, final List<Relationship> relationships){
        this.competences = competences;
        this.relationships = relationships;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }
}
