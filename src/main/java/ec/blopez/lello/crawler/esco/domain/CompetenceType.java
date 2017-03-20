package ec.blopez.lello.crawler.esco.domain;

/**
 * Created by benjilopez on 21/02/2017.
 */
public enum CompetenceType {

    SKILL("skills"),
    QUALIFICATION("qualifications"),
    OCCUPATION("occupations"),
    AWARDING_BODIES("awarding bodies", "awarding_bodies"),
    COMPETENCES("competences"),
    OTHER(null);


    private String value;

    private String url;

    CompetenceType(final String value){
        this(value, value);
    }

    CompetenceType(final String value, final String url){
        this.value = value;
        this.url = url;
    }

    public String getValue(){
        return value;
    }

    public String getUrl(){return url;}

    public static CompetenceType fromString(final String string){
        if(string == null) return OTHER;
        for(CompetenceType type : values()) if(string.toLowerCase().equals(type.getValue())) return type;
        return OTHER;
    }
}
