package dao;

import Model.AuthToken;
import dao.DataAccessException;
import dao.Database;
import dao.AuthTokenDao;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class AuthTokenDaoTests {
    private Database db;
    private AuthToken testToken;
    private AuthToken testToken2;
    private AuthTokenDao aDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        testToken = new AuthToken("11111", "12345");
        testToken2 = new AuthToken(null, null);
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        aDao = new AuthTokenDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        //While insert returns a bool we can't use that to verify that our function actually worked
        //only that it ran without causing an error

        aDao.insert(testToken);
        //So lets use a find method to get the event that we just put in back out
        AuthToken compareTest = aDao.find(testToken.getUsername());
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(testToken, compareTest);
    }
    @Test
    public void insertFail() throws DataAccessException {
        //throws because authToken2 has now username
        assertThrows(DataAccessException.class, ()-> aDao.insert(testToken2));
    }
    @Test
    public void findPass() throws DataAccessException {
        //search for something that is there and check if it found the right thing
        aDao.insert(testToken);
        AuthToken compareTest = aDao.find(testToken.getUsername());
        assertNotNull(compareTest);
        assertEquals(testToken, compareTest);
    }
    @Test
    public void findFail() throws DataAccessException {
        //search for something not there should return null(nothing there)
        AuthToken compareTest = aDao.find(testToken.getUsername());
        assertNull(compareTest);
    }
    @Test
    public void clearTest() throws DataAccessException {
        //checks to see if it can still find testUser
        aDao.insert(testToken);
        aDao.clear();
        AuthToken compareTest = aDao.find(testToken.getUsername());
        assertNull(compareTest);
    }
}
