package ec.blopez.lello.enums;

/**
 * Created by benjilopez on 21/02/2017.
 */
public enum XMLType {

    SKILL("skills"),
    QUALIFICATION("qualifications"),
    OCCUPATION("occupations"),
    AWARDING_BODIES("awarding bodies"),
    OTHER(null);


    private String value;

    XMLType(final String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static XMLType fromString(final String string){
        if(string == null) return OTHER;
        for(XMLType type : values()) if(string.toLowerCase().equals(type.getValue())) return type;
        return OTHER;
    }
}
