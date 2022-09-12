package Family.Map.Client.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import Family.Map.Client.Data.DataCache;
import Family.Map.R;

public class SettingsActivity extends AppCompatActivity {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DataCache DC = DataCache.getDataCache();
        Switch lifeLineSwitchView = findViewById(R.id.life_line_switch);
        if (DC.getUserSettings().isLifeLines()) {
            lifeLineSwitchView.setChecked(true);
        } else {
            lifeLineSwitchView.setChecked(false);
        }

        View.OnClickListener tempListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        lifeLineSwitchView.setOnClickListener(tempListener);

        Switch treeLineSwitchView = findViewById(R.id.tree_line_switch);
        if (DC.getUserSettings().isTreeLines()){ treeLineSwitchView.setChecked(true); }
        else { treeLineSwitchView.setChecked(false); }
        View.OnClickListener temp = tempListener;

        treeLineSwitchView.setOnClickListener(temp);
        Switch spouseLineSwitchView = (Switch) findViewById(R.id.spouse_line_switch);
        if (DC.getUserSettings().isSpouseLines()){ spouseLineSwitchView.setChecked(true); }
        else { spouseLineSwitchView.setChecked(false); }
        View.OnClickListener spouseLinesListener = tempListener;

        spouseLineSwitchView.setOnClickListener(spouseLinesListener);

        Switch fatherSideSwitchView = (Switch) findViewById(R.id.father_side_switch);
        if (DC.getUserSettings().isFatherSide()){ fatherSideSwitchView.setChecked(true); }
        else { fatherSideSwitchView.setChecked(false); }

        View.OnClickListener fatherSideSwitchViewListener = tempListener;

        fatherSideSwitchView.setOnClickListener(fatherSideSwitchViewListener);

        Switch motherSideSwitchView = (Switch) findViewById(R.id.mother_side_switch);
        if (DC.getUserSettings().isMotherSide()){ motherSideSwitchView.setChecked(true); }
        else { motherSideSwitchView.setChecked(false); }
        View.OnClickListener motherSideSwitchViewListener = tempListener;

        motherSideSwitchView.setOnClickListener(motherSideSwitchViewListener);
        Switch maleEventSwitch = (Switch) findViewById(R.id.male_events_switch);
        if (DC.getUserSettings().isMaleEvents()){ maleEventSwitch.setChecked(true); }
        else { maleEventSwitch.setChecked(false); }
        View.OnClickListener maleEventSwitchListener = tempListener;

        maleEventSwitch.setOnClickListener(maleEventSwitchListener);
        Switch femaleEventSwitch = (Switch) findViewById(R.id.female_events_switch);
        if (DC.getUserSettings().isFemaleEvents()){ femaleEventSwitch.setChecked(true); }
        else { femaleEventSwitch.setChecked(false); }
        View.OnClickListener femaleEventSwitchListener = tempListener;

        femaleEventSwitch.setOnClickListener(femaleEventSwitchListener);

        RelativeLayout logoutView = (RelativeLayout) findViewById(R.id.logoutView);
        View.OnClickListener logoutListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DC.getUserSettings().setLoggedIn(false);
                DC.clearDC();
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        };

        logoutView.setOnClickListener(logoutListener);

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
