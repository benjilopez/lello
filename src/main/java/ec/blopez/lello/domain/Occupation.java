package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import ec.blopez.lello.enums.CompetenceType;
import ec.blopez.lello.xml.domain.LexicalValue;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by benjilopez on 21/02/2017.
 */
public class Occupation extends Competence {

    @JsonProperty("notation")
    private String notation;

    @JsonProperty("memberOfISCOGroup")
    private List<ISCOGroup> groups;

    @JsonProperty("simpleNonPreferredTerm")
    private Map<String, String> simpleNonPreferredTerm;

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

    @Override
    public CompetenceType getType() {
        return CompetenceType.OCCUPATION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Occupation that = (Occupation) o;

        if (notation != null ? !notation.equals(that.notation) : that.notation != null) return false;
        return groups != null ? groups.equals(that.groups) : that.groups == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (notation != null ? notation.hashCode() : 0);
        result = 31 * result + (groups != null ? groups.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Occupation{" +
                "notation='" + notation + '\'' +
                ", groups=" + groups +
                '}';
    }

    public static class Builder extends Competence.Builder<Occupation.Builder, Occupation>{
        private Map<String, String> simpleNonPreferredTerm;
        private String notation;
        private List<ISCOGroup> groups;

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

        public Occupation build(){
            try {
                final Occupation result = super.build(Occupation.class);
                result.setSimpleNonPreferredTerm(simpleNonPreferredTerm);
                result.setNotation(notation);
                result.setGroups(groups);
                return result;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
