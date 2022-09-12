package Family.Map.Client.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.List;

import Family.Map.Client.Data.DataCache;
import Family.Map.Client.Data.EventDisplay;
import Family.Map.R;
import Model.Person;

public class PersonActivity extends AppCompatActivity {
    private String currentPersonID;
    private String tempPersonID = "personID";
    private DataCache DC = DataCache.getDataCache();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //save the personThatImUsing ID in to use it later


        if (savedInstanceState == null) {
            if (getIntent().getExtras() != null) {
                currentPersonID = getIntent().getExtras().getString(tempPersonID);
            }
        } else {
            currentPersonID = (String) savedInstanceState.getSerializable(tempPersonID);
        }
        DataCache DC = DataCache.getDataCache();
        Person personThatImUsing = DC.getPersonFromID(currentPersonID);
        ((TextView) findViewById(R.id.first_name)).setText(personThatImUsing.getFirstname());
        ((TextView) findViewById(R.id.last_name)).setText(personThatImUsing.getLastname());

        if (personThatImUsing.getGender().equals("m")) {
            ((TextView) findViewById(R.id.gender)).setText(R.string.maleString);
        } else {
            ((TextView) findViewById(R.id.gender)).setText(R.string.femaleString);
        }

        ArrayList<EventDisplay> eventDisplayArrayListEvent;
        eventDisplayArrayListEvent = DC.getEventsInOrder(personThatImUsing);
        ArrayList<EventDisplay> eventDisplayArrayListPeople;
        eventDisplayArrayListPeople = DC.getPeopleInOrder(personThatImUsing);

        ArrayList<Group> listOfGroups = new ArrayList<>();

        listOfGroups.add(new Group("Event List", eventDisplayArrayListEvent));
        listOfGroups.add(new Group("Relations List", eventDisplayArrayListPeople));


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.expandableList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAdapter myAdapter = new MyAdapter(this, listOfGroups);
        recyclerView.setAdapter(myAdapter);
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


    class MyAdapter extends ExpandableRecyclerAdapter<Group, EventDisplay, GroupHolder, InfoHolder> {

        private LayoutInflater inflater;

        public MyAdapter(Context context, List<Group> groups) {
            super(groups);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public GroupHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
            return new GroupHolder(inflater.inflate(R.layout.activity_person_title, parentViewGroup, false));
        }

        @NonNull
        @Override
        public InfoHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
            return new InfoHolder(inflater.inflate(R.layout.activity_person_line, childViewGroup, false));
        }

        @Override
        public void onBindParentViewHolder(@NonNull GroupHolder parentViewHolder, int parentPosition, @NonNull Group parent) {
            parentViewHolder.bindToGroupHolder(parent);
        }

        @Override
        public void onBindChildViewHolder(@NonNull InfoHolder childViewInfoHolder, int parentPosition, int childPosition, @NonNull Family.Map.Client.Data.EventDisplay child) {
            childViewInfoHolder.bind(child);
        }
    }


    static class GroupHolder extends ParentViewHolder {
        private TextView TV;
        /**
         * Default constructor.
         *
         * @param itemView The {@link View} being hosted in this ViewHolder
         */
        public GroupHolder(@NonNull View itemView) {
            
            super(itemView);
            TV = (TextView) itemView.findViewById(R.id.groupHolderId);
        }
        void bindToGroupHolder(Group group) {

            TV.setText(group.title);
        }



    }

    private class InfoHolder extends ChildViewHolder implements View.OnClickListener {
        private ImageView lineIcon;
        private TextView lineText;
        private String lineType;
        private String linePersonID;
        /**
         * Default constructor.
         *
         * @param itemView The {@link View} being hosted in this ViewHolder
         */
//        public Holder(@NonNull View itemView) {
//            super(itemView);
//        }
        public InfoHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.line_layout).setOnClickListener(this);
            lineText = itemView.findViewById(R.id.lineText);
            lineIcon = itemView.findViewById(R.id.line_icon);
        }

        void bind(EventDisplay line) {
            if (line.getLineType().equals("event")){
                lineIcon.setImageResource(R.drawable.event);
            } else if (line.getLineType().equals("female")){
                lineIcon.setImageResource(R.drawable.female);
            } else if (line.getLineType().equals("male")){
                lineIcon.setImageResource(R.drawable.male);
            } else {
                lineIcon.setImageResource(R.drawable.android1);
            }

            lineText.setText(line.getLineText());
            lineType = line.getLineType();
            linePersonID = line.getLinePersonID();
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("personID", linePersonID);
            Intent intent;
            if (lineType.equals("event")){
                intent = new Intent(v.getContext(), EventActivity.class);
            } else if (lineType.equals("male") || lineType.equals("female")){
                intent = new Intent(v.getContext(), PersonActivity.class);
            } else {
                intent = new Intent(v.getContext(), MainActivity.class);
            }
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
    static class Group implements Parent<EventDisplay> {
        String title;
        List<EventDisplay> list;

        Group(String name, List<EventDisplay> list){
            this.title = name;
            this.list = list;
        }

        @Override
        public List<EventDisplay> getChildList() {
            return list;
        }

        @Override
        public boolean isInitiallyExpanded() {
            return false;
        }
    }


}