package Service;

import Results.ClearResult;
import dao.DataAccessException;
import dao.Database;

import java.sql.SQLException;


public class ClearService {
    private Database db = new Database();
    private ClearResult result = new ClearResult();
    /**
     * Constructor
     */
    public ClearService(){

    }
    /**
     * ClearingDB
     */
    public ClearResult Clear() throws DataAccessException{
        try {

            db.getConnection();
            db.clearTables();
            db.closeConnection(true);
            result.setSuccess(true);
            result.setMessage("Clear succeeded.");
        }
        catch (DataAccessException e){
            System.out.println(e.getMessage());
            result.setMessage("Internal server error");
            try{
                db.closeConnection(false);
            }catch (DataAccessException d){
                result.setMessage(d.getMessage());
                return result;
            }
        }
    return result;
    }

}
