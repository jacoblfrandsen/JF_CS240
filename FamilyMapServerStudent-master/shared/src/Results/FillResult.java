package Results;

public class FillResult {
    private String message;
    private Boolean success;

    /**
     * Constructor
     */
    public FillResult(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public FillResult() {}


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
