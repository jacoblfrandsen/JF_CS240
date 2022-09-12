package Handlers;

import Results.EventResult;
import Results.EventsResult;
import Results.FillResult;
import Service.EncoderDecoder;
import Service.EventService;
import Service.EventsService;
import Service.FillService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;

import java.io.*;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        FillService fillService = new FillService();
        EncoderDecoder encoderDecoder = new EncoderDecoder();
        FillResult result = null;
        String [] urID;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                urID = exchange.getRequestURI().toString().split("/");

                if (urID.length > 3) {
                    result = fillService.Fill(urID[2], Integer.parseInt(urID[3]));

                } else {
                    result = fillService.Fill(urID[2], 4);
                }
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(encoderDecoder.encodeFillResult(result), respBody);
                respBody.close();
            }
        }
        catch (IOException | DataAccessException e){
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
