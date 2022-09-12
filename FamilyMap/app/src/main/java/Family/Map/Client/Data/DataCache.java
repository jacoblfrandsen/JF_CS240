package Family.Map.Client.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import Model.Event;
import Model.Person;

public class DataCache {

    static DataCache DC = new DataCache();

    private Map<String, Person> peopleMap;
    private Map<String, Event> eventMap;
    private Map<String, List<Event>> personToEventsMap;



    private Boolean maleEvents = false;
    private Boolean femaleEvents = false;
    private Boolean userEvents = false;
    private Person currentUser;
    private List<String> eventsUser;
    private List<String> eventsFatherAncestors;
    private List<String> eventsMotherAncestors;
    private List<String> allEventTypes;
    private Map<String, List<Person>> childrenMap;
    private Set<Person> motherSet;
    private Set<Person> fatherSet;
    private ArrayList<EventDisplay> searchList;
    private UserSettings currentUserSettings;
    private Logger logger;

    private DataCache(){
        peopleMap = new HashMap<>();
        eventMap = new HashMap<>();
        personToEventsMap = new HashMap<>();
        eventsUser = new ArrayList<>();
        eventsFatherAncestors = new ArrayList<>();
        eventsMotherAncestors = new ArrayList<>();
        childrenMap = new HashMap<>();
        motherSet = new HashSet<>();
        fatherSet = new HashSet<>();
        searchList = new ArrayList<>();
        currentUserSettings = new UserSettings();

        logger = Logger.getLogger(DataCache.class.getName());
    }

    public void settingPeopleMap(ArrayList<Person> listPersons){
        currentUser = listPersons.get(0);
        for(int i = 0; i < listPersons.size(); i++){
            peopleMap.put(listPersons.get(i).getPersonID_ID(),listPersons.get(i));
        }
//        Need to set the children of the People
        Person mother = peopleMap.get(currentUser.getMotherID());
        Person father = peopleMap.get(currentUser.getFatherID());

        //add the parents
        addingTheParents(father,false);
        addingTheParents(mother,true);
        for (Map.Entry<String, Person> item:
                peopleMap.entrySet()){
            String maybeParentID = new String(item.getValue().getPersonID_ID());
            List<Person> childrenOfPossible = new ArrayList<>();

            for (Map.Entry<String, Person> entryTwo : peopleMap.entrySet()) {
                if (maybeParentID.equals(entryTwo.getValue().getFatherID()) || maybeParentID.equals(entryTwo.getValue().getMotherID())) {
                    childrenOfPossible.add(entryTwo.getValue());
                }
            }
            childrenMap.put(maybeParentID, childrenOfPossible);
        }

    }

    public void settingEventMap(ArrayList<Event> listEvents){
        for(int i = 0; i < listEvents.size(); i++){
            eventMap.put(listEvents.get(i).getEventID(),listEvents.get(i));


            //isntpersonToeventsMap empty??
            //it should be empty the first time but if it isnt the first time then we have to check to see if one already exists
            //CHECKING
            if(personToEventsMap.containsKey(listEvents.get(i).getPersonID())){ // checks to see if the list<event> associated with the person exists
                //now i need to place the list of events that is associeated with the user in a seperate list of events
                List<Event> eventsFromMap = personToEventsMap.get(listEvents.get(i).getPersonID());
                if(listEvents.get(i).getYear() < eventsFromMap.get(0).getYear()){
                    eventsFromMap.add(0,listEvents.get(i));
                }
                else if(listEvents.get(i).getYear()>eventsFromMap.get(eventsFromMap.size()-1).getYear()){
                    eventsFromMap.add(listEvents.get(i));
                }
                else{
                    for(int k = 0; k <eventsFromMap.size()-1; ++k){
                        if ((listEvents.get(i).getYear() > eventsFromMap.get(k).getYear()) && (!(listEvents.get(i).getYear() > eventsFromMap.get(k + 1).getYear()))) {
                            eventsFromMap.add(k+1, listEvents.get(i));
                        }
                    }
                }
                personToEventsMap.put(listEvents.get(i).getPersonID(), eventsFromMap); // add the events to the person map
            }
            else { // it doesnt exist therfore this is the first one
                List<Event> newList = new ArrayList<>();
                newList.add(listEvents.get(i));
                personToEventsMap.put(listEvents.get(i).getPersonID(), newList);
            }
        }

        createEventListTypes();


    }

