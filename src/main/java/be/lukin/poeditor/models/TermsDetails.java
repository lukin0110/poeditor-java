package be.lukin.poeditor.models;

public class TermsDetails {
    public int parsed;
    public int added;
    public int deleted;

    @Override
    public String toString() {
        return "TermsDetails{" +
                "parsed=" + parsed +
                ", added=" + added +
                ", deleted=" + deleted +
                '}';
    }
}
