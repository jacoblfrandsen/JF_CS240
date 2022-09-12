package Service;

import Model.AuthToken;
import Model.Event;
import Results.EventResult;
import Results.RegisterResult;
import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.EventDao;

import java.sql.Connection;
import java.sql.SQLException;

public class EventService {
    Database db = new Database();
    /**
     * Constructor
     */
    public EventService(){};

    /**
     * returns single event using event ID
     */
    public EventResult event(String authTokenStr, String eventID) throws DataAccessException {
        EventResult result = new EventResult();
        EncoderDecoder ED = new EncoderDecoder();
        ServiceGeneral SG = new ServiceGeneral();
        Connection conn = db.getConnection();

        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        EventDao eventDao = new EventDao(conn);
        AuthToken authToken;
        Event event;
        try{
            authToken = authTokenDao.getAuthTokenFromToken(authTokenStr);
            event = eventDao.getEventFromID(eventID);

            if(authToken == null){
                result.setSuccess(false);
                result.setMessage("error: not authorized");
                db.closeConnection(true);
                return result;
            }

            if(!(event.getUsername().equals(authToken.getUsername()))){
                result.setSuccess(false);
                result.setMessage("error: no authtoken connected to username");
                db.closeConnection(true);
                return result;
            }
            result = ED.decodeEventResult(ED.encodeEvent(event));

            result.setSuccess(true);
            db.closeConnection(true);
            return result;
        }
        catch (DataAccessException e){
            e.printStackTrace();
            result.setMessage("error in event");
            result.setSuccess(false);
            return result;
        }


    }

}
