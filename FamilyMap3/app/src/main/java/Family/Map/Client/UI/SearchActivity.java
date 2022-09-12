package Family.Map.Client.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Family.Map.Client.Data.DataCache;
import Family.Map.Client.Data.EventDisplay;
import Family.Map.R;

public class SearchActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    EditText searchBar;
    Context context;
//Activity implements View.OnKeyListener
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        setTitle("Family Map: Search");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchBar = findViewById(R.id.search_bar);

        OnKeyListener onKeyListener = new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyevent) {
                DataCache DC = DataCache.getDataCache();
//
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN)&& (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    DC.search(searchBar.getText().toString());


                    SearchAdapter adapter = new SearchAdapter(context, DC.getSearchList());
                    recyclerView.setAdapter(adapter);

                    return true;
                }
                return false;
            }
        };

        searchBar.setOnKeyListener(onKeyListener);
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

//    @Override
//    public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
//        DataCache DC = DataCache.getDataCache();
//
//        if ((keyevent.getAction() == KeyEvent.ACTION_DOWN)&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
//
//            DC.search(search_bar.getText().toString());
//
//
//            SearchAdapter adapter = new SearchAdapter(context, DC.getSearchList());
//            recyclerView.setAdapter(adapter);
//
//            return true;
//        }
//        return false;
//    }

    class SearchAdapter extends RecyclerView.Adapter<SearchActivity.Holder> {
        private ArrayList<EventDisplay> theList;
        private LayoutInflater inflater;
        public SearchAdapter(Context context, ArrayList<EventDisplay> searchList) {
            this.theList = searchList;
            inflater = LayoutInflater.from(context);
        }
        @Override
        public SearchActivity.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.expandable_list, parent, false);
            Holder thisHolder = new Holder(view);
            return thisHolder;
        }

        @Override
        public void onBindViewHolder(SearchActivity.Holder holder, int position) {
            EventDisplay item = theList.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return theList.size();
        }
    }

    private class Holder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tView;
        private TextView bView;
        private ImageView image;
        private EventDisplay outer_item;

        public Holder(View view) {
            super(view);
            tView = view.findViewById(R.id.textTop);
            bView = view.findViewById(R.id.textBottom);
            image = view.findViewById(R.id.imageView);
        }
        public void test(){

        }
        void bind(EventDisplay item) {
            outer_item = item;
            if(item.getEvent() == null){
                if(item.getGender().equals("m")){
                    image.setImageResource(R.drawable.male);
                }else{
                    image.setImageResource(R.drawable.female);
                }
                tView.setText(item.getName());
                bView.setText("");

                View.OnClickListener searchClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, PersonActivity.class);
                        intent.putExtra("personID", outer_item.getPerson().getPersonID_ID());
                        startActivity(intent);
                    }
                };

                image.setOnClickListener(searchClickListener);

                tView.setOnClickListener(searchClickListener);

                bView.setOnClickListener(searchClickListener);


            }else{
                image.setImageResource(R.drawable.event);
                tView.setText(item.getEventDetails());
                bView.setText(item.getName());

                View.OnClickListener searchClickListener2 = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, EventActivity.class);
                        intent.putExtra("eventIDToZoom", outer_item.getEvent().getEventID());
                        startActivity(intent);
                    }
                };

                image.setOnClickListener(searchClickListener2);

                tView.setOnClickListener(searchClickListener2);

                bView.setOnClickListener(searchClickListener2);
            }
        }
        @Override
        public void onClick(View v) {

        }


    }
}

