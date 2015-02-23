package be.lukin.poeditor.models;

/**
* Created by maartenhuijsmans on 20/2/15.
*/
public class TermsDetails {
    public int parsed;
    public int added;

    @Override
    public String toString() {
        return "Details{" +
                "parsed=" + parsed +
                ", added=" + added +
                '}';
    }
}
