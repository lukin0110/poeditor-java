package be.lukin.poeditor;

import be.lukin.poeditor.exceptions.ApiException;
import be.lukin.poeditor.exceptions.InvalidTokenException;
import be.lukin.poeditor.exceptions.PermissionDeniedException;
import be.lukin.poeditor.models.Response;

import java.util.Map;
import static java.lang.String.format;

public abstract class ApiUtils {
    
    public static void checkResponse(Response response){
        checkResponse(response, null);
    }
    
    public static void checkResponse(Response response, Map<String, String> context){
        
        if("403".equals(response.code)) {
            throw new PermissionDeniedException();
            
        } else if("4011".equals(response.code)) {
            //{"response":{"status":"fail","code":"4011","message":"Invalid API Token"}}
            throw new InvalidTokenException();

        } else if("4043".equals(response.code)){
            //{"response":{"status":"fail","code":"4043","message":"Wrong language code"}}
            String lang = context != null ? context.get("lang") : null;
            String msg = format("Wrong language code: %s", lang);
            throw new ApiException(response.status, response.code, msg);
            
        } else if(!"200".equals(response.code)){
            throw new ApiException(response.status, response.code, response.message);
        }
    }
}