    private void createEventListTypes() {
        String fString = "f";
        String mString = "m";

        for (Map.Entry<String, List<Event>> item:
                personToEventsMap.entrySet()) {
            if(maleEvents == true && femaleEvents == true && userEvents == true){
                break;
            }
//            else if (item.getKey().equals(currentUser.getPersonID_ID())){
                //makes the eventtypes for the user
                for(int i = 0; i < item.getValue().size(); i++){
                    eventsUser.add(item.getValue().get(i).getEventType().toLowerCase()); //lowercase??
                }
                maleEvents = true;
//            }
//            else if(peopleMap.get(item.getKey()).getGender().equals(fString) && femaleEvents ==false){
                //makes the eventTypes for the female ancestors
                for(int i = 0; i < item.getValue().size(); i++){
                    eventsMotherAncestors.add(item.getValue().get(i).getEventType().toLowerCase());
                }
                femaleEvents = true;
//            }
//            else if(peopleMap.get(item.getKey()).getGender().equals(mString) && maleEvents ==false) {
                //makes the eventTypes for the male ancestors
                for(int i = 0; i < item.getValue().size(); i++){
                    eventsFatherAncestors.add(item.getValue().get(i).getEventType().toLowerCase());
                }
                maleEvents = true;
//            }
        }

    }


    private void addingTheParents(Person person, Boolean femaleSide){
        if (femaleSide) {
            motherSet.add(person);
        }
        else {
            fatherSet.add(person);
        }

        if (!(person.getFatherID() == null || person.getFatherID().equals(""))) {
            Person motherOfPerson = peopleMap.get(person.getMotherID());
            Person fatherOfPersonReceived = peopleMap.get(person.getFatherID());
            if (femaleSide) {
                addingTheParents(motherOfPerson, true);
                addingTheParents(fatherOfPersonReceived, true);
            } else {
                addingTheParents(motherOfPerson, false);
                addingTheParents(fatherOfPersonReceived, false);
            }
        }
    }

    public static DataCache getDataCache(){
        if(DC == null){
            DC = new DataCache();;
        }
        return DC;
    }

    public void setAllEventTypes(List<String> allEventTypes) {
        this.allEventTypes = allEventTypes;
    }

    public List<String> getEventsUser() {
        return eventsUser;
    }

    public void setEventsUser(List<String> eventsUser) {
        this.eventsUser = eventsUser;
    }

    public List<String> getEventsFatherAncestors() {
        return eventsFatherAncestors;
    }

    public void setEventsFatherAncestors(List<String> eventsFatherAncestors) {
        this.eventsFatherAncestors = eventsFatherAncestors;
    }

    public List<String> getEventsMotherAncestors() {
        return eventsMotherAncestors;
    }

    public void setEventsMotherAncestors(List<String> eventsMotherAncestors) {
        this.eventsMotherAncestors = eventsMotherAncestors;
    }

    public Map<String, Person> getPeopleMap() {
        return peopleMap;
    }

    public void setPeopleMap(Map<String, Person> peopleMap) {
        this.peopleMap = peopleMap;
    }

    public void setEventMap(Map<String, Event> eventMap) {
        this.eventMap = eventMap;
    }

    public Map<String, Event> getEventMap() {
        return eventMap;
    }

    public Person getPersonFromID(String personID) {
        return peopleMap.get(personID);
    }

    public Map<String, List<Event>> getPersonToEventsMap() {
        return personToEventsMap;
    }

    public void setPersonToEventsMap(Map<String, List<Event>> personToEventsMap) {
        this.personToEventsMap = personToEventsMap;
    }

