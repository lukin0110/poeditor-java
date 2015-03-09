package be.lukin.poeditor;

import be.lukin.poeditor.exceptions.ApiException;
import be.lukin.poeditor.exceptions.InvalidTokenException;
import be.lukin.poeditor.exceptions.PermissionDeniedException;
import be.lukin.poeditor.models.Response;

public abstract class ApiUtils {
    
    public static void checkResponse(Response response){
        //{"response":{"status":"fail","code":"4011","message":"Invalid API Token"}}
        
        if("403".equals(response.code)) {
            throw new PermissionDeniedException();
            
        } else if("4011".equals(response.code)){
            throw new InvalidTokenException();
            
        } else if(!"200".equals(response.code)){
            throw new ApiException(response.status, response.code, response.message);
        }
    }
}
