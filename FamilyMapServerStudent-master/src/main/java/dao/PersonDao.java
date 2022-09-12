package dao;

import Model.Event;
import Model.Person;
import Model.User;


import java.sql.*;
import java.util.ArrayList;

public class PersonDao {
    private final Connection conn;

    /**
     * PersonDao Constructor
     */
    public PersonDao(Connection conn){

        this.conn = conn;
    }



    /**
     * Insert Person
     */
    public void insertPerson(Person person) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Person (PersonID_ID, Associated_Username, Firstname, Lastname, " +
                "Gender, FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID_ID());
            stmt.setString(2, person.getAssociated_username());
            stmt.setString(3, person.getFirstname());
            stmt.setString(4, person.getLastname());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
    /**
     * Retrieve Person
     */
    Person find(String PersonID_ID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE PersonID_ID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, PersonID_ID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("PersonID_ID"), rs.getString("Associated_Username"),
                        rs.getString("Firstname"), rs.getString("Lastname"), rs.getString("Gender"),
                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
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
     * Clear Person
     */
    void clear() throws DataAccessException{
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Person";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing Person");
        }
    }

    public Person getPersonFromID(String eventID) throws DataAccessException {
        Person person = null;

        String sql = "select * from person where personID_ID = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                person = new Person(
                        resultSet.getString("personID_ID"),
                        resultSet.getString("associated_Username"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("gender"),
                        resultSet.getString("fatherID"),
                        resultSet.getString("motherID"),
                        resultSet.getString("spouseID")
                );
            }
            resultSet.close();
            stmt.close();
            return person;
        } catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException();
        }
    }

    public ArrayList<Person> getPeopleFromUsername(String personID) throws DataAccessException {
        Person person;
        ArrayList<Person> People = new ArrayList<>();
        String sql = "select * from person where Associated_Username = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                person = new Person(
                        resultSet.getString("personID_ID"),
                        resultSet.getString("associated_Username"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("gender"),
                        resultSet.getString("fatherID"),
                        resultSet.getString("motherID"),
                        resultSet.getString("spouseID")
                );
                People.add(person);
            }
            resultSet.close();
            stmt.close();
            return People;
        } catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException();
        }
    }

    public void removePersonbyUsername(String username) throws DataAccessException {
        String sql = "delete from person where associated_Username = ? ";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new DataAccessException();
        }
    }
}
