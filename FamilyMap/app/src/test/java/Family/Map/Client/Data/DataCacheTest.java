package Family.Map.Client.Data;


import static org.junit.Assert.*;

import Model.Person;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Model.Event;


public class DataCacheTest {

    private Map<String, Event> eventMap;
    private Map<String, Person> peopleMap;
    private Map<String, List<Event>> peopleEventMap;
    private Person user;
    private Set<Person> paternalAncestors;
    private Set<Person> maternalAncestors;
    private List<String> eventTypesForUser;
    private List<String> eventTypesForFemaleAncestors;
    private List<String> eventTypesForMaleAncestors;
    private Map<String, List<Person>> childrenMap;
    private DataCache DC = DataCache.getDataCache();
    @Before
    public void setUp(){
        eventMap = DC.getEventMap();
        peopleMap = DC.getPeopleMap();
        peopleEventMap = DC.getPersonToEventsMap();
        user = DC.getCurrentUser();
        eventTypesForUser = DC.getEventsUser();
        eventTypesForFemaleAncestors = DC.getEventsMotherAncestors();
        eventTypesForMaleAncestors = DC.getEventsFatherAncestors();
        childrenMap = DC.getChildrenMap();
        DC.clearDC();
    }
    @Test
    public void testSetPeople(){
        Person person1 = null;
        Person person2 = null;
        Person person3 = null;
        Person person4 = null;
        Person person5 = null;
        Person person6 = null;
        Person person7 = null;

        person1.setAssociated_username("username1");
        person2.setAssociated_username("username2");
        person3.setAssociated_username("username3");
        person4.setAssociated_username("username4");
        person5.setAssociated_username("username5");
        person6.setAssociated_username("username6");
        person7.setAssociated_username("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstname("firstName");
        person1.setLastname("lastName");
        person1.setPersonID_ID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstname("firstName father");
        person2.setLastname("lastName");
        person2.setPersonID_ID("personID2 father");
        person2.setFatherID("personID2 paternal grandpa");
        person2.setMotherID("personID2 paternal grandma");

        person3.setFirstname("firstName mother");
        person3.setLastname("lastName");
        person3.setPersonID_ID("personID3 mother");
        person3.setFatherID("personID3 maternal grandpa");
        person3.setMotherID("personID3 maternal grandma");

        person4.setFirstname("firstName paternal grandpa");
        person4.setLastname("lastName");
        person4.setPersonID_ID("personID4 paternal grandpa");

        person5.setFirstname("firstName paternal grandma");
        person5.setLastname("lastName");
        person5.setPersonID_ID("personID5 paternal grandma");

        person6.setFirstname("firstName maternal grandpa");
        person6.setLastname("lastName");
        person6.setPersonID_ID("personID6 maternal grandpa");

        person7.setFirstname("firstName maternal grandma");
        person7.setLastname("lastName");
        person7.setPersonID_ID("personID7 maternal grandma");

        ArrayList<Person> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);


        Map<String, Person> temp = DC.getPeopleMap();

        for(Map.Entry<String, Person> entry : peopleMap.entrySet()){
            assertTrue(temp.containsValue( entry.getValue()));
        }
    }

    @Test
    public void testFindParentsByID(){
        Person person1 = null;
        Person person2 = null;
        Person person3 = null;
        Person person4 = null;
        Person person5 = null;
        Person person6 = null;
        Person person7 = null;

        person1.setAssociated_username("username1");
        person2.setAssociated_username("username2");
        person3.setAssociated_username("username3");
        person4.setAssociated_username("username4");
        person5.setAssociated_username("username5");
        person6.setAssociated_username("username6");
        person7.setAssociated_username("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstname("firstName");
        person1.setLastname("lastName");
        person1.setPersonID_ID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstname("firstName father");
        person2.setLastname("lastName");
        person2.setPersonID_ID("personID2 father");
        person2.setFatherID("personID2 paternal grandpa");
        person2.setMotherID("personID2 paternal grandma");

        person3.setFirstname("firstName mother");
        person3.setLastname("lastName");
        person3.setPersonID_ID("personID3 mother");
        person3.setFatherID("personID3 maternal grandpa");
        person3.setMotherID("personID3 maternal grandma");

        person4.setFirstname("firstName paternal grandpa");
        person4.setLastname("lastName");
        person4.setPersonID_ID("personID4 paternal grandpa");

        person5.setFirstname("firstName paternal grandma");
        person5.setLastname("lastName");
        person5.setPersonID_ID("personID5 paternal grandma");

        person6.setFirstname("firstName maternal grandpa");
        person6.setLastname("lastName");
        person6.setPersonID_ID("personID6 maternal grandpa");

        person7.setFirstname("firstName maternal grandma");
        person7.setLastname("lastName");
        person7.setPersonID_ID("personID7 maternal grandma");

        ArrayList<Person> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);



        DC.settingPeopleMap(family);

        List<Person> expectedParents = new ArrayList<>();
        expectedParents.add(person2);
        expectedParents.add(person3);

        Person output = DC.findSpouseFromPersonID(person1);
        assertTrue(expectedParents.contains(output));


    }


