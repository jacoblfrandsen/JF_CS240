package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        boolean success = false;
        try{
            String urlPath = exchange.getRequestURI().toString();
            if(urlPath == null || urlPath.equals("/")){
                urlPath = "/index.html";
            }
            String filePath = "web" + urlPath;
            String nonExistFile = "web/HTML/404.html";
            File file = new File(filePath);
            OutputStream respBody = exchange.getResponseBody();

            if(file.exists()){
                exchange.sendResponseHeaders(200, 0);
                Files.copy(file.toPath(),respBody);
                exchange.getResponseBody().close();
            }
            else {
                exchange.sendResponseHeaders(404, 0);
                file = new File(nonExistFile);
                Files.copy(file.toPath(),respBody);
                exchange.getResponseBody().close();
            }
            }
            catch (IOException e){
                e.printStackTrace();
            }

    }
}
