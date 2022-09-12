package Results;

public class LoadResult {

    private String message;
    private Boolean success;

    /**
     * Constructor
     */
    public LoadResult(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public LoadResult() {}

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
