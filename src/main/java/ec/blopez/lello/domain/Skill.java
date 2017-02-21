package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import java.text.ParseException;
import java.util.List;

/**
 * Created by benjilopez on 31/01/2017.
 */
public class Skill extends Competence {

    @JsonProperty("simpleNonPreferredTerm")
    private List<LexicalValue> simpleNonPreferredTerm;

    public List<LexicalValue> getSimpleNonPreferredTerm() {
        return simpleNonPreferredTerm;
    }

    public void setSimpleNonPreferredTerm(final List<LexicalValue> simpleNonPreferredTerm) {
        this.simpleNonPreferredTerm = simpleNonPreferredTerm;
    }

    public void addSimpleNonPreferredTerm(final LexicalValue simpleNonPreferredTerm){
        if(simpleNonPreferredTerm == null) return;
        if(this.simpleNonPreferredTerm == null) this.simpleNonPreferredTerm = Lists.newArrayList();
        this.simpleNonPreferredTerm.add(simpleNonPreferredTerm);
    }

    public void addSimpleNonPreferredTerm(final List<LexicalValue> simpleNonPreferredTerms){
        if(simpleNonPreferredTerms == null) return;
        if(this.simpleNonPreferredTerm == null) this.simpleNonPreferredTerm = Lists.newArrayList();
        this.simpleNonPreferredTerm.addAll(simpleNonPreferredTerms);
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
        private List<LexicalValue> simpleNonPreferredTerm;

        public Builder setSimpleNonPreferredTerm(List<LexicalValue> simpleNonPreferredTerm) {
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
