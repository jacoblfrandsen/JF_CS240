package Service;

import Model.AuthToken;
import Model.Person;
import Results.PeopleResult;
import dao.*;

import java.sql.Connection;
import java.util.ArrayList;

public class PeopleService {
    Database db = new Database();
    /**
     * Constructor
     */
    public PeopleService(Person person){}

    public PeopleService() {}

    /**
     * Return all the persons identified with person
     */
    public PeopleResult People(String authTokenStr) throws DataAccessException {

        Connection conn = db.getConnection();
        PeopleResult peopleResult = new PeopleResult();
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        PersonDao personDao = new PersonDao(conn);
        AuthToken authToken;
        ArrayList<Person> personArrayList;

        try{
            authToken = authTokenDao.getAuthTokenFromToken(authTokenStr);
            personArrayList = personDao.getPeopleFromUsername(authToken.getUsername());

            if(authToken == null){
                peopleResult.setSuccess(false);
                peopleResult.setMessage("error: people not authorized");
                db.closeConnection(true);
                return peopleResult;
            }
            if(personArrayList == null){
                peopleResult.setSuccess(false);
                peopleResult.setMessage("error: people not authorized");
                db.closeConnection(true);
                return peopleResult;
            }
            if(personArrayList.size() ==0){
                peopleResult.setSuccess(false);
                peopleResult.setMessage("error: people not authorized");
                db.closeConnection(true);
                return peopleResult;
            }

            peopleResult.setData(personArrayList);
            peopleResult.setSuccess(true);
            peopleResult.setMessage("success: array populated");
            db.closeConnection(true);
            return peopleResult;
        }
        catch (DataAccessException e){
            peopleResult.setData(null);
            peopleResult.setMessage("error in people service");
            peopleResult.setSuccess(false);
            return peopleResult;
        }
    }

}
