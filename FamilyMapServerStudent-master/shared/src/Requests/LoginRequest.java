package Requests;

public class LoginRequest {

    private String username;
    private String password;
    private String host;
    private String port;
    /**
     * Constructor
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String toString) {
        this.host = toString;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String toString) {
        this.port = toString;
    }
}
