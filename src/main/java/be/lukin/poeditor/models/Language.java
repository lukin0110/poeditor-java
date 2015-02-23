package be.lukin.poeditor.models;

public class Language {
    public String name;
    public String code;
    public double percentage;

    public Language(){}
    
    public Language(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Language{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}
