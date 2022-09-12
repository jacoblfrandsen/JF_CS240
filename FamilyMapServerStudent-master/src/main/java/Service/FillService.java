package Service;

import Model.Event;
import Model.Person;
import Model.User;
import Results.FillResult;
import com.google.gson.JsonObject;
import dao.*;

import java.sql.Connection;
import java.util.ArrayList;

public class FillService {
    /**
     * Constructor
     */
    public FillService(User user){}

    public FillService() {}
    private static int eventTotal;
    public static int getEventTotal(){ return eventTotal; }
    public static void setEventTotal(int number){ eventTotal = number; }
    public static void incrementEventTotal(){ eventTotal++; }
    /**
     * fills the Database with the generated data
     */
    public FillResult Fill(String username, Integer numGenerations) throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        EncoderDecoder encoderDecoder = new EncoderDecoder();
        ArrayList<Person> parents = new ArrayList<>();
        FillResult fillResult;
        fillResult = new FillResult();

        UserDao userDao = new UserDao(conn);
        PersonDao personDao = new PersonDao(conn);
        EventDao eventDao = new EventDao(conn);
        int peopleNum = 0;
        User user;
        Person person;
        ArrayList<Person> currGen = new ArrayList<>();
        ArrayList<Person> nextGen = new ArrayList<>();

