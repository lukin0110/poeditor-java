package be.lukin.poeditor.models;

import com.google.gson.annotations.SerializedName;

/**
 * JSON: {"id":"29666","name":"Viking App: iOS","public":"0","open":"0","created":"2015-02-18 12:44:40"}
 */
public class Project {
    public String id;
    public String name;
    @SerializedName("public") public String isPublic;
    public String open;
    public String reference_language;
    public String created;

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isPublic='" + isPublic + '\'' +
                ", open='" + open + '\'' +
                ", reference_language='" + reference_language + '\'' +
                ", created='" + created + '\'' +
                '}';
    }
}
