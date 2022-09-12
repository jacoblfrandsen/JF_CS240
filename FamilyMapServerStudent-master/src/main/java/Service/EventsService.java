package Service;

import Model.AuthToken;
import Model.Event;
import Model.User;
import Results.EventsResult;
import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.EventDao;

import java.sql.Connection;
import java.util.ArrayList;

public class EventsService {
    Database db = new Database();
    /**
     * Constructor
     */
    public EventsService(User user){}

    public EventsService() {}

    /**
     * return all the events for all family members of tree
     */
    public EventsResult events(String authTokenStr) throws DataAccessException {
        Connection conn = db.getConnection();
        EventsResult eventsResult = new EventsResult();
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        EventDao eventDao = new EventDao(conn);
        AuthToken authToken;
        ArrayList<Event> eventArrayList;

        try{
            authToken = authTokenDao.getAuthTokenFromToken(authTokenStr);
            eventArrayList = eventDao.getEventsFromUsername(authToken.getUsername());

            if(authToken == null){
                eventsResult.setSuccess(false);
                eventsResult.setMessage("error: events not authorized");
                db.closeConnection(true);
                return eventsResult;
            }
            if(eventArrayList == null){
                eventsResult.setSuccess(false);
                eventsResult.setMessage("error: events not authorized");
                db.closeConnection(true);
                return eventsResult;
            }
            if(eventArrayList.size() ==0){
                eventsResult.setSuccess(false);
                eventsResult.setMessage("error: people not authorized");
                db.closeConnection(true);
                return eventsResult;
            }
            eventsResult.setData(eventArrayList);
            eventsResult.setSuccess(true);
            db.closeConnection(true);
            return eventsResult;
        }
        catch (DataAccessException e){
            eventsResult.setData(null);
            eventsResult.setMessage("error in events service");
            eventsResult.setSuccess(false);
            return eventsResult;
        }

    }

}
