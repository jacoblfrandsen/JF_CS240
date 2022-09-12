package Family.Map.Client.Data;

import Model.Event;
import Model.Person;

public class EventDisplay {
    String text;
    String type;
    String IDofLine;
    private String gender;
    private String relation;
    private String eventDetailsString;
    private String name;
    private String eventType;
    private Integer year;
    private Event event;
    private Person person;


    public EventDisplay(String topRow, String type, String Id){
        this.text = topRow;
        this.type = type;
        this.IDofLine = Id;
    }
    public String getLineText(){
        return text;
    }

    public String getLineType(){
        return type;
    }

    public String getLinePersonID() {
        return IDofLine;
    }


    public EventDisplay(Event event){
        this.event = event;
        setName(DataCache.getDataCache().getPersonFromEvent(event).getFirstname() + " " + DataCache.getDataCache().getPersonFromEvent(event).getLastname());
        setEventDetails(DataCache.getDataCache().getEventSpecificsString(event));
        setYear(event.getYear());
        setEventType(event.getEventType());
    }
    public EventDisplay(Person person) {
        this.person = person;
        setName(person.getFirstname() + " " + person.getLastname());
        setGender(person.getGender());

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIDofLine() {
        return IDofLine;
    }

    public void setIDofLine(String IDofLine) {
        this.IDofLine = IDofLine;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getEventDetails() {
        return eventDetailsString;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetailsString = eventDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


}
