package Results;

import Model.Person;

import java.util.ArrayList;

public class PeopleResult {

    private ArrayList<Person> data;
    private String message;
    private Boolean success;

    /**
     * Constructor
     */
    public PeopleResult(ArrayList<Person> data, String message, Boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public PeopleResult() {}

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
