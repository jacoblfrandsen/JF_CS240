package Results;

import Model.Event;

import java.util.ArrayList;

public class EventsResult {
    private ArrayList<Event> data;
    private String message;
    private Boolean success;

    /**
     * Constructor
     */
    public EventsResult(ArrayList<Event> data, String message, Boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public EventsResult() {}

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
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
