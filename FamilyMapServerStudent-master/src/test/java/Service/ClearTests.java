package Service;

import Requests.RegisterRequest;
import Results.ClearResult;
import Results.RegisterResult;
import dao.DataAccessException;
import org.junit.jupiter.api.Test;

public class ClearTests {
    @Test
    public void clearTest() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.Clear();
    }
}
