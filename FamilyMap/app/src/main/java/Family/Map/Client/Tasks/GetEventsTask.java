package Family.Map.Client.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;

import Family.Map.Client.Data.DataCache;
import Family.Map.Client.ServerProxy.ServerProxy;
import Family.Map.Client.UI.MainActivity;
import Family.Map.R;
import Requests.RegisterRequest;
import Results.EventsResult;

public class GetEventsTask extends AsyncTask<String,Void, EventsResult> {
    EventsResult eventsResult = new EventsResult();
    private Fragment frag;
    private Context context;
    private RegisterRequest registerRequest;

    public GetEventsTask(Fragment f, RegisterRequest RR1, Context c){
        frag = f;
        context = c;
        registerRequest = RR1;
    }
    @Override
    protected EventsResult doInBackground(String... strings) {
        try{

            ServerProxy serverProxy = new ServerProxy();
            URL url = new URL(strings[0]);

            eventsResult = serverProxy.getEvents(url, strings[1]);
            //TODO
            //need to add authToken to data cache
            //It doesn't get stored int EventsResult so has to be added here

            return eventsResult;

        }
        catch (MalformedURLException e){
            eventsResult.setMessage("Bad URl");
            eventsResult.setSuccess(false);
            return eventsResult;
        }
    }

    @Override
    protected void onPostExecute(EventsResult eventsResult) {
        if(eventsResult.getSuccess()){
            //Save people info into datachace
            DataCache DC = DataCache.getDataCache();
            DC.settingEventMap(eventsResult.getData());

            MainActivity mainActivity = (MainActivity) context;
            mainActivity.switchToMap();

            //output if getting people worked
            Toast.makeText(frag.getContext(),
                    R.string.gettingPeopleSuccess,
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(frag.getContext(),
                    R.string.gettingPeopleFailed,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
