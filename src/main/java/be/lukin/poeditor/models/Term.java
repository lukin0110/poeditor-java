package be.lukin.poeditor.models;

import java.util.List;

public class Term {
    public String term;
    public String context;
    public String reference;
    public String plural;
    public String comment;
    public String created;
    public String updated;
    public List<String> tags;

    public Term(){}
    
    public Term(String term) {
        this.term = term;
    }

    @Override
    public String toString() {
        return "Term{" +
                "term='" + term + '\'' +
                ", context='" + context + '\'' +
                ", reference='" + reference + '\'' +
                ", plural='" + plural + '\'' +
                ", comment='" + comment + '\'' +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", tags=" + tags +
                '}';
    }
}
