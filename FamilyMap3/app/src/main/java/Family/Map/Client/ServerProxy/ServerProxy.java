package Family.Map.Client.ServerProxy;


import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.EventsResult;
import Results.LoginResult;
import Results.PeopleResult;
import Results.PersonResult;
import Results.RegisterResult;


public class ServerProxy {


//    LoginResult login(LoginRequest request){}
//
//    RegisterResult register(RegisterRequest request){}
//
//    GetPeopleResult getPeople(GetPeopleRequest request){}
//
//    GetEventsResult getEvents(GetEventsRequest request){}

    public LoginResult login(URL url, LoginRequest loginRequest) {
        Gson gson = new Gson();
        try {

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");

            http.setDoOutput(true);

            http.addRequestProperty("Accept", "application/json");

            String json = gson.toJson(loginRequest);
            OutputStream reqBody = http.getOutputStream();
            writeString(json, reqBody);
            reqBody.close();
            // Connect to the server and send the HTTP request
            http.connect();
            int tempInt = http.getResponseCode();

            if (tempInt == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(http.getInputStream());
                LoginResult result = gson.fromJson(reader, LoginResult.class);
                result.setSuccess(true);
                http.getInputStream().close();
                return result;
            }
            else {
                LoginResult result = new LoginResult();
                result.setSuccess(false);
                result.setMessage(http.getResponseMessage());
                return result;
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return null;
    }

    // The claimRoute method calls the server's "/routes/claim" operation to
    // claim the route between Atlanta and Miami
    public RegisterResult register(URL url, RegisterRequest registerRequest) {

        // This method shows how to send a POST request to a server
        Gson gson = new Gson();
        try {
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");

            http.setDoOutput(true);	// There is a request body

            http.addRequestProperty("Accept", "application/json");

            String json = gson.toJson(registerRequest);
            OutputStream reqBody = http.getOutputStream();
            writeString(json, reqBody);
            reqBody.close();
            // Connect to the server and send the HTTP request
            http.connect();

            int tempInt = http.getResponseCode();
            if (tempInt == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(http.getInputStream());
                RegisterResult result = gson.fromJson(reader, RegisterResult.class);
                result.setSuccess(true);
                http.getInputStream().close();
                return result;
            }
            else {
                RegisterResult result = new RegisterResult();
                result.setSuccess(false);
                result.setMessage(http.getResponseMessage());
                return result;
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return null;

    }

    public PeopleResult getPeople(URL url, String authToken){

        Gson gson = new Gson();
        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");

            http.setDoOutput(false);    // There is a request body
            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");

            http.connect();
            int tempInt = http.getResponseCode();

            if (tempInt == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(http.getInputStream());
                PeopleResult result = gson.fromJson(reader, PeopleResult.class);
                result.setSuccess(true);
                http.getInputStream().close();
                return result;
            }
            else {
                PeopleResult result = new PeopleResult();
                result.setSuccess(false);
                result.setMessage(http.getResponseMessage());
                return result;
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return null;
    }

    public PersonResult getPerson(URL url, String authToken){
        Gson gson = new Gson();
        try{
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");

            http.setDoOutput(false);    // There is a request body
            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");

            http.connect();
            int tempInt = http.getResponseCode();

            if (tempInt == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(http.getInputStream());
                PersonResult result = gson.fromJson(reader, PersonResult.class);
                result.setSuccess(true);
                http.getInputStream().close();
                return result;
            }
            else {
                PersonResult result = new PersonResult();
                result.setSuccess(false);
                result.setMessage(http.getResponseMessage());
                return result;
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return null;
    }
    public EventsResult getEvents(URL url, String authToken) {
        Gson gson = new Gson();
        try{
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");

            http.setDoOutput(false);    // There is a request body
            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");

            http.connect();
            int tempInt = http.getResponseCode();

            if (tempInt == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(http.getInputStream());
                EventsResult result = gson.fromJson(reader, EventsResult.class);
                result.setSuccess(true);
                http.getInputStream().close();
                return result;
            }
            else {
                EventsResult result = new EventsResult();
                result.setSuccess(false);
                result.setMessage(http.getResponseMessage());
                return result;
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return null;
    }

    /*
        The readString method shows how to read a String from an InputStream.
    */
    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


}

