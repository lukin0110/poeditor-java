package be.lukin.poeditor;

import be.lukin.poeditor.exceptions.PermissionDeniedException;
import be.lukin.poeditor.models.Response;

public abstract class ApiUtils {
    
    public static void checkResponse(Response response){
        if("403".equals(response.code)) {
            throw new PermissionDeniedException();
        }
    }
    
}
