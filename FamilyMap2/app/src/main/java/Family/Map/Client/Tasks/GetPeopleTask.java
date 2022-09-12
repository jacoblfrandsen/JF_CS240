package Family.Map.Client.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;

import Family.Map.Client.Data.DataCache;
import Family.Map.Client.ServerProxy.ServerProxy;
import Family.Map.R;
import Requests.RegisterRequest;
import Results.PeopleResult;
import Results.RegisterResult;

public class GetPeopleTask extends AsyncTask <String,Void, PeopleResult> {

    private Fragment frag;
    private Context context;
    private RegisterRequest registerRequest;
    private RegisterResult registerResult;
    PeopleResult peopleResult = new PeopleResult();

    public GetPeopleTask(Fragment f, RegisterRequest RR1, RegisterResult RR2, Context c) {
        frag = f;
        context = c;
        registerRequest = RR1;
        registerResult = RR2;
    }

    @Override
    protected PeopleResult doInBackground(String... strings) {
        try {
            ServerProxy serverProxy = new ServerProxy();
            URL url = new URL(strings[0]);

            peopleResult = serverProxy.getPeople(url, strings[1]);

            return peopleResult;

        } catch (MalformedURLException e){
            peopleResult.setMessage("Bad URl");
            peopleResult.setSuccess(false);
            return peopleResult;
        }
    }

    @Override
    protected void onPostExecute(PeopleResult peopleResult) {
        if(peopleResult.getSuccess()){

            //putting the data of the people result into the datacache
            DataCache DC = DataCache.getDataCache();
            DC.settingPeopleMap(peopleResult.getData());

            String url = ("http://" + registerRequest.getHost() + ":" + registerRequest.getPort() + "/event/");

            GetEventsTask getEventsTask = new GetEventsTask(frag, registerRequest,context);
            getEventsTask.execute(url, registerResult.getAuthToken());

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