    public Person getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Person currentUser) {
        this.currentUser = currentUser;
    }

    public Event getEventFromID(String centerEvent) {
        return eventMap.get(centerEvent);
    }

    public Person getPersonFromEvent(Event eventFromID) {
        for (Map.Entry<String, Person> entry : peopleMap.entrySet()) {
            if (entry.getValue().getPersonID_ID().equals(eventFromID.getPersonID())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public ArrayList<Event> getOrderLifeEvents(Person personFromEvent,DataCache DC) {
        
        ArrayList<Event> returnEvents = new ArrayList<>();
        List<Event> eventsToDisplay = DC.getEventsOfPersonByPersonId(personFromEvent.getPersonID_ID());
        for (int i = 0; i < eventsToDisplay.size(); i++) {
            if (personFromEvent.getPersonID_ID().equals(eventsToDisplay.get(i).getPersonID())) {
                returnEvents.add(eventsToDisplay.get(i));
            }
        }
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < returnEvents.size() - 1; j++) {
                if (returnEvents.get(j).getYear() > returnEvents.get(j + 1).getYear()) {
                    Collections.swap(returnEvents, j, j + 1);
                }
            }
        }
        return returnEvents;
    }

    public List<Event> getEventsOfPersonByPersonId(String personID_id) {
        return personToEventsMap.get(personID_id);
    }


    public Person findSpouseFromPersonID(Person personFromEvent) {
        try{
            for (Map.Entry<String, Person> entry : peopleMap.entrySet()) {
                if (entry.getValue().getSpouseID().equals(personFromEvent.getPersonID_ID())) {
                    return entry.getValue();
                }
            }
            return null;
        }
        catch (NullPointerException e){
            return null;
        }
    }

    public Event findFirstEvent(Person personSpouse) {
        int earliestYear = Integer.MAX_VALUE;
        Event earliestEvent = null;
        for (Map.Entry<String, Event> entry : eventMap.entrySet()) {
            if (entry.getValue().getPersonID().equals(personSpouse.getPersonID_ID())) {
                if (earliestEvent == null || entry.getValue().getYear() < earliestYear) {
                    earliestYear = entry.getValue().getYear();
                    earliestEvent = entry.getValue();
                }
            }
        }
        return earliestEvent;
    }

    public Person findPersonFromParentID(String fatherID) {
        for (Map.Entry<String, Person> entry : peopleMap.entrySet()) {
            if (entry.getValue().getPersonID_ID().equals(fatherID)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public String getEventSpecificsString(Event event) {
        String eventDes = event.getEventType() +": "+
                event.getCity() + ", " +
                event.getCountry() +"(" +
                event.getYear()+")";
        return eventDes;
    }

    public ArrayList<EventDisplay> getEventsInOrder(Person person) {
        DataCache DC = DataCache.getDataCache();
        ArrayList<Event> eventArrayList = new ArrayList<>();

        List<Event> eventsDisplay = DC.getEventsOfPersonByPersonId(person.getPersonID_ID());

        for(int i = 0; i < eventsDisplay.size();i++){
            if(person.getPersonID_ID().equals(eventsDisplay.get(i).getPersonID())){
                if(person.getGender().equals("m")){
                    if(DC.getUserSettings().isMaleEvents()){
                        eventArrayList.add(eventsDisplay.get(i));
                    }

                }
            }
            if(person.getGender().equals("f")){
                if(DC.getUserSettings().isFemaleEvents()){
                    eventArrayList.add(eventsDisplay.get(i));
                }

            }
        }


        Map<String, Event> events = null;
        if(DC.getUserSettings().isFatherSide()){
            if(DC.getUserSettings().isMotherSide()){
                events = DC.getEventMap();
            }
        }
        if(!DC.getUserSettings().isFatherSide()){
            if(DC.getUserSettings().isMotherSide()){
                events = DC.getSearchEventMap();
            }
        }
        if(DC.getUserSettings().isFatherSide()){
            if(!DC.getUserSettings().isMotherSide()){
                events = DC.getSearchEventMap();
            }
        }

        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < eventArrayList.size() - 1; j++) {
                if (eventArrayList.get(j).getYear() > eventArrayList.get(j + 1).getYear()) {
                    Collections.swap(eventArrayList, j, j + 1);
                }
            }
        }

        ArrayList<EventDisplay> eventRows = new ArrayList<>();

        for (int i = 0; i < eventArrayList.size(); i++) {

            EventDisplay temp = new EventDisplay(setDescriptionOfEvent(eventArrayList.get(i)) + "\n" + setDescriptionOfPerson(person),
                    "event", eventArrayList.get(i).getEventID());
            //for when i check userSettings

            if(events.containsValue(eventArrayList.get(i))){
                eventRows.add(temp);
            }
        }
        return eventRows;
    }
    public String setDescriptionOfEvent(Event eventSelectedOnMap){
        String eventDes = eventSelectedOnMap.getEventType().toLowerCase() +": "+
                eventSelectedOnMap.getCity().toLowerCase() + ", " +
                eventSelectedOnMap.getCountry().toLowerCase() +"(" +
                eventSelectedOnMap.getYear()+")";
        return eventDes;
    }
    public String setDescriptionOfPerson(Person person){
        String out = new String(person.getFirstname() + " " + person.getLastname());
        return out;
    }

    public ArrayList<EventDisplay> getPeopleInOrder(Person person) {
        Person spouse = DC.findSpouseFromPersonID(person);
        ArrayList<EventDisplay> returnList = new ArrayList<>();
        List<Person> children = new ArrayList<>();
        List<Person> parents;
        DataCache DC = DataCache.getDataCache();
        Map<String, List<Person>> mapChildren1 = DC.getChildrenMap();
        if (mapChildren1.containsKey(person.getPersonID_ID())) {
            children = mapChildren1.get(person.getPersonID_ID());
        }
        parents = DC.findParentsFromPersonID(person);
        //setting for parents
        if (parents.size() != 0) {
            for (int i = 0; i < parents.size(); i++) {
                if (parents.get(i).getGender().equals("f")) {
                    EventDisplay temp = new EventDisplay(setDescriptionOfPerson(parents.get(i)) + "\n" + "Mother", "female", parents.get(i).getPersonID_ID());
                    returnList.add(temp);
                } else {
                    EventDisplay temp = new EventDisplay(setDescriptionOfPerson(parents.get(i)) + "\n" + "Father", "male", parents.get(i).getPersonID_ID());
                    returnList.add(temp);
                }

            }
        }
        //setting for children
        if (children.size() != 0) {
            for (int i = 0; i < children.size(); i++) {
                if (children.get(i).getGender().equals("f")) {
                    EventDisplay temp = new EventDisplay(setDescriptionOfPerson(children.get(i)) + "\n" + "Daughter", "female", children.get(i).getPersonID_ID());
                    returnList.add(temp);
                } else {
                    EventDisplay temp = new EventDisplay(setDescriptionOfPerson(children.get(i)) + "\n" + "Son", "male", children.get(i).getPersonID_ID());
                    returnList.add(temp);
                }

            }
        }
        //setting spouse
        if (spouse != null) {
            //error checking
            if (spouse.getPersonID_ID() != null && !spouse.getPersonID_ID().equals("")) {
                if (spouse.getGender().equals("f")) {
                    EventDisplay temp = new EventDisplay(setDescriptionOfPerson(spouse) + "\n" + "Wife", "female", spouse.getPersonID_ID());
                    returnList.add(temp);
                } else {
                    EventDisplay temp = new EventDisplay(setDescriptionOfPerson(spouse) + "\n" + "Husband", "male", spouse.getPersonID_ID());
                    returnList.add(temp);
                }
            }
        }
        return returnList;
    }

    private List<Person> findParentsFromPersonID(Person person) {
        List<Person> p = new ArrayList<>();

        for (Map.Entry<String, List<Person>> e : childrenMap.entrySet()) {
            String possibleParent = e.getKey();
            List<Person> children = e.getValue();

            for (int i = 0; i < children.size(); i++) {
                if (person.getPersonID_ID().equals(children.get(i).getPersonID_ID())) {
                    p.add(peopleMap.get(possibleParent));
                }
            }
        }
        return p;
    }

    public Map<String, List<Person>> getChildrenMap() {
        return childrenMap;
    }

    public void search(String stringToSearch) {
        Map<String, Person> stringPersonMap;
        DataCache DC = DataCache.getDataCache();
        //clear the searchList
        searchList.clear();
        //add all the people the can come up from the search for


        stringPersonMap = DC.getPeopleMap();
        //stringPersonMap = DC.getSearchPeopleMap();

        for (Map.Entry<String,Person> entry:
                stringPersonMap.entrySet()) {
            if (entry.getValue().getLastname().toLowerCase().contains(stringToSearch.toLowerCase())) {
                EventDisplay obj = new EventDisplay(entry.getValue());
                searchList.add(obj);
            } else if (entry.getValue().getFirstname().toLowerCase().contains(stringToSearch.toLowerCase())) {
                EventDisplay obj = new EventDisplay(entry.getValue());
                searchList.add(obj);
            }
        }

        Map<String, Event> events;
        events = DC.getEventMap();
        //events = DC.getSearchEventMap();

        for (Map.Entry<String, Event> item : events.entrySet()) {
            String compareYear = Integer.toString(item.getValue().getYear());
            //DC.getUserSettings().isMaleEvents() &&
            if ( DC.getPersonFromID(item.getValue().getPersonID()).getGender().equals("m")) {
                if (compareYear.contains(stringToSearch)) {
                    EventDisplay obj = new EventDisplay(item.getValue());
                    searchList.add(obj);
                } else if (item.getValue().getEventType().toLowerCase().contains(stringToSearch.toLowerCase())) {
                    EventDisplay obj = new EventDisplay(item.getValue());
                    searchList.add(obj);
                } else if (item.getValue().getCountry().toLowerCase().contains(stringToSearch.toLowerCase())) {
                    EventDisplay obj = new EventDisplay(item.getValue());
                    searchList.add(obj);
                } else if (item.getValue().getCity().toLowerCase().contains(stringToSearch.toLowerCase())) {
                    EventDisplay obj = new EventDisplay(item.getValue());
                    searchList.add(obj);
                }
            }
            //DC.getUserSettings().isFemaleEvents() &&
            if ( DC.getPersonFromID(item.getValue().getPersonID()).getGender().equals("f")) {
                if (compareYear.contains(stringToSearch)) {
                    EventDisplay obj = new EventDisplay(item.getValue());
                    searchList.add(obj);
                } else if (item.getValue().getEventType().toLowerCase().contains(stringToSearch.toLowerCase())) {
                    EventDisplay obj = new EventDisplay(item.getValue());
                    searchList.add(obj);
                } else if (item.getValue().getCountry().toLowerCase().contains(stringToSearch.toLowerCase())) {
                    EventDisplay obj = new EventDisplay(item.getValue());
                    searchList.add(obj);
                } else if (item.getValue().getCity().toLowerCase().contains(stringToSearch.toLowerCase())) {
                    EventDisplay obj = new EventDisplay(item.getValue());
                    searchList.add(obj);
                }
            }
        }

    }

    public UserSettings getUserSettings() {
        return currentUserSettings;
    }
    public ArrayList<EventDisplay> getSearchList() {
        return searchList;
    }


    public Map<String, Person> getSearchPeopleMap() {
        Map<String, Person> temp = new HashMap<>();
        Map<String, Person> temp2 = new HashMap<>();
        List<String> tempID = new ArrayList<>();
        List<String> tempID2 = new ArrayList<>();

        if(DC.getUserSettings().isMotherSide()){
            for (Person t : motherSet) {
                tempID.add(t.getPersonID_ID());
            }
            for (Map.Entry<String, Person> entry : peopleMap.entrySet()) {
                if (tempID.contains(entry.getValue().getPersonID_ID())) {
                    temp.put(entry.getValue().getPersonID_ID(), entry.getValue());
                }
            }
            return temp;
        }

        if(DC.getUserSettings().isFatherSide()){
            for (Person t : fatherSet) {
                tempID2.add(t.getPersonID_ID());
            }
            for (Map.Entry<String, Person> entry : peopleMap.entrySet()) {
                if (tempID2.contains(entry.getValue().getPersonID_ID())) {
                    temp2.put(entry.getValue().getPersonID_ID(), entry.getValue());
                }
            }
            return temp2;
        }

        if(DC.getUserSettings().isFatherSide() && DC.getUserSettings().isMotherSide()){
            temp.putAll(temp2);
            return temp;
        }
        logger.log(Level.WARNING,"Fail in getSearchPeople; Returning Null");
        return null;

    }

    public Map<String, Event> getSearchEventMap() {
        Map<String, Event> temp = new HashMap<>();
        Map<String, Event> temp2 = new HashMap<>();
        List<String> tempID = new ArrayList<>();
        List<String> tempID2 = new ArrayList<>();
        if(DC.getUserSettings().isMotherSide()){
            for (Person t : motherSet) {
                tempID.add(t.getPersonID_ID());
            }
            for (Map.Entry<String, Event> entry : eventMap.entrySet()) {
                if (tempID.contains(entry.getValue().getPersonID())) {
                    temp.put(entry.getValue().getPersonID(), entry.getValue());
                }
            }
            return temp;
        }

        if(DC.getUserSettings().isFatherSide()){
            for (Person t : fatherSet) {
                tempID2.add(t.getPersonID_ID());
            }
            for (Map.Entry<String, Event> entry : eventMap.entrySet()) {
                if (tempID2.contains(entry.getValue().getPersonID())) {
                    temp2.put(entry.getValue().getPersonID(), entry.getValue());
                }
            }
            return temp2;
        }
        if(DC.getUserSettings().isFatherSide() && DC.getUserSettings().isMotherSide()){
            temp.putAll(temp2);
            return temp;
        }
        logger.log(Level.WARNING,"Fail in getSearchEvent; Returning Null");
        return null;
    }

    public void clearDC() {
        peopleMap.clear();
        eventMap.clear();
        personToEventsMap.clear();

        currentUser = null;
        eventsUser.clear();
        allEventTypes = null;
        childrenMap.clear();
        motherSet.clear();
        fatherSet.clear();
        searchList.clear();
    }
}
