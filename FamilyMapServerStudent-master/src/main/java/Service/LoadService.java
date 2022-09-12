package Service;

import Model.Event;
import Model.Person;
import Model.User;
import Requests.LoadRequest;
import Results.LoadResult;
import dao.*;

import java.sql.Connection;

public class LoadService {
    Database db = new Database();
    /**
     * Constructor
     */
    public LoadService (User user){}

    public LoadService() {}

    /**
     * Load the users Data into the database
     */
    public LoadResult load(LoadRequest loadRequest) throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.Clear();// clear all the tables

        LoadResult loadResult = new LoadResult();
        Connection conn = db.getConnection();
        EventDao eventDao = new EventDao(conn);
        UserDao userDao = new UserDao(conn);
        PersonDao personDao = new PersonDao(conn);

        int eventNum = 0;
        int personNum = 0;
        int userNum = 0;
        try {
            for (Event event : loadRequest.getEvents()) {
                eventDao.insert(event);
                eventNum++;
            }
            for (Person person : loadRequest.getPersons()) {
                personDao.insertPerson(person);
                personNum++;
            }
            for (User user : loadRequest.getUsers()) {
                userDao.insertUser(user);
                userNum++;
            }
            loadResult.setMessage("Successfully added " + userNum + " users, " + personNum + " persons, and " + eventNum + " events to the database.");
            loadResult.setSuccess(true);
            db.closeConnection(true);
            return loadResult;
        }
        catch (DataAccessException e){
            loadResult.setMessage("error in load Service");
            loadResult.setSuccess(false);
            return loadResult;
        }
    }

}
