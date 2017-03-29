package ec.blopez.lello.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import ec.blopez.lello.utils.JSONKeys;

import java.util.List;

/**
 * Created by benjilopez on 23/03/2017.
 */
public class CompetenceSearchResult {

    @JsonProperty(JSONKeys.TOTAL)
    private long total;
    @JsonProperty(JSONKeys.LIMIT)
    private int limit;
    @JsonProperty(JSONKeys.OFFSET)
    private int offset;
    @JsonProperty(JSONKeys.COMPETENCES)
    private List<Competence> competences;

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(final List<Competence> competences) {
        this.competences = competences;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(final long total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(final int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(final int offset) {
        this.offset = offset;
    }

    public static class Builder{
        private List<Competence> competences;
        private long total;
        private int limit;
        private int offset;

        public Builder setCompetences(final List<Competence> competences) {
            this.competences = competences;
            return this;
        }

        public Builder setTotal(final long total) {
            this.total = total;
            return this;
        }

        public Builder setLimit(final int limit) {
            this.limit = limit;
            return this;
        }

        public Builder setOffset(final int offset) {
            this.offset = offset;
            return this;
        }

        public CompetenceSearchResult build(){
            final CompetenceSearchResult result = new CompetenceSearchResult();
            result.setCompetences(competences);
            result.setTotal(total);
            result.setLimit(limit);
            result.setOffset(offset);
            return result;
        }
    }
}
