package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by benjilopez on 31/01/2017.
 */
public class Qualification extends Competence {

    @JsonProperty("definition")
    private Map<String, String> definition;

    @JsonProperty("hasAwardingBody")
    private List<String> hasAwardingBody;

    public Map<String, String> getDefinition() {
        return definition;
    }

    public void setDefinition(Map<String, String> definition) {
        this.definition = definition;
    }

    public List<String> getHasAwardingBody() {
        return hasAwardingBody;
    }

    public void setHasAwardingBody(List<String> hasAwardingBody) {
        this.hasAwardingBody = hasAwardingBody;
    }

    public void addDefinitions(final Map<String, String> definitions){
        if(definitions == null) return;
        for(Map.Entry<String, String> entry : definitions.entrySet()){
            this.definition.computeIfAbsent(entry.getKey(), k -> entry.getValue());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Qualification that = (Qualification) o;

        if (definition != null ? !definition.equals(that.definition) : that.definition != null) return false;
        return hasAwardingBody != null ? hasAwardingBody.equals(that.hasAwardingBody) : that.hasAwardingBody == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (definition != null ? definition.hashCode() : 0);
        result = 31 * result + (hasAwardingBody != null ? hasAwardingBody.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Qualification{" +
                "definition=" + definition +
                ", hasAwardingBody=" + hasAwardingBody +
                '}';
    }

    public static class Builder extends Competence.Builder<Builder, Qualification>{
        private Map<String, String> definition;
        private List<String> hasAwardingBody;

        public Builder setDefinition(final Map<String, String> definition) {
            this.definition = definition;
            return this;
        }

        public Builder setHasAwardingBody(final List<String> hasAwardingBody) {
            this.hasAwardingBody = hasAwardingBody;
            return this;
        }

        public Qualification build(){
            try {
                final Qualification result = super.build(Qualification.class);
                result.setDefinition(definition);
                result.setHasAwardingBody(hasAwardingBody);
                return result;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
