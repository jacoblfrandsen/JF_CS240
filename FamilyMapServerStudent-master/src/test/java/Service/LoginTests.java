package Service;

import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.LoginResult;
import Results.RegisterResult;
import dao.DataAccessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTests {
    @Test
    public void loginTest() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.Clear();
        RegisterRequest request = new RegisterRequest();
        RegisterResult registerResult = new RegisterResult();
        RegisterService registerService = new RegisterService();
        LoginService loginService = new LoginService();
        LoginRequest loginRequest = new LoginRequest();
        LoginRequest loginRequest2 = new LoginRequest();
        LoginResult loginResult = new LoginResult();
        LoginResult loginResult2 = new LoginResult();

        request.setUsername("Jfran33-Username");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Jacob");
        request.setLastName("Frandsen");
        request.setGender("m");

        registerResult = registerService.register(request);


        loginRequest.setUsername("Jfran33-Username");
        loginRequest.setPassword("password");

        loginResult = loginService.login(loginRequest);

        assertTrue(loginResult.getUsername().equals("Jfran33-Username"));

        loginRequest2.setUsername("null");
        loginRequest2.setPassword("password");

        loginResult2 = loginService.login(loginRequest);

        assertTrue(loginResult2.getSuccess().equals(true));
        clearService.Clear();

    }
    @Test
    public void loginTestFail() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.Clear();
        RegisterRequest request = new RegisterRequest();
        RegisterResult registerResult = new RegisterResult();
        RegisterService registerService = new RegisterService();
        LoginService loginService = new LoginService();
        LoginRequest loginRequest = new LoginRequest();
        LoginRequest loginRequest2 = new LoginRequest();
        LoginResult loginResult = new LoginResult();
        LoginResult loginResult2 = new LoginResult();

        request.setUsername("Jfran33-Username");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Jacob");
        request.setLastName("Frandsen");
        request.setGender("m");

        registerResult = registerService.register(request);


        loginRequest.setUsername("Jfran33-Username");
        loginRequest.setPassword("password");

        loginResult = loginService.login(loginRequest);

        assertTrue(loginResult.getUsername().equals("Jfran33-Username"));

        loginRequest2.setUsername("null");
        loginRequest2.setPassword("password");

        loginResult2 = loginService.login(loginRequest2);

        assertFalse(loginResult2.getSuccess().equals(true));
        clearService.Clear();

    }
}
