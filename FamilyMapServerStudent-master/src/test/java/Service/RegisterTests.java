package Service;

import Requests.RegisterRequest;
import Results.RegisterResult;
import dao.DataAccessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterTests {
    @Test
    public void registerTest() throws DataAccessException {
        RegisterService services = new RegisterService();
        ClearService clearService = new ClearService();
        clearService.Clear();
        RegisterRequest request = new RegisterRequest();

        request.setUsername("Jfran33-Username");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Jacob");
        request.setLastName("Frandsen");
        request.setGender("m");

        RegisterResult result = services.register(request);

        assertTrue(result.getUsername().equals("Jfran33-Username"));
        try {
            result.getMessage().equals("error");
        } catch (NullPointerException ex) {
            assertTrue(true);
        }

        clearService.Clear();
    }
    @Test
    public void registerTestFail() throws DataAccessException {
        RegisterService services = new RegisterService();
        ClearService clearService = new ClearService();
        clearService.Clear();
        RegisterRequest request = new RegisterRequest();

        request.setUsername("Jfran33-Username");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Jacob");
        request.setLastName("Frandsen");
        request.setGender("m");

        RegisterResult result = services.register(request);

        assertFalse(result.getUsername().equals("RandomUsername"));
        try {
            result.getMessage().equals("error");
        } catch (NullPointerException ex) {
            assertTrue(true);
        }

        clearService.Clear();
    }

}
