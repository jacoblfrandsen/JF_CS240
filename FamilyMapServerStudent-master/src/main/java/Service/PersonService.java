package Service;

import Model.AuthToken;
import Model.Event;
import Model.Person;
import Results.EventResult;
import Results.PersonResult;
import dao.*;

import java.sql.Connection;


public class PersonService {
    Database db = new Database();
    /**
     * Constructor
     */
    public PersonService(String personID){};

    public PersonService() {
    }

    /**
     * Returns the single Person object with the specified ID.
     */
    public PersonResult person(String authTokenStr, String eventID) throws DataAccessException {

        PersonResult result = new PersonResult();
        EncoderDecoder ED = new EncoderDecoder();
        ServiceGeneral SG = new ServiceGeneral();
        Connection conn = db.getConnection();

        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        PersonDao personDao = new PersonDao(conn);
        AuthToken authToken;
        Person person;
        try{
            authToken = authTokenDao.getAuthTokenFromToken(authTokenStr);

            person = personDao.getPersonFromID(eventID);

            if(authToken == null){
                result.setSuccess(false);
                result.setMessage("error: not authorized");
                db.closeConnection(true);
                return result;
            }

            if(!(person.getAssociated_username().equals(authToken.getUsername()))){
                result.setSuccess(false);
                result.setMessage("error: no authtoken connecte to username");
                db.closeConnection(true);
                return result;
            }

            result = ED.decodePersonResult(ED.encodePersons(person));


            result.setSuccess(true);
            db.closeConnection(true);
            return result;
        }
        catch (DataAccessException e){
            e.printStackTrace();
            result.setMessage("error in person service");
            result.setSuccess(false);
            return result;
        }
    }

}
