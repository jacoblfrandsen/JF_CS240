package Service;

import Model.AuthToken;
import Model.Person;
import Model.User;
import Requests.RegisterRequest;
import Results.FillResult;
import Results.RegisterResult;
import dao.*;

import java.sql.Connection;
import java.sql.SQLException;

public class RegisterService {
    private Database db = new Database();
    /**
     * Constructor
     */
    public RegisterService(){}


    /**
     * Register User
     */
    public RegisterResult register(RegisterRequest request) throws DataAccessException {
           /*
    TODO
        Creates a new user account,
        generates 4 generations of ancestor data for the new user,
        logs the user in,
        and returns an auth token.
    */
        RegisterResult result = new RegisterResult();
        EncoderDecoder ED = new EncoderDecoder();
        ServiceGeneral SG = new ServiceGeneral();
        Connection conn = db.getConnection();

        UserDao userDao = new UserDao(conn);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);

        User modelUser;
        AuthToken modelAuthToken;
        try {
            modelUser = ED.decodeUser(ED.encodeRegisterRequest(request));
            modelUser.setPersonID(SG.getRandIDNum());
            String tempstr = modelUser.getUsername();

            if(userDao.find(tempstr) != null){
                result.setMessage("error: Already registered user");
                result.setSuccess(false);
                db.closeConnection(true);
                return result;
            }

            userDao.insertUser(modelUser);
            modelAuthToken = new AuthToken(SG.getRandIDNum(), modelUser.getUsername());
            authTokenDao.insert(modelAuthToken);
            db.closeConnection(true);
            //fill
            FillService fillService = new FillService();
            fillService.Fill(modelUser.getUsername(),4);

            result.setAuthToken(modelAuthToken.getAuthToken());
            result.setPersonID(modelUser.getPersonID());
            result.setUsername(modelUser.getUsername());
            result.setMessage("User Registered");
            result.setSuccess(true);
            return result;
        } catch (DataAccessException e) {
            result.setMessage("Error in register Service");
            result.setSuccess(false);
            return result;
        }

    }
}
