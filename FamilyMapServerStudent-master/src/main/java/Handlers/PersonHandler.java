package Handlers;

import Results.EventResult;
import Results.PeopleResult;
import Results.PersonResult;
import Service.EncoderDecoder;
import Service.EventService;
import Service.PeopleService;
import Service.PersonService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;

import java.io.*;
import java.net.HttpURLConnection;

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            PersonService personService = new PersonService();
            EncoderDecoder encoderDecoder = new EncoderDecoder();
            PersonResult result;
            PeopleResult peopleResult;
            PeopleService peopleService = new PeopleService();
            String authToken = null;
            String [] urID;

            if (exchange.getRequestMethod().toLowerCase().equals("get")){
                if(exchange.getRequestHeaders().containsKey("Authorization")){
                    authToken = exchange.getRequestHeaders().getFirst("Authorization");
                }
                urID = exchange.getRequestURI().toString().split("/");
                if(urID.length > 2){
                    result = personService.person(authToken,urID[2]); //Actually doing the service the urID[2] is the event ID that is passed in
                    if(result.getSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    OutputStream resBody = exchange.getResponseBody();
                    String jsonStr = encoderDecoder.encodePersonResult(result);
                    writeString(jsonStr, resBody);
                    resBody.close();
                }
                else{
                    peopleResult = peopleService.People(authToken);//people service
                    if(peopleResult.getSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    OutputStream resBody = exchange.getResponseBody();
                    String jsonStr = encoderDecoder.encodePeopleResult(peopleResult);
                    writeString(jsonStr, resBody);
                    resBody.close();
                }

            }
        }
        catch(IOException | DataAccessException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR,0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}

