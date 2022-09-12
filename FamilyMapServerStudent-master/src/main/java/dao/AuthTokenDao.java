package dao;

import Model.AuthToken;
import Model.Event;

import java.sql.*;
import java.util.Set;

public class AuthTokenDao {
    private final Connection conn;

    /**
     * AuthTokenDao Constructor
     */
    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Insert Auth Token
     */
    public void insert(AuthToken authToken) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO AuthToken (AuthToken, Username ) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Find AuthToken by person ID
     */
    public AuthToken find(String personID) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthToken WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("AuthToken"), rs.getString("Username"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if (rs != null) {
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
     * Clear AuthToken
     */
    void clear() throws DataAccessException {
        try (Statement stmt = conn.createStatement()) {
            String sql = "DELETE FROM AuthToken";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing Event");
        }
    }


    public AuthToken getAuthTokenFromToken(String authTokenStr) throws DataAccessException {
        AuthToken newAuthToken = new AuthToken("", "");
        ResultSet resultSet;
        String sql = "select * from authToken where authToken = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authTokenStr);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                newAuthToken = new AuthToken(
                        resultSet.getString("authToken"),
                        resultSet.getString("username")
                );
            }
            resultSet.close();
            stmt.close();
            return newAuthToken;

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while getting authToken from token");
        }
    }
}
