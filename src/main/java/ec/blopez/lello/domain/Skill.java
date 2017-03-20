package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import ec.blopez.lello.crawler.esco.domain.CompetenceType;
import ec.blopez.lello.crawler.esco.domain.LexicalValue;

import java.text.ParseException;
import java.util.Map;

/**
 * Created by benjilopez on 31/01/2017.
 */
public class Skill extends Competence {

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

    @Override
    public CompetenceType getType() {
        return CompetenceType.SKILL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Skill skill = (Skill) o;

        return simpleNonPreferredTerm != null ? simpleNonPreferredTerm.equals(skill.simpleNonPreferredTerm) : skill.simpleNonPreferredTerm == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (simpleNonPreferredTerm != null ? simpleNonPreferredTerm.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "simpleNonPreferredTerm=" + simpleNonPreferredTerm +
                '}';
    }

    public static class Builder extends Competence.Builder<Builder, Skill>{
        private Map<String, String> simpleNonPreferredTerm;

        public Builder setSimpleNonPreferredTerm(Map<String, String> simpleNonPreferredTerm) {
            this.simpleNonPreferredTerm = simpleNonPreferredTerm;
            return this;
        }

        public Skill build(){
            try {
                final Skill result = super.build(Skill.class);
                result.setSimpleNonPreferredTerm(simpleNonPreferredTerm);
                return result;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
