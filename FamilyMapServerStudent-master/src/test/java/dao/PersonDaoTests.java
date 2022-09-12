package dao;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class PersonDaoTests {
    private Database db;
    private Person testPerson;
    private Person testPerson2;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        testPerson = new Person("1111", "bob123", "bob",
                "billy", "m", "2222", "3333", "4444");
        testPerson2 = new Person(null, "bob123", "bob",
                "billy", "m", "2222", "3333", "4444");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        pDao = new PersonDao(conn);
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

        pDao.insertPerson(testPerson);
        //So lets use a find method to get the event that we just put in back out
        Person compareTest = pDao.find(testPerson.getPersonID_ID());
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(testPerson, compareTest);
    }
    @Test
    public void insertFail() throws DataAccessException {
        //throws because testuser2 has now username
        assertThrows(DataAccessException.class, ()-> pDao.insertPerson(testPerson2));
    }
    @Test
    public void findPass() throws DataAccessException {
        //search for something that is there and check if it found the right thing
        pDao.insertPerson(testPerson);
        Person compareTest = pDao.find(testPerson.getPersonID_ID());
        assertNotNull(compareTest);
        assertEquals(testPerson, compareTest);
    }
    @Test
    public void findFail() throws DataAccessException {
        //search for something not there should return null(nothing there)
        Person compareTest = pDao.find(testPerson.getPersonID_ID());
        assertNull(compareTest);
    }
    @Test
    public void clearTest() throws DataAccessException {
        //checks to see if it can still find testUser
        pDao.insertPerson(testPerson);
        pDao.clear();
        Person compareTest = pDao.find(testPerson.getPersonID_ID());
        assertNull(compareTest);
    }
}
