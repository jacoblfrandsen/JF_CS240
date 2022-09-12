package Service;

import Model.User;
import Model.AuthToken;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.LoadResult;
import Results.LoginResult;
import Results.RegisterResult;
import dao.*;

import java.sql.Connection;

public class LoginService {
    Database db = new Database();
    /**
     * Constructor
     */
    public LoginService(User user, AuthToken authToken){}
    public LoginService() {}

    /**
     * login user
     */
    public LoginResult login(LoginRequest loginRequest) throws DataAccessException {
        LoginResult loginResult = new LoginResult();
        Connection conn = db.getConnection();
        ServiceGeneral SG = new ServiceGeneral();
        UserDao userDao = new UserDao(conn);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);

        User user;
        AuthToken authToken;

        try{
            user = userDao.getUserFromUsername(loginRequest.getUsername());

            if (user == null) {
                loginResult.setMessage("error: invalid username or password");
                loginResult.setSuccess(false);
                db.closeConnection(true);
                return loginResult;
            }
            if (!(user.getPassword().equals(loginRequest.getPassword()))) {
                loginResult.setMessage("error: invalid username or password");
                loginResult.setSuccess(false);
                db.closeConnection(true);
                return loginResult;
            }

            authToken = new AuthToken(SG.getRandIDNum(), user.getUsername());
            authTokenDao.insert(authToken);
            loginResult.setPersonID(user.getPersonID());
            loginResult.setUsername(user.getUsername());
            loginResult.setAuthtoken(authToken.getAuthToken());
            loginResult.setSuccess(true);
            db.closeConnection(true);
            return loginResult;
        }
        catch(DataAccessException e){
            loginResult.setMessage("error in login");
            loginResult.setSuccess(false);
            return loginResult;
        }
    }



}
