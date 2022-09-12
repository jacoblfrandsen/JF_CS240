package Results;

public class ClearResult {

    private String message;
    private Boolean success;

    /**
     * Constructor
     */
    public ClearResult(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
    public ClearResult(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
