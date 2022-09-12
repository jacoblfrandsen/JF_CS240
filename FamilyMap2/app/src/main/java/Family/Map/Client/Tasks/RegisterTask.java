package Family.Map.Client.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;

import Family.Map.Client.ServerProxy.ServerProxy;
import Family.Map.R;
import Requests.RegisterRequest;
import Results.RegisterResult;

public class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResult> {

    private RegisterResult result = new RegisterResult();
    private RegisterRequest request = new RegisterRequest();
    private Fragment frag = new Fragment();
    private Context mainActivity;

    public RegisterTask (Fragment in, Context activity){
        this.frag = in;
        mainActivity = activity;
    }

    @Override
    protected RegisterResult doInBackground(RegisterRequest... registerRequests) {
        request = registerRequests[0];
        try{
            ServerProxy serverProxy = new ServerProxy();

            URL url = new URL("http://" + registerRequests[0].getHost() + ":" + registerRequests[0].getPort() + "/user/register");

            result = serverProxy.register(url, registerRequests[0]);
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result.setMessage("URL BAD");
            result.setSuccess(false);
            return result;
        }
    }

    protected void onPostExecute(RegisterResult registerResult){
        if(registerResult == null){
            Toast.makeText(frag.getContext(),
                    R.string.registerNotSuccessful,
                    Toast.LENGTH_SHORT).show();
        }

        if (registerResult.getSuccess()){ //was a successful register

            String url = ("http://" + request.getHost() + ":" + request.getPort() + "/person/");
            String stringForToastIfSuccessful = ("Register Success!" + "\n" + request.getFirstName() + "\n" + request.getLastName());

            Toast.makeText(frag.getContext(), stringForToastIfSuccessful,
                    Toast.LENGTH_SHORT).show();

            GetPeopleTask peopleTask = new GetPeopleTask(frag, request, registerResult, mainActivity);
            peopleTask.execute(url, registerResult.getAuthToken());


        } else {
            Toast.makeText(frag.getContext(),
                    R.string.registerNotSuccessful,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
