package be.lukin.poeditor.models;

public class CommentsDetails {
    public int parsed;
    public int added;

    @Override
    public String toString() {
        return "CommentsDetails{" +
                "parsed=" + parsed +
                ", added=" + added +
                '}';
    }
}
