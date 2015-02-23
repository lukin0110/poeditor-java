package be.lukin.poeditor.response;

import be.lukin.poeditor.models.Response;
import be.lukin.poeditor.models.Term;

import java.util.List;


public class ViewTermsResponse {
    public Response response;
    public List<Term> list;

    @Override
    public String toString() {
        return "ViewTermsResponse{" +
                "response=" + response +
                ", list=" + list +
                '}';
    }
}
