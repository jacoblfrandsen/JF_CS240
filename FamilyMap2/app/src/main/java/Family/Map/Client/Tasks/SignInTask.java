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
import Results.LoginResult;

public class SignInTask  extends AsyncTask<LoginRequest, Void, LoginResult> {

    private LoginResult result = new LoginResult();
    private LoginRequest request = new LoginRequest();
    private Fragment frag = new Fragment();
    private Context mainActivity;

    public SignInTask(Fragment in, Context activity){
        this.frag = in;
        mainActivity = activity;
    }
    public LoginResult doInBackground(LoginRequest... loginRequests){
        request = loginRequests[0];
        try{
            ServerProxy serverProxy = new ServerProxy();

            URL url = new URL("http://" + request.getHost() + ":" + request.getPort() + "/user/login");

            result = serverProxy.login(url, loginRequests[0]);
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result.setMessage("URL BAD");
            result.setSuccess(false);
            return result;
        }
    }

    protected void onPostExecute(LoginResult loginResult){

        if (result.getSuccess()){

            String url = ("http://" + request.getHost() + ":" + request.getPort() + "/person/" + loginResult.getPersonID());

            GetPersonTask personTask = new GetPersonTask(frag, mainActivity,request, loginResult);
            personTask.execute(url, loginResult.getAuthtoken());

        } else {
            Toast.makeText(frag.getContext(),
                    R.string.loginNotSuccessful,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
