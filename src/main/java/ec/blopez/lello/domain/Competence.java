package ec.blopez.lello.domain;

import java.util.List;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
public class Competence {

    private String uri;
    private List<String> types;
    private String identifier;
    private String status;
    private boolean topConcept;
    private List<LexicalValue> preferredTerm;

}
