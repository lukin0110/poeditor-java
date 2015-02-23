package be.lukin.poeditor.models;

public class Response {
    public String status;
    public String code;
    public String message;

    @Override
    public String toString() {
        return "Response{" +
                "status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
