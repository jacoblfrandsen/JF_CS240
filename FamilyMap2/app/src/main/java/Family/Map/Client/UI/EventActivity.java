package Family.Map.Client.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.MapFragment;

import Family.Map.R;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String personID = null;
        if (savedInstanceState == null) {
            if (getIntent().getExtras() != null) {
                personID = getIntent().getExtras().getString("personID");
                if(personID == null){
                    personID = getIntent().getExtras().getString("eventIDToZoom");
                }
            }
        } else {
            personID = (String) savedInstanceState.getSerializable("personID");
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        MapsFragment mapsFragment = (MapsFragment) fragmentManager.findFragmentById(R.id.map);


        if(mapsFragment==null) {
            mapsFragment = new MapsFragment();
            mapsFragment.setEventbyId(personID);
            mapsFragment.setCameFromPerson(true);
            fragmentManager.beginTransaction()
                    .replace(R.id.event_layout, mapsFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP|intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }
}
