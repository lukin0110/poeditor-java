package be.lukin.poeditor.exceptions;

public class ApiException extends RuntimeException {
    private String status;
    private String code;
    
    public ApiException() {
    }

    public ApiException(String status, String code, String message){
        super(message);
        this.status = status;
        this.code = code;
        
    }
    
    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", message='" + super.getMessage() + '\'' +
                '}';
    }
}
