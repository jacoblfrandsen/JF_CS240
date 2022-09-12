package Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class ServiceGeneral {
    public String getRandIDNum(){
        return UUID.randomUUID().toString().replace("-", "").substring(0,8);
    }

    public String nameFromFile(String filename) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(filename);
            NameDataClass dataName = gson.fromJson(reader, NameDataClass.class);
            return dataName.data[ThreadLocalRandom.current().nextInt(0, dataName.data.length)];
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public JsonObject getLocationFromFile(String filename) {
        try{
            JsonParser parser = new JsonParser();
            FileReader reader = new FileReader(filename);
            JsonObject object = (JsonObject) parser.parse(reader);
            JsonArray data = (JsonArray) object.get("data");
            return (JsonObject) data.get(ThreadLocalRandom.current().nextInt(0, data.size()));
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private class NameDataClass {
        public String[] data;
    }
}
