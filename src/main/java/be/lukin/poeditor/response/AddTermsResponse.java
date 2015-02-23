package be.lukin.poeditor.response;

import be.lukin.poeditor.models.Response;
import be.lukin.poeditor.models.TermsDetails;

public class AddTermsResponse {

    public Response response;
    public TermsDetails details;

    @Override
    public String toString() {
        return "AddTermsResponse{" +
                "response=" + response +
                ", details=" + details +
                '}';
    }
}
