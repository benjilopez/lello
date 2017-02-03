package ec.blopez.lello.enums;

/**
 * Created by Benjamin Lopez on 03/02/2017.
 */
public enum DataBaseAction {

    UPDATE("Update"),
    CREATE("Create"),
    DELETE("Delete");

    private String value;

    private DataBaseAction(final String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
