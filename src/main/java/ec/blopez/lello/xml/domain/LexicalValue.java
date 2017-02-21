package ec.blopez.lello.xml.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;

/**
 * Created by Benjamin Lopez on 31/01/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LexicalValue {

    @XmlAttribute(name="lang")
    private String lang;

    @XmlValue
    private String value;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexicalValue that = (LexicalValue) o;

        if (lang != null ? !lang.equals(that.lang) : that.lang != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = lang != null ? lang.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LexicalValue{" +
                "lang='" + lang + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
