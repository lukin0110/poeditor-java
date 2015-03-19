package be.lukin.poeditor.response;

import be.lukin.poeditor.models.CommentsDetails;
import be.lukin.poeditor.models.Response;

public class AddCommentsResponse {
    public Response response;
    public CommentsDetails details;

    @Override
    public String toString() {
        return "AddCommentsResponse{" +
                "response=" + response +
                ", details=" + details +
                '}';
    }
}
