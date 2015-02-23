package be.lukin.poeditor.models;

public class Response {
    /**
     * https://poeditor.com/api_reference/#create_project 
     */
    public static class Item{
        public int id;

        @Override
        public String toString() {
            return "Item{" +
                    "id=" + id +
                    '}';
        }
    }
    
    public String status;
    public String code;
    public String message;
    public Item item;

    @Override
    public String toString() {
        return "Response{" +
                "status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", item=" + item +
                '}';
    }
}
