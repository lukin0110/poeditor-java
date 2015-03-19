package be.lukin.poeditor.models;

public class Comment {
    public String term;
    public String context;
    public String comment;

    public Comment() {
    }
    
    public Comment(String term, String comment) {
        this.term = term;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "term='" + term + '\'' +
                ", context='" + context + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
