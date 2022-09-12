package Family.Map.Client.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import Family.Map.Client.Data.DataCache;
import Family.Map.Client.Data.LinkedEvents;
import Family.Map.R;
import Model.Event;
import Model.Person;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private Context context;

    private String centerEvent;
    private boolean cameFromPerson;
    private Event eventSelectedOnMap;
    private boolean clickBool;
    private View view;
    private GoogleMap gMap;
    private int counter = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View.OnClickListener eventScreenListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickBool == true){
                    Intent i = new Intent(getActivity(), PersonActivity.class);
                    i.putExtra("personID", eventSelectedOnMap.getPersonID());
                    startActivity(i);
                }
            }
        };
        view =  inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        LinearLayout eventScreen = view.findViewById(R.id.bottomOfScreen);
        eventScreen.setOnClickListener(eventScreenListener);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        if (cameFromPerson == true) {
            setHasOptionsMenu(false);
        } else {
            setHasOptionsMenu(true);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.search:
                Intent intentThree = new Intent(getActivity(), SearchActivity.class);
                startActivity(intentThree);
                return true;

            case R.id.settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.activity_main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        DataCache DC = DataCache.getDataCache();
        Map<String, Event> tempEventMap = DC.getEventMap();



        createMarkers(gMap);


        //moving the camera to the centered event
        Event currentCenterEvent = tempEventMap.get(centerEvent);
        LatLng whereToCenter = new LatLng(currentCenterEvent.getLatitude(),currentCenterEvent.getLongitude());
        gMap.moveCamera(CameraUpdateFactory.newLatLng(whereToCenter));

        if(cameFromPerson == true){
            Event selectedEvent = DC.getEventFromID(centerEvent);
            eventSelectedOnMap = selectedEvent;
            clickMarker(view);
            //marker was clicked
        }

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                DataCache DC = DataCache.getDataCache();
                Event tempEvent = (Event) marker.getTag();
                eventSelectedOnMap = tempEvent;
                clickMarker(view);
                return false;
            }
        });

    }
    private void createMarkers(GoogleMap gMap) {
        //create list of colors
        List<String> listColors = Arrays.asList(
                "#F42335",
                "#2136F4",
                "#4CAF51",
                "#CDEC38",
                "#675AB6",
                "#BA8300",
                "#289261",
                "#FF33FF",
                "#62DC51",
                "#684204",
                "#e5ede1",
                "#090e0a");
        DataCache DC = DataCache.getDataCache();
        //check usersettings
        Map<String, Event> eventMap = new HashMap<>();

        if(DC.getUserSettings().isFatherSide()) {
            if (DC.getUserSettings().isMotherSide()) {
                eventMap = DC.getEventMap();
            }
        }
        if(!DC.getUserSettings().isFatherSide()) {
            if (DC.getUserSettings().isMotherSide()) {
                eventMap = DC.getSearchEventMap();
            }
        }
        if(DC.getUserSettings().isFatherSide()) {
            if (!DC.getUserSettings().isMotherSide()) {
                eventMap = DC.getSearchEventMap();
            }
        }



        Set<String> eventMasterSet = new HashSet<>();

        //GETTING EVENT TYPES
        eventMasterSet.addAll(DC.getEventsUser()); //get all the event types for the user
        eventMasterSet.addAll(DC.getEventsFatherAncestors());
        eventMasterSet.addAll(DC.getEventsMotherAncestors());

        List<String> finalEventTypes;
        finalEventTypes = new ArrayList<>(eventMasterSet);

        DC.setAllEventTypes(finalEventTypes);

        for (Map.Entry<String, Event> item :
        eventMap.entrySet()){
            Event tempEvent = item.getValue();

            String tempString = DC.getPersonFromID(tempEvent.getPersonID()).getGender();
            //debugging null gender
//            if(tempString == null){
//                System.out.println("test");
//                String ID = DC.getPersonFromID(tempEvent.getPersonID()).toString();
//            }

            if(tempString.equals("f") && DC.getUserSettings().isFemaleEvents()){

                int tempInt = settingColorToUse(finalEventTypes, tempEvent);

                String colorValue = listColors.get(tempInt);
                LatLng eventLatLng = new LatLng(tempEvent.getLatitude(), tempEvent.getLongitude());
                gMap.addMarker( new MarkerOptions().position(eventLatLng).icon(getMarkerIcon(colorValue))).setTag(tempEvent);
            }

            if(DC.getPersonFromID(tempEvent.getPersonID()).getGender().equals("m") && DC.getUserSettings().isMaleEvents()){
                int colorToUse = settingColorToUse(finalEventTypes, tempEvent);

                String colorValue = listColors.get(colorToUse);
                LatLng eventLatLng = new LatLng(tempEvent.getLatitude(), tempEvent.getLongitude());
                gMap.addMarker( new MarkerOptions().position(eventLatLng).icon(getMarkerIcon(colorValue))).setTag(tempEvent);
            }
            if(counter >3){
                counter = 0;
            }
        }
    }

    private int settingColorToUse(List<String> finalEventTypes,Event tempEvent) {
        int colorToUse = finalEventTypes.indexOf(tempEvent.getEventType().toLowerCase());
        Set<String> setOfEventTypes = new HashSet<>();

//        if(tempEvent.getEventType().equals("Ate Brazilian Barbecue")){
//            colorToUse = 11;
//        }

        if(colorToUse <0){
            colorToUse = 11;
//            counter++;
        }
        return colorToUse;
    }

    private void clickMarker(View view) {
        DataCache DC = DataCache.getDataCache();
        Person selectedPerson = DC.getPersonFromID(eventSelectedOnMap.getPersonID());
        ImageView IV = (ImageView) view.findViewById(R.id.iconImageView);

        //TODO Set gender when clicking the image
        if(selectedPerson.getGender().equals("f")){
//            Drawable fIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.fe_color).sizeDp(40);
            IV.setImageResource(R.drawable.female);
        }
        if(selectedPerson.getGender().equals("m")){
//            Drawable mIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.me_color).sizeDp(40);
            IV.setImageResource(R.drawable.male);
        }

        TextView TV = (TextView) view.findViewById(R.id.text_description_map);
        String tempString = selectedPerson.getFirstname().toLowerCase() + " " + selectedPerson.getLastname().toLowerCase();
        String eventDes = eventSelectedOnMap.getEventType().toLowerCase() +": "+
                eventSelectedOnMap.getCity().toLowerCase() + ", " +
                eventSelectedOnMap.getCountry().toLowerCase() +"(" +
                eventSelectedOnMap.getYear()+")";

        TV.setText(tempString + "\n" + eventDes);
        clickBool = true;

        settingLines(DC.getEventFromID(eventSelectedOnMap.getEventID()),gMap);
    }

    private void settingLines(Event eventFromID, GoogleMap gMap) {
        gMap.clear();
        createMarkers(gMap);
        DataCache DC = DataCache.getDataCache();

        if(DC.getUserSettings().isSpouseLines()){
            settingSpouseLines(eventFromID, gMap, DC);
        }
        if(DC.getUserSettings().isTreeLines()){
            settingTreeLines(eventFromID,gMap,DC);
        }
        if(DC.getUserSettings().isLifeLines()){
            settingLifeLines(eventFromID,gMap,DC);
        }


    }


    private void settingSpouseLines(Event eventFromID, GoogleMap gMap,DataCache dc) {
        Integer tempSpouseLinesColor = Color.RED;
        Person personSpouse = dc.findSpouseFromPersonID(dc.getPersonFromEvent(eventFromID));
        //check spouse found
        if(personSpouse!= null){
            Event event = dc.findFirstEvent(personSpouse);
            if(event != null){
                if(dc.getPersonFromID(personSpouse.getPersonID_ID()).getGender().equals("m")){
                    if(dc.getUserSettings().isMaleEvents()){
                        settingSingleLine(new LinkedEvents(eventFromID,event), tempSpouseLinesColor, gMap, 12);
                    }
                }
                if(dc.getPersonFromID(personSpouse.getPersonID_ID()).getGender().equals("f")){
                    if(dc.getUserSettings().isFemaleEvents()){
                        settingSingleLine(new LinkedEvents(eventFromID,event), tempSpouseLinesColor, gMap, 12);
                    }

                }
            }
        }
    }


    private void settingLifeLines(Event eventFromID, GoogleMap gMap, DataCache dc) {
        Integer tempColor = Color.GREEN;
        ArrayList<Event> listEvents = dc.getOrderLifeEvents(dc.getPersonFromEvent(eventFromID), dc);

        for (int i = 0; i < listEvents.size() -1; i++) {
            settingSingleLine(new LinkedEvents(listEvents.get(i),listEvents.get(i+1)), tempColor,gMap, 9);
        }
    }


    public void settingSingleLine(LinkedEvents linkedEvents, Integer color, GoogleMap googleMap, int thickness){
        PolylineOptions lines = new PolylineOptions().add(
                new LatLng(linkedEvents.getFirst().getLatitude(), linkedEvents.getFirst().getLongitude()),
                new LatLng(linkedEvents.getSecond().getLatitude(), linkedEvents.getSecond().getLongitude())
        ).color(color).width(thickness);
        googleMap.addPolyline(lines);
    }


    private void settingTreeLines(Event eventFromID, GoogleMap gMap, DataCache dc) {
        int generation = 1;
        if(dc.getPersonFromID(eventFromID.getPersonID()).getGender().equals("m")){
            if(dc.getUserSettings().isMaleEvents()){
                settingTreeLinesForPerson(dc.getPersonFromEvent(eventFromID), eventFromID, gMap,dc, generation);
            }

        }
        if(dc.getPersonFromID(eventFromID.getPersonID()).getGender().equals("f")){
            if(dc.getUserSettings().isFemaleEvents()){
                settingTreeLinesForPerson(dc.getPersonFromEvent(eventFromID), eventFromID, gMap,dc, generation);
            }

        }
    }

    private void settingTreeLinesForPerson(Person personFromEvent, Event eventFromID, GoogleMap gMap, DataCache dc, int generation) {
        DataCache DC = DataCache.getDataCache();

        if(DC.getPersonFromID(eventFromID.getPersonID()).getGender().equals("m")){
            if(DC.getUserSettings().isMaleEvents()){
                settingTreeLinesForFather(personFromEvent,eventFromID,gMap, dc, generation);
            }
        }
        if(DC.getPersonFromID(eventFromID.getPersonID()).getGender().equals("f")){
            if(DC.getUserSettings().isFemaleEvents()){
                settingTreeLinesForMother(personFromEvent,eventFromID,gMap, dc, generation);
            }
        }
    }

    private void settingTreeLinesForFather(Person personFromEvent, Event eventFromID, GoogleMap gMap, DataCache dc, int generation) {
        int fatherGen;
        Integer tempColorForTreeLines = Color.BLUE;


        if(personFromEvent.getFatherID() != null){
            Event fatherEvent = dc.findFirstEvent(dc.findPersonFromParentID(personFromEvent.getFatherID()));
            if(fatherEvent!= null){
                if(dc.getUserSettings().isMaleEvents()){
                    settingSingleLine(new LinkedEvents(eventFromID,fatherEvent), tempColorForTreeLines, gMap, 22/ generation);
                    fatherGen = generation+1;
                    settingTreeLinesForPerson(dc.findPersonFromParentID(personFromEvent.getFatherID()),fatherEvent, gMap, dc, fatherGen);
                }
            }
        }

    }

    private void settingTreeLinesForMother(Person personFromEvent, Event eventFromID, GoogleMap gMap, DataCache dc, int generation) {
        int motherGen;
        Integer tempColorForTreeLines = Color.BLUE;


        if(personFromEvent.getMotherID()!= null){
            Event motherEvent = dc.findFirstEvent(dc.findPersonFromParentID(personFromEvent.getMotherID()));
            if(motherEvent!= null){
                if(dc.getUserSettings().isMaleEvents()){
                    settingSingleLine(new LinkedEvents(eventFromID,motherEvent), tempColorForTreeLines, gMap, 22/ generation);
                    motherGen = generation+1;
                    settingTreeLinesForPerson(dc.findPersonFromParentID(personFromEvent.getFatherID()),motherEvent, gMap, dc, motherGen);
                }
            }
        }

    }

    public void setContext(Context c){
        context = c;
    }
    public void setEventbyId(String s){
        centerEvent = s;
    }
    public void setCameFromPerson(Boolean marker){
        cameFromPerson = marker;
    }
    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }
}