        try{
            user = userDao.getUserFromUsername(username);
            if(user == null){
                throw new DataAccessException();
            }
            eventDao.removeEventbyUsername(username);
            personDao.removePersonbyUsername(username);
            setEventTotal(0);
            String tempString = encoderDecoder.encodeUsers(user);

            person = encoderDecoder.decodePerson(tempString);
            person.setAssociated_username(username);
            createBirthdayDeathDay(person,null,conn);
            nextGen.add(person);

            for(int i = 1; i <= numGenerations; i++){
                currGen.addAll(nextGen);
                nextGen.clear();

                for(Person person1: currGen){
                    parents = createCouple(person1, username, conn);
                    person1.setFatherID(parents.get(1).getPersonID_ID());
                    person1.setMotherID(parents.get(0).getPersonID_ID());
                    personDao.insertPerson(person1);
                    peopleNum++;
                    if(i==numGenerations){
                        personDao.insertPerson(parents.get(0));
                        personDao.insertPerson(parents.get(1));
                        peopleNum++;
                        peopleNum++;
                    }
                    nextGen.addAll(parents);
                }
                currGen.clear();
            }
            fillResult.setMessage("Successfully added " + peopleNum + " persons and " + getEventTotal() +  " events to the database.");
            fillResult.setSuccess(true);
            db.closeConnection(true);
            return fillResult;
        } catch (DataAccessException e) {
            e.printStackTrace();
            fillResult.setMessage("error");
            fillResult.setSuccess(false);
            db.closeConnection(true);
            return fillResult;
        }

    }

    private ArrayList<Person> createCouple(Person person, String username, Connection conn) throws DataAccessException {
        ServiceGeneral SG = new ServiceGeneral();
        ArrayList<Person> parents = new ArrayList<>();
        final String FEMALE_NAME = "/Users/jacobfrandsen/Desktop/Code/CS_240/FamilyMapServerStudent-master/json/fnames.json";
        final String MALE_NAME = "/Users/jacobfrandsen/Desktop/Code/CS_240/FamilyMapServerStudent-master/json/mnames.json";
        final String LAST_NAME = "/Users/jacobfrandsen/Desktop/Code/CS_240/FamilyMapServerStudent-master/json/snames.json";
        Person father = new Person( SG.getRandIDNum(),
                username,
                SG.nameFromFile(MALE_NAME),
                person.getLastname(),
                "m",
                null,
                null,
                null);
        Person mother = new Person(SG.getRandIDNum(),
                username,
                SG.nameFromFile(FEMALE_NAME),
                SG.nameFromFile(LAST_NAME),
                "f",
                null,
                null,
                null);
        createBirthdayDeathDay(mother,person,conn);
        createBirthdayDeathDay(father,person,conn);

        father.setSpouseID(mother.getPersonID_ID());
        mother.setSpouseID(father.getPersonID_ID());

        createMarriage(mother,father,conn);

        parents.add(mother);
        parents.add(father);

        return parents;
    }


    private void createBirthdayDeathDay(Person person, Person child, Connection conn) throws DataAccessException {
        final String LOCATION = "/Users/jacobfrandsen/Desktop/Code/CS_240/FamilyMapServerStudent-master/json/locations.json";
        EncoderDecoder encoderDecoder = new EncoderDecoder();
        EventDao eventDao = new EventDao(conn);
        ServiceGeneral SG = new ServiceGeneral();

        Event birth = encoderDecoder.decodeEvents(SG.getLocationFromFile(LOCATION)); //read location file(IDK if working)

        if(birth == null){
            System.out.println("getLocationfromfile LOCATION FAILED");
            throw new DataAccessException();
        }
        birth.setEventID(SG.getRandIDNum());
        birth.setUsername(person.getAssociated_username());
        birth.setEventType("birth");
        birth.setPersonID(person.getPersonID_ID());

        if (child != null) {
            ArrayList<Event> eventArrayList = new ArrayList<>();
            eventArrayList = eventDao.getEventsFromPersonID(child.getPersonID_ID());

            //if the event type is birth we need to calculate a random birth year for them
            for(Event event: eventArrayList){
                if(event.getEventType().equals("birth") ){
                    birth.setYear((event).getYear() - 13); //- (int)(Math.random()*17));
                    break;
                }
            }
        }
        else{
            birth.setYear(1995);
        }
        eventDao.insert(birth);
        incrementEventTotal();
        //Time to do Death stufffffff

        if(birth.getYear() <1990){
            Event death = encoderDecoder.decodeEvents(SG.getLocationFromFile(LOCATION));
            death.setEventID(SG.getRandIDNum());
            death.setUsername(person.getAssociated_username());
            death.setPersonID(person.getPersonID_ID());
            death.setEventType("death");
            death.setYear(birth.getYear() + 30 + (int)(Math.random()*55));
            eventDao.insert(death);
            incrementEventTotal();
        }

    }
    private void createMarriage(Person mother, Person father, Connection conn) throws DataAccessException {
        final String LOCATION = "/Users/jacobfrandsen/Desktop/Code/CS_240/FamilyMapServerStudent-master/json/locations.json";
        EncoderDecoder encoderDecoder = new EncoderDecoder();
        EventDao eventDao = new EventDao(conn);
        ServiceGeneral SG = new ServiceGeneral();
        ArrayList<Event> eventListFather = new ArrayList<>();
        ArrayList<Event> eventListMother = new ArrayList<>();
        int youngAge = 2020;

        eventListFather = eventDao.getEventsFromPersonID(father.getPersonID_ID());
        for (Event event:
             eventListFather) {
            if(event.getEventType().equals("birth")){
                youngAge = (event).getYear()+13;
                break;
            }
        }
        eventListMother = eventDao.getEventsFromPersonID(mother.getPersonID_ID());
        for (Event event:
             eventListMother) {
            if(event.getEventType().equals("birth")&&((event).getYear())+13> youngAge ){
                youngAge = (event).getYear()+13;
                break;
            }
        }
        youngAge+=1;
        JsonObject temp = SG.getLocationFromFile(LOCATION);
        Event fatherMarriage = encoderDecoder.decodeEvents(temp);
        fatherMarriage.setEventID(SG.getRandIDNum());
        fatherMarriage.setPersonID(father.getPersonID_ID());
        fatherMarriage.setUsername(father.getAssociated_username());
        fatherMarriage.setEventType("marriage");
        fatherMarriage.setYear(youngAge);

        Event motherMarriage = encoderDecoder.decodeEvents(temp);
        motherMarriage.setEventID(SG.getRandIDNum());
        motherMarriage.setPersonID(mother.getPersonID_ID());
        motherMarriage.setUsername(mother.getAssociated_username());
        motherMarriage.setEventType("marriage");
        motherMarriage.setYear(youngAge);
        if(motherMarriage == null){
            System.out.println("here");
        }
        EventDao eventAccess = new EventDao(conn);
        eventAccess.insert(fatherMarriage);
        eventAccess.insert(motherMarriage);

        incrementEventTotal();
        incrementEventTotal();
    }


}
