package be.lukin.poeditor.models;

public class UploadDetails {
    public static class Item{
        public int parsed;
        public int added;
        public int deleted;

        @Override
        public String toString() {
            return "Item{" +
                    "parsed=" + parsed +
                    ", added=" + added +
                    ", deleted=" + deleted +
                    '}';
        }
    }

    public Item terms;
    public Item definitions;

    @Override
    public String toString() {
        return "UploadDetails{" +
                "terms=" + terms +
                ", definitions=" + definitions +
                '}';
    }
}
