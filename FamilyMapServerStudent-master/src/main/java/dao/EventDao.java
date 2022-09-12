package dao;

import Model.Event;

import java.sql.*;
import java.util.ArrayList;

public class EventDao {
    private final Connection conn;

    public EventDao(Connection conn)
    {
        this.conn = conn;
    }

    public void insert(Event event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Event (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4,  event.getLatitude());
            stmt.setFloat(5,  event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
    /**
     * Clear Event
     */
    void clear() throws DataAccessException{
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Event";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing Event");
        }
    }

    public Event getEventFromID(String eventID) throws DataAccessException {
        Event newEvent = null;

        String sql = "select * from event where eventID = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                newEvent = new Event(
                        resultSet.getString("eventID"),
                        resultSet.getString("associatedUsername"),
                        resultSet.getString("personID"),
                        resultSet.getFloat("latitude"),
                        resultSet.getFloat("longitude"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getString("eventType"),
                        resultSet.getInt("year")
                );
            }
            resultSet.close();
            stmt.close();
            return newEvent;
        } catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException();
        }
    }

    public ArrayList<Event> getEventsFromUsername(String username) throws DataAccessException {
        Event event;
        ArrayList<Event> newEvent = new ArrayList<>();
        String sql = "select * from event where AssociatedUsername = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                event = new Event(
                        resultSet.getString("eventID"),
                        resultSet.getString("associatedUsername"),
                        resultSet.getString("personID"),
                        resultSet.getFloat("latitude"),
                        resultSet.getFloat("longitude"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getString("eventType"),
                        resultSet.getInt("year"));
                newEvent.add(event);
            }
            resultSet.close();
            stmt.close();
            return newEvent;
        } catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException();
        }
    }

    public void removeEventbyUsername(String username) throws DataAccessException {
        String sql = "delete from event where associatedUsername = ? ";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new DataAccessException();
        }
    }

    public ArrayList<Event> getEventsFromPersonID(String personID_id) throws DataAccessException {
        Event event;
        ArrayList<Event> newEvent = new ArrayList<>();
        String sql = "select * from event where PersonID = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, personID_id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                event = new Event(
                        resultSet.getString("eventID"),
                        resultSet.getString("associatedUsername"),
                        resultSet.getString("personID"),
                        resultSet.getFloat("latitude"),
                        resultSet.getFloat("longitude"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getString("eventType"),
                        resultSet.getInt("year"));
                newEvent.add(event);
            }
            resultSet.close();
            stmt.close();
            return newEvent;
        } catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException();
        }
    }
}
