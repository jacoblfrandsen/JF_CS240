package ServerProxy;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import Family.Map.Client.ServerProxy.ServerProxy;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.EventsResult;
import Results.LoginResult;
import Results.PeopleResult;
import Results.RegisterResult;

public class Login {

    private URL testURL;
    private ServerProxy proxy;

    @Before
    public void setUP(){
        proxy = new ServerProxy();
        try{
            testURL = new URL("http://");
        } catch (Exception e ){
            assertEquals("Throwing exception", e.getMessage());
        }

    }

    @After
    public void tearDown(){
        testURL = null;
        proxy = null;
    }
    @Test
    private void LoginTest() throws MalformedURLException {
        ServerProxy serverProxy = new ServerProxy();
        LoginRequest loginRequest = new LoginRequest();
        LoginResult loginResult = new LoginResult();
        URL url = new URL("http://10.0.2.2:8080/user/login");
        loginRequest.setUsername("Jfran33-Username");
        loginRequest.setPassword("password");
        loginResult = serverProxy.login(url,loginRequest);
        assertTrue(loginResult.getUsername().equals("Jfran33-Username"));
    }
    @Test
    private void RegisterTest(){
        ServerProxy serverProxy = new ServerProxy();
        RegisterRequest request = new RegisterRequest();
        request.setPort("8080");
        request.setHost("127.0.0.1");
        request.setUsername("username2");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("first");
        request.setLastName("last");
        try {
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        RegisterResult out = proxy.register(testURL, request);

        String authToken = out.getAuthToken();
        try {
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/event/");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }


        EventsResult outTwo = proxy.getEvents(testURL, authToken);

        assertEquals(outTwo.getData().size(), 91);
        assertTrue(outTwo.getSuccess());
    }
    @Test
    private void retrievePeople(){
        RegisterRequest request = new RegisterRequest();
        request.setPort("8080");
        request.setHost("127.0.0.1");
        request.setUsername("username3");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("first");
        request.setLastName("last");
        try {
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }



        RegisterResult out = proxy.register(testURL, request);

        String authToken = out.getAuthToken();
        try {
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/person/");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        PeopleResult outTwo = proxy.getPeople(testURL, authToken);

        assertEquals(outTwo.getData().size(), 31);
        assertTrue(outTwo.getSuccess());

    }
    @Test
    private void retrieveEvents(){
        RegisterRequest request = new RegisterRequest();
        request.setPort("8080");
        request.setHost("127.0.0.1");
        request.setUsername("username2");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("first");
        request.setLastName("last");
        try {
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        RegisterResult out = proxy.register(testURL, request);

        String authToken = out.getAuthToken();
        try {
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/event/");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }


        EventsResult outTwo = proxy.getEvents(testURL, authToken);

        assertEquals(outTwo.getData().size(), 91);
        assertTrue(outTwo.getSuccess());
    }



}
