package Handlers;

import Model.AuthToken;
import Requests.RegisterRequest;
import Results.EventResult;
import Results.EventsResult;
import Results.RegisterResult;
import Service.EncoderDecoder;
import Service.EventService;
import Service.EventsService;
import Service.RegisterService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;

import java.io.*;
import java.net.HttpURLConnection;

public class EventHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try{
            EventService eventService = new EventService();
            EventsService eventsService = new EventsService();
            EncoderDecoder encoderDecoder = new EncoderDecoder();
            EventResult result = null;
            EventsResult eventsResult = null;
            String authToken = null;
            String [] urID;

            if (exchange.getRequestMethod().toLowerCase().equals("get")){
                if(exchange.getRequestHeaders().containsKey("Authorization")){
                    authToken = exchange.getRequestHeaders().getFirst("Authorization");
                }
                urID = exchange.getRequestURI().toString().split("/");
                if(urID.length > 2){
                    result = eventService.event(authToken,urID[2]); //Actually doing the service the urID[2] is the event ID that is passed in
                    if(result.getSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    OutputStream resBody = exchange.getResponseBody();
                    String jsonStr = encoderDecoder.encodeEventResult(result);
                    writeString(jsonStr, resBody);
                    resBody.close();
                    success = true;
                }
                else{
                    eventsResult = eventsService.events(authToken);
                    if(eventsResult.getSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    OutputStream resBody = exchange.getResponseBody();
                    String jsonStr = encoderDecoder.encodeEventsResult(eventsResult);
                    writeString(jsonStr, resBody);
                    resBody.close();
                    success = true;
                }

            }
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
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