    @Test
    public void testFindSpouseByPersonID(){
        Person person1 = null;
        Person person2 = null;
        Person person3 = null;
        Person person4 = null;
        Person person5 = null;
        Person person6 = null;
        Person person7 = null;

        person1.setAssociated_username("username1");
        person2.setAssociated_username("username2");
        person3.setAssociated_username("username3");
        person4.setAssociated_username("username4");
        person5.setAssociated_username("username5");
        person6.setAssociated_username("username6");
        person7.setAssociated_username("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstname("firstName");
        person1.setLastname("lastName");
        person1.setPersonID_ID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstname("firstName father");
        person2.setLastname("lastName");
        person2.setPersonID_ID("personID2 father");
        person2.setFatherID("personID2 paternal grandpa");
        person2.setMotherID("personID2 paternal grandma");

        person3.setFirstname("firstName mother");
        person3.setLastname("lastName");
        person3.setPersonID_ID("personID3 mother");
        person3.setFatherID("personID3 maternal grandpa");
        person3.setMotherID("personID3 maternal grandma");

        person4.setFirstname("firstName paternal grandpa");
        person4.setLastname("lastName");
        person4.setPersonID_ID("personID4 paternal grandpa");

        person5.setFirstname("firstName paternal grandma");
        person5.setLastname("lastName");
        person5.setPersonID_ID("personID5 paternal grandma");

        person6.setFirstname("firstName maternal grandpa");
        person6.setLastname("lastName");
        person6.setPersonID_ID("personID6 maternal grandpa");

        person7.setFirstname("firstName maternal grandma");
        person7.setLastname("lastName");
        person7.setPersonID_ID("personID7 maternal grandma");

        ArrayList<Person> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);


        DC.settingPeopleMap(family);

        Person output = DC.findSpouseFromPersonID(person3);

        assertEquals(person2, output);

        output = DC.findSpouseFromPersonID(person6);

        assertEquals(person7, output);

    }

    @Test
    public void testCreatePaternalAndMaternalSets(){
        Person person1 = null;
        Person person2 = null;
        Person person3 = null;
        Person person4 = null;
        Person person5 = null;
        Person person6 = null;
        Person person7 = null;

        person1.setAssociated_username("username1");
        person2.setAssociated_username("username2");
        person3.setAssociated_username("username3");
        person4.setAssociated_username("username4");
        person5.setAssociated_username("username5");
        person6.setAssociated_username("username6");
        person7.setAssociated_username("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstname("firstName");
        person1.setLastname("lastName");
        person1.setPersonID_ID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstname("firstName father");
        person2.setLastname("lastName");
        person2.setPersonID_ID("personID2 father");
        person2.setFatherID("personID2 paternal grandpa");
        person2.setMotherID("personID2 paternal grandma");

        person3.setFirstname("firstName mother");
        person3.setLastname("lastName");
        person3.setPersonID_ID("personID3 mother");
        person3.setFatherID("personID3 maternal grandpa");
        person3.setMotherID("personID3 maternal grandma");

        person4.setFirstname("firstName paternal grandpa");
        person4.setLastname("lastName");
        person4.setPersonID_ID("personID4 paternal grandpa");

        person5.setFirstname("firstName paternal grandma");
        person5.setLastname("lastName");
        person5.setPersonID_ID("personID5 paternal grandma");

        person6.setFirstname("firstName maternal grandpa");
        person6.setLastname("lastName");
        person6.setPersonID_ID("personID6 maternal grandpa");

        person7.setFirstname("firstName maternal grandma");
        person7.setLastname("lastName");
        person7.setPersonID_ID("personID7 maternal grandma");

        ArrayList<Person> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);;

        ArrayList< Person> expectedParentalAncestors = new ArrayList<>();
        expectedParentalAncestors.add(person4);
        expectedParentalAncestors.add(person2);
        expectedParentalAncestors.add(person5);

        ArrayList<Person> expectedMaternalAncestors = new ArrayList<>();
        expectedMaternalAncestors.add(person7);
        expectedMaternalAncestors.add(person6);
        expectedMaternalAncestors.add(person3);


        assertEquals((DC.getEventsMotherAncestors()).toString(), expectedParentalAncestors.toString());
        assertEquals((DC.getEventsFatherAncestors()).toString(), expectedMaternalAncestors.toString());
    }

