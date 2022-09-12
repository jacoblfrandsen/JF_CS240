package Family.Map.Client.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;

import Family.Map.Client.ServerProxy.ServerProxy;
import Family.Map.R;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.LoginResult;
import Results.PersonResult;
import Results.RegisterResult;

public class GetPersonTask extends AsyncTask<String, Void, PersonResult> {
    Fragment frag;
    Context context;
    PersonResult personResult;
    LoginResult loginResult;
    LoginRequest loginRequest;
    RegisterResult registerResult;
    RegisterRequest registerRequest;
    public GetPersonTask(Fragment in, Context c, LoginRequest LR1, LoginResult LR2){
        frag = in;
        context = c;
        loginRequest = LR1;
        loginResult = LR2;
        registerResult = new RegisterResult();
        registerRequest = new RegisterRequest();
        registerRequest.setHost(loginRequest.getHost());
        registerRequest.setPort(loginRequest.getPort());
    }

    @Override
    protected PersonResult doInBackground(String... authUrl) {
        try {
            ServerProxy serverProxy = new ServerProxy();
            URL url = new URL(authUrl[0]);

            personResult = serverProxy.getPerson(url, authUrl[1]);

            return personResult;

        } catch (MalformedURLException e){
            personResult.setMessage("Bad URl");
            personResult.setSuccess(false);
            return personResult;
        }
    }

    protected void onPostExecute(PersonResult result){
        if (result.getSuccess()){

            String stringForToastIfSuccessful = ("Login Success!" + "\n" + result.getFirstName() + "\n" + result.getLastName());
            registerResult.setAuthToken(loginResult.getAuthtoken());
            String url = ("http://" + registerRequest.getHost() + ":" + registerRequest.getPort() + "/person/");

            Toast.makeText(frag.getContext(), stringForToastIfSuccessful,
                    Toast.LENGTH_SHORT).show();

            GetPeopleTask peopleTask = new GetPeopleTask(frag,registerRequest, registerResult, context);
            peopleTask.execute(url, registerResult.getAuthToken());

        }
        else {
            Toast.makeText(frag.getContext(),
                    R.string.loginNotSuccessfulPerson,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
