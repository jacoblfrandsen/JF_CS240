package Service;

import Requests.LoadRequest;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.*;
import dao.DataAccessException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTests_OtherTests {
    @Test
    public void EventTest() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.Clear();
        RegisterRequest request = new RegisterRequest();
        RegisterResult registerResult = new RegisterResult();
        RegisterService registerService = new RegisterService();

        request.setUsername("Jfran33-Username");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Jacob");
        request.setLastName("Frandsen");
        request.setGender("m");
        registerResult = registerService.register(request);

        LoginService loginService = new LoginService();
        LoginRequest loginRequest = new LoginRequest();
        LoginResult loginResult = new LoginResult();

        loginRequest.setUsername("testUsername");
        loginRequest.setPassword("testpassword");

        loginResult = loginService.login(loginRequest);

        EventsResult eventsResult = new EventsResult();
        EventsService eventsService = new EventsService();

        eventsResult = eventsService.events(loginResult.getAuthtoken());

        assertTrue(!eventsResult.getSuccess());
    }

    @Test
    public void EventTestFail() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.Clear();
        RegisterRequest request = new RegisterRequest();
        RegisterResult registerResult = new RegisterResult();
        RegisterService registerService = new RegisterService();

        request.setUsername("Jfran33-Username");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Jacob");
        request.setLastName("Frandsen");
        request.setGender("m");
        registerResult = registerService.register(request);

        LoginService loginService = new LoginService();
        LoginRequest loginRequest = new LoginRequest();
        LoginResult loginResult = new LoginResult();

        loginRequest.setUsername("testUsername");
        loginRequest.setPassword("testpassword");

        loginResult = loginService.login(loginRequest);

        EventsResult eventsResult = new EventsResult();
        EventsService eventsService = new EventsService();

        eventsResult = eventsService.events(loginResult.getAuthtoken());

        assertFalse(eventsResult.getSuccess());
    }


    @Test
    public void EventsTest() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.Clear();
        RegisterRequest request = new RegisterRequest();
        RegisterResult registerResult = new RegisterResult();
        RegisterService registerService = new RegisterService();
        LoginService loginService = new LoginService();
        LoginRequest loginRequest = new LoginRequest();
        LoginRequest loginRequest2 = new LoginRequest();
        LoginResult loginResult = new LoginResult();
        LoginResult loginResult2 = new LoginResult();
        EventsResult eventsResult = new EventsResult();
        EventsService eventsService = new EventsService();

        request.setUsername("Jfran33-Username");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Jacob");
        request.setLastName("Frandsen");
        request.setGender("m");

        registerResult = registerService.register(request);

        loginRequest.setUsername("testUsername");
        loginRequest.setPassword("testpassword");

        loginResult = loginService.login(loginRequest);

        eventsResult = eventsService.events(loginResult.getAuthtoken());

        assertTrue(!eventsResult.getSuccess());

        clearService.Clear();
    }
    @Test
    public void EventsTestFail() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.Clear();
        RegisterRequest request = new RegisterRequest();
        RegisterResult registerResult = new RegisterResult();
        RegisterService registerService = new RegisterService();
        LoginService loginService = new LoginService();
        LoginRequest loginRequest = new LoginRequest();
        LoginRequest loginRequest2 = new LoginRequest();
        LoginResult loginResult = new LoginResult();
        LoginResult loginResult2 = new LoginResult();
        EventsResult eventsResult = new EventsResult();
        EventsService eventsService = new EventsService();

        request.setUsername("Jfran33-Username");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Jacob");
        request.setLastName("Frandsen");
        request.setGender("m");

        registerResult = registerService.register(request);

        loginRequest.setUsername("testUsername");
        loginRequest.setPassword("testpassword");

        loginResult = loginService.login(loginRequest);

        eventsResult = eventsService.events(loginResult.getAuthtoken());

        assertFalse(eventsResult.getSuccess());

        clearService.Clear();
    }
    @Test
    public void fillTest() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.Clear();
        RegisterRequest request = new RegisterRequest();
        RegisterResult registerResult = new RegisterResult();
        RegisterService registerService = new RegisterService();

        request.setUsername("Jfran33-Username");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Jacob");
        request.setLastName("Frandsen");
        request.setGender("m");
        registerResult = registerService.register(request);

        FillService fillService = new FillService();
        FillResult fillResult = new FillResult();

        fillResult = fillService.Fill(registerResult.getUsername(),4);
        assertTrue(fillResult.getSuccess().equals(true));
        clearService.Clear();
    }

    @Test
    public void fillTestFail() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.Clear();
        RegisterRequest request = new RegisterRequest();
        RegisterResult registerResult = new RegisterResult();
        RegisterService registerService = new RegisterService();

        request.setUsername("Jfran33-Username");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Jacob");
        request.setLastName("Frandsen");
        request.setGender("m");
        registerResult = registerService.register(request);

        FillService fillService = new FillService();
        FillResult fillResult = new FillResult();

        fillResult = fillService.Fill(registerResult.getUsername(),4);
        assertFalse(fillResult.getSuccess().equals(false));
        clearService.Clear();

    }
    @Test
    public void loadTest() throws DataAccessException, IOException {
        ClearService clearService = new ClearService();
        clearService.Clear();
        File file = new File("/Users/jacobfrandsen/Desktop/Code/CS_240/FamilyMapServerStudent-master/json/testData.json");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String str = new String(data);
        EncoderDecoder ED = new EncoderDecoder();

        LoadRequest loader = ED.decodeLoadRequest(str);
        assertTrue( loader != null);
        clearService.Clear();
    }

    @Test
    public void loadTestFail() throws DataAccessException, IOException {
        ClearService clearService = new ClearService();
        clearService.Clear();
        File file = new File("/Users/jacobfrandsen/Desktop/Code/CS_240/FamilyMapServerStudent-master/json/testData.json");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String str = new String(data);
        EncoderDecoder ED = new EncoderDecoder();

        LoadRequest loader = ED.decodeLoadRequest(str);
        assertFalse( loader == null);
        clearService.Clear();
    }

    @Test
    public void personTest() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.Clear();
        RegisterRequest request = new RegisterRequest();
        RegisterResult registerResult = new RegisterResult();
        RegisterService registerService = new RegisterService();

        request.setUsername("Jfran33-Username");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Jacob");
        request.setLastName("Frandsen");
        request.setGender("m");
        registerResult = registerService.register(request);

        PersonResult personResult = new PersonResult();
        PersonService personService = new PersonService();
        personResult = personService.person(registerResult.getAuthToken(),registerResult.getPersonID());
        assertTrue(personResult.getSuccess().equals(true));
        assertTrue(personResult.getAssociatedUsername().equals("Jfran33-Username"));
        clearService.Clear();

    }
    @Test
    public void personTestFail() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.Clear();
        RegisterRequest request = new RegisterRequest();
        RegisterResult registerResult = new RegisterResult();
        RegisterService registerService = new RegisterService();

        request.setUsername("Jfran33-Username");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Jacob");
        request.setLastName("Frandsen");
        request.setGender("m");
        registerResult = registerService.register(request);

        PersonResult personResult = new PersonResult();
        PersonService personService = new PersonService();
        personResult = personService.person(registerResult.getAuthToken(),registerResult.getPersonID());
        assertFalse(personResult.getSuccess().equals(false));
        assertTrue(personResult.getAssociatedUsername().equals("Jfran33-Username"));

        clearService.Clear();
    }

    @Test
    public void peopleTest() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.Clear();
        RegisterRequest request = new RegisterRequest();
        RegisterResult registerResult = new RegisterResult();
        RegisterService registerService = new RegisterService();

        request.setUsername("Jfran33-Username");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Jacob");
        request.setLastName("Frandsen");
        request.setGender("m");
        registerResult = registerService.register(request);

        PeopleService peopleService = new PeopleService();
        PeopleResult peopleResult = new PeopleResult();

        peopleResult = peopleService.People(registerResult.getAuthToken());
        assertTrue(peopleResult.getSuccess().equals(true));
    }
    @Test
    public void peopleTestFail() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.Clear();
        RegisterRequest request = new RegisterRequest();
        RegisterResult registerResult = new RegisterResult();
        RegisterService registerService = new RegisterService();

        request.setUsername("Jfran33-Username");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Jacob");
        request.setLastName("Frandsen");
        request.setGender("m");
        registerResult = registerService.register(request);

        PeopleService peopleService = new PeopleService();
        PeopleResult peopleResult = new PeopleResult();

        peopleResult = peopleService.People(registerResult.getAuthToken());
        assertFalse(peopleResult.getSuccess().equals(false));
        clearService.Clear();
    }






}