    @Test
    public void testSetEvents(){
        Person person1 = null;
        Person person2 = null;
        Person person3 = null;
        Person person4 = null;
        Person person5 = null;
        Person person6 = null;
        Person person7 = null;

        person1.setAssociated_username("username1");
        person2.setAssociated_username("username2");
        person3.setAssociated_username("username3");
        person4.setAssociated_username("username4");
        person5.setAssociated_username("username5");
        person6.setAssociated_username("username6");
        person7.setAssociated_username("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstname("firstName");
        person1.setLastname("lastName");
        person1.setPersonID_ID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstname("firstName father");
        person2.setLastname("lastName");
        person2.setPersonID_ID("personID2 father");
        person2.setFatherID("personID2 paternal grandpa");
        person2.setMotherID("personID2 paternal grandma");

        person3.setFirstname("firstName mother");
        person3.setLastname("lastName");
        person3.setPersonID_ID("personID3 mother");
        person3.setFatherID("personID3 maternal grandpa");
        person3.setMotherID("personID3 maternal grandma");

        person4.setFirstname("firstName paternal grandpa");
        person4.setLastname("lastName");
        person4.setPersonID_ID("personID4 paternal grandpa");

        person5.setFirstname("firstName paternal grandma");
        person5.setLastname("lastName");
        person5.setPersonID_ID("personID5 paternal grandma");

        person6.setFirstname("firstName maternal grandpa");
        person6.setLastname("lastName");
        person6.setPersonID_ID("personID6 maternal grandpa");

        person7.setFirstname("firstName maternal grandma");
        person7.setLastname("lastName");
        person7.setPersonID_ID("personID7 maternal grandma");

        ArrayList<Person> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);


        DC.settingPeopleMap(family);


        Event event1 = new Event(
                "eventID1",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event2 = new Event(
                "eventID2",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event3 = new Event(
                "eventID3",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event4 = new Event(
                "eventID4",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event5 = new Event(
                "eventID5",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event6 = new Event(
                "eventID6",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);

        ArrayList<Event> familyEvents = new ArrayList<>();

        familyEvents.add(event1);
        familyEvents.add(event2);
        familyEvents.add(event3);
        familyEvents.add(event4);
        familyEvents.add(event5);
        familyEvents.add(event6);


        DC.settingEventMap(familyEvents);

        Map<String, Event> events = DC.getEventMap();

        for (int i = 0; i < familyEvents.size(); i++){
            assertEquals(familyEvents.get(i), events.get(familyEvents.get(i).getEventID()));
        }
    }

    @Test
    public void testCreateEventsLists(){
        Person person1 = null;
        Person person2 = null;
        Person person3 = null;
        Person person4 = null;
        Person person5 = null;
        Person person6 = null;
        Person person7 = null;

        person1.setAssociated_username("username1");
        person2.setAssociated_username("username2");
        person3.setAssociated_username("username3");
        person4.setAssociated_username("username4");
        person5.setAssociated_username("username5");
        person6.setAssociated_username("username6");
        person7.setAssociated_username("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstname("firstName");
        person1.setLastname("lastName");
        person1.setPersonID_ID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstname("firstName father");
        person2.setLastname("lastName");
        person2.setPersonID_ID("personID2 father");
        person2.setFatherID("personID2 paternal grandpa");
        person2.setMotherID("personID2 paternal grandma");

        person3.setFirstname("firstName mother");
        person3.setLastname("lastName");
        person3.setPersonID_ID("personID3 mother");
        person3.setFatherID("personID3 maternal grandpa");
        person3.setMotherID("personID3 maternal grandma");

        person4.setFirstname("firstName paternal grandpa");
        person4.setLastname("lastName");
        person4.setPersonID_ID("personID4 paternal grandpa");

        person5.setFirstname("firstName paternal grandma");
        person5.setLastname("lastName");
        person5.setPersonID_ID("personID5 paternal grandma");

        person6.setFirstname("firstName maternal grandpa");
        person6.setLastname("lastName");
        person6.setPersonID_ID("personID6 maternal grandpa");

        person7.setFirstname("firstName maternal grandma");
        person7.setLastname("lastName");
        person7.setPersonID_ID("personID7 maternal grandma");

        ArrayList<Person> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);


        DC.settingPeopleMap(family);


        Event event1 = new Event(
                "eventID1",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event2 = new Event(
                "eventID2",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event3 = new Event(
                "eventID3",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event4 = new Event(
                "eventID4",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event5 = new Event(
                "eventID5",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event6 = new Event(
                "eventID6",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);

        ArrayList<Event> familyEvents = new ArrayList<>();

        familyEvents.add(event1);
        familyEvents.add(event2);
        familyEvents.add(event3);
        familyEvents.add(event4);
        familyEvents.add(event5);
        familyEvents.add(event6);


        DC.settingEventMap(familyEvents);

        List<Event> expectedEventsOfUser = new ArrayList<>();
        expectedEventsOfUser.add(event1);
        expectedEventsOfUser.add(event2);
        expectedEventsOfUser.add(event3);

        assertEquals(expectedEventsOfUser,  DC.getEventsOfPersonByPersonId("personID1"));

    }


    @Test
    public void testFindParentsByIDShouldFail(){
        Person person1 = null;
        Person person2 = null;
        Person person3 = null;
        Person person4 = null;
        Person person5 = null;
        Person person6 = null;
        Person person7 = null;

        person1.setAssociated_username("username1");
        person2.setAssociated_username("username2");
        person3.setAssociated_username("username3");
        person4.setAssociated_username("username4");
        person5.setAssociated_username("username5");
        person6.setAssociated_username("username6");
        person7.setAssociated_username("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstname("firstName");
        person1.setLastname("lastName");
        person1.setPersonID_ID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstname("firstName father");
        person2.setLastname("lastName");
        person2.setPersonID_ID("personID2 father");
        person2.setFatherID("personID2 paternal grandpa");
        person2.setMotherID("personID2 paternal grandma");

        person3.setFirstname("firstName mother");
        person3.setLastname("lastName");
        person3.setPersonID_ID("personID3 mother");
        person3.setFatherID("personID3 maternal grandpa");
        person3.setMotherID("personID3 maternal grandma");

        person4.setFirstname("firstName paternal grandpa");
        person4.setLastname("lastName");
        person4.setPersonID_ID("personID4 paternal grandpa");

        person5.setFirstname("firstName paternal grandma");
        person5.setLastname("lastName");
        person5.setPersonID_ID("personID5 paternal grandma");

        person6.setFirstname("firstName maternal grandpa");
        person6.setLastname("lastName");
        person6.setPersonID_ID("personID6 maternal grandpa");

        person7.setFirstname("firstName maternal grandma");
        person7.setLastname("lastName");
        person7.setPersonID_ID("personID7 maternal grandma");

        ArrayList<Person> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);


        DC.settingPeopleMap(family);


        Event event1 = new Event(
                "eventID1",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event2 = new Event(
                "eventID2",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event3 = new Event(
                "eventID3",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event4 = new Event(
                "eventID4",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event5 = new Event(
                "eventID5",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);
        Event event6 = new Event(
                "eventID6",
                "username1",
                "personID1",
                1,
                2,
                "USA",
                "oronoco",
                "ev",
                2000);

        ArrayList<Event> familyEvents = new ArrayList<>();


        familyEvents.add(event1);
        familyEvents.add(event2);
        familyEvents.add(event3);
        familyEvents.add(event4);
        familyEvents.add(event5);
        familyEvents.add(event6);


        DC.settingEventMap(familyEvents);

        List<Person> expectedParents = new ArrayList<>();
        expectedParents.add(person4);
        expectedParents.add(person5);

        Person output = DC.findPersonFromParentID(person1.getPersonID_ID());
        assertFalse(expectedParents.contains(output));
        assertFalse(expectedParents.contains(output));
    }


    @Test
    public void testFindSpouseByPersonIDShouldFail(){
        Person person1 = null;
        Person person2 = null;
        Person person3 = null;
        Person person4 = null;
        Person person5 = null;
        Person person6 = null;
        Person person7 = null;

        person1.setAssociated_username("username1");
        person2.setAssociated_username("username2");
        person3.setAssociated_username("username3");
        person4.setAssociated_username("username4");
        person5.setAssociated_username("username5");
        person6.setAssociated_username("username6");
        person7.setAssociated_username("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstname("firstName");
        person1.setLastname("lastName");
        person1.setPersonID_ID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstname("firstName father");
        person2.setLastname("lastName");
        person2.setPersonID_ID("personID2 father");
        person2.setFatherID("personID2 paternal grandpa");
        person2.setMotherID("personID2 paternal grandma");

        person3.setFirstname("firstName mother");
        person3.setLastname("lastName");
        person3.setPersonID_ID("personID3 mother");
        person3.setFatherID("personID3 maternal grandpa");
        person3.setMotherID("personID3 maternal grandma");

        person4.setFirstname("firstName paternal grandpa");
        person4.setLastname("lastName");
        person4.setPersonID_ID("personID4 paternal grandpa");

        person5.setFirstname("firstName paternal grandma");
        person5.setLastname("lastName");
        person5.setPersonID_ID("personID5 paternal grandma");

        person6.setFirstname("firstName maternal grandpa");
        person6.setLastname("lastName");
        person6.setPersonID_ID("personID6 maternal grandpa");

        person7.setFirstname("firstName maternal grandma");
        person7.setLastname("lastName");
        person7.setPersonID_ID("personID7 maternal grandma");

        ArrayList<Person> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);


        DC.clearDC();


        Person output = DC.findSpouseFromPersonID(person3);
        assertNotEquals(person6,output);
        assertNotEquals(person6, output);

    }

}
