package Service;

import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Requests.LoadRequest;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class EncoderDecoder {
    Gson gson = new Gson();

    //decode functions into a jSON string to be read by the program(gets from the client)

    //Models
    public AuthToken decodeAuthToken(String jSON){return gson.fromJson(jSON, AuthToken.class);}
    public Event decodeEvent(String jSON){return gson.fromJson(jSON, Event.class);}
    public Person decodePerson(String jSON){return gson.fromJson(jSON, Person.class);}
    public User decodeUser(String jSON){return gson.fromJson(jSON, User.class);}
    //Requests
    public LoadRequest decodeLoadRequest(String jSON){return gson.fromJson(jSON, LoadRequest.class);}
    public LoginRequest decodeLoginRequest(String jSON){return gson.fromJson(jSON, LoginRequest.class);}
    public RegisterRequest decodeRegisterRequest(String jSON){return gson.fromJson(jSON, RegisterRequest.class);}
    //Results
    public EventResult decodeEventResult(String jSON){return gson.fromJson(jSON, EventResult.class);}
    public PersonResult decodePersonResult(String encodePersons) {return gson.fromJson(encodePersons, PersonResult.class);}

    // encode returns the argument as a string
    public String encodeUsers(User user){ return gson.toJson(user); }
    public String encodePersons(Person person){ return gson.toJson(person); }
    public String encodeEvent(Event event){ return gson.toJson(event); }
    public String encodeAuthToken(AuthToken authToken){ return gson.toJson(authToken); }
    //requests
    public String encodeRegisterRequest(RegisterRequest register){ return gson.toJson(register); }
    public String encodeLoginRequest(RegisterRequest login){ return gson.toJson(login); }
    public String encodeLoadRequest(RegisterRequest load){ return gson.toJson(load); }
    // results
    public String encodeRegisterResult(RegisterResult register){ return gson.toJson(register); }
    public String encodeEventResult(EventResult event){ return gson.toJson(event); }
    public String encodePersonResult(PersonResult person) {return gson.toJson(person);}
    public String encodeLoadResult(LoadResult result) {return gson.toJson(result);}
    public String encodeLoginResult(LoginResult result) {return gson.toJson(result);}
    public String encodeEventsResult(EventsResult eventsResult) {return gson.toJson(eventsResult);}
    public String encodePeopleResult(PeopleResult peopleResult) {return gson.toJson(peopleResult);}
    public String encodeFillResult(FillResult result) {return gson.toJson(result);}

    public Event decodeEvents(JsonObject locationFromFile) { return gson.fromJson(gson.toJson(locationFromFile), Event.class);}
}
