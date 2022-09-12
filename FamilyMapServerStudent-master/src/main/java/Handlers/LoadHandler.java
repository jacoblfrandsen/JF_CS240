package Handlers;

import Requests.LoadRequest;
import Requests.RegisterRequest;
import Results.LoadResult;
import Results.RegisterResult;
import Service.EncoderDecoder;
import Service.LoadService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoadService loadService = new LoadService();
        EncoderDecoder ED = new EncoderDecoder();
        LoadRequest loadRequest;
        LoadResult loadResult;
        boolean success = false;

        try{
            if (exchange.getRequestMethod().toLowerCase().equals("post")){
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);

                LoadRequest request = ED.decodeLoadRequest(reqData);

                LoadResult result = loadService.load(request); //Actually doing the register
                OutputStream resBody = exchange.getResponseBody();

                String jsonStr = ED.encodeLoadResult(result);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                writeString(jsonStr, resBody);
                resBody.close();
                success = true;
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
