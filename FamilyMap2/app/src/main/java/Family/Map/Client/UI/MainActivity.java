package Family.Map.Client.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import Family.Map.Client.Data.DataCache;
import Family.Map.R;

public class MainActivity extends AppCompatActivity {




    public void switchToMap() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        MapsFragment mapsFragment = (MapsFragment) fragmentManager.findFragmentById(R.id.map);
        DataCache DC = DataCache.getDataCache();

        if(mapsFragment == null){
            mapsFragment = new MapsFragment();
            //Birth ID of the current user
            String s = DC.getPersonToEventsMap().get(DC.getCurrentUser().getPersonID_ID()).get(0).getEventID();
            mapsFragment.setEventbyId(s);
            mapsFragment.setContext(this);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_layout, mapsFragment)
                    .commit();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment loginFrag = fm.findFragmentById(R.id.fragment_layout);

        if (loginFrag == null) {
            loginFrag = LoginFragment.newInstance(this);
            fm.beginTransaction()
                    .add(R.id.fragment_layout, loginFrag)
                    .commit();
        }
    }
     
}