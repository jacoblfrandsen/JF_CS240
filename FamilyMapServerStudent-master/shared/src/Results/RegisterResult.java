package Results;

public class RegisterResult {

    private String authtoken;
    private String username;
    private String personID;
    private String message;
    private Boolean success;
    /**
     * Constructor
     */
    public RegisterResult(String authtoken, String username, String personID, String message, Boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.message = message;
        this.success = success;
    }
    public RegisterResult(){};

    public RegisterResult(boolean b, String s, Object o) {
        this.success = b;
        this.message = s;

    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authToken) {
        this.authtoken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

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
