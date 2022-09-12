package Family.Map.Client.UI;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import Family.Map.Client.Tasks.RegisterTask;
import Family.Map.Client.Tasks.SignInTask;
import Family.Map.R;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.LoginResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private static Context context;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private LoginResult loginResult;
    private Button signInButton;
    private Button registerButton;
    private EditText serverHostEditField;
    private EditText serverPortEditField;
    private EditText userNameEditFeild;
    private EditText passwordEditFeild;

    private EditText lastNameEditFeild;
    private EditText firstNameEditFeild;
    private EditText emailEditFeild;
    private RadioGroup genderGroup;

    public LoginFragment() {
        // Required empty public constructor
    }



    public static LoginFragment newInstance(Context in) {
        context = in;
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginRequest = new LoginRequest();
        registerRequest = new RegisterRequest();
    }

    private void emptyFieldCheckerLogin(){
        String EF1 = serverHostEditField.getText().toString();
        String EF2 = serverPortEditField.getText().toString();
        String EF3 = userNameEditFeild.getText().toString();
        String EF4 = passwordEditFeild.getText().toString();
        if(EF1.equals("")|| EF2.equals("")|| EF3.equals("")|| EF4.equals("")){
            signInButton.setEnabled(false);
        }
        else{
            signInButton.setEnabled(true);
        }
    }
    private void emptyFieldCheckerRegister(){
        if(serverHostEditField.getText().toString().equals("")||
                serverPortEditField.getText().toString().equals("") ||
                userNameEditFeild.getText().toString().equals("")||
                passwordEditFeild.getText().toString().equals("")||
                firstNameEditFeild.getText().toString().equals("")||
                lastNameEditFeild.getText().toString().equals("")||
                emailEditFeild.getText().toString().equals("")){
            registerButton.setEnabled(false);
        }
        else{
            registerButton.setEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        signInButton = (Button) v.findViewById(R.id.signInButton);
        signInButton.setEnabled(false);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInButtonClicked();
            }
        });

        registerButton = (Button) v.findViewById(R.id.registerButton);
        registerButton.setEnabled(false);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterButtonClicked();
            }
        });

        serverHostEditField = (EditText) v.findViewById(R.id.serverHostEditText);
        serverHostEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("1")) {
                    loginRequest.setHost("10.0.2.2");
                    registerRequest.setHost("10.0.2.2");
                } else {
                    loginRequest.setHost(s.toString());
                    registerRequest.setHost(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                emptyFieldCheckerLogin();
                emptyFieldCheckerRegister();
            }
        });

        serverPortEditField = (EditText) v.findViewById(R.id.serverPortEditText);
        serverPortEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("1")) {
                    loginRequest.setPort("8080");
                    registerRequest.setPort("8080");
                } else {
                    loginRequest.setPort(s.toString());
                    registerRequest.setPort(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                emptyFieldCheckerLogin();
                emptyFieldCheckerRegister();
            }
        });

        userNameEditFeild = (EditText) v.findViewById(R.id.userNameEditText);
        userNameEditFeild.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginRequest.setUsername(s.toString());
                registerRequest.setUsername(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                emptyFieldCheckerLogin();
                emptyFieldCheckerRegister();
            }
        });

        passwordEditFeild = (EditText) v.findViewById(R.id.passwordEditText);
        passwordEditFeild.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginRequest.setPassword(s.toString());
                registerRequest.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                emptyFieldCheckerLogin();
                emptyFieldCheckerRegister();
            }
        });

        firstNameEditFeild = (EditText) v.findViewById(R.id.firstNameEditText);
        firstNameEditFeild.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerRequest.setFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                emptyFieldCheckerRegister();
            }

        });

        lastNameEditFeild = (EditText) v.findViewById(R.id.lastNameEditText);
        lastNameEditFeild.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerRequest.setLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                emptyFieldCheckerRegister();
            }
        });

        emailEditFeild = (EditText) v.findViewById(R.id.emailEditText);
        emailEditFeild.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerRequest.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                emptyFieldCheckerRegister();
            }
        });

        genderGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.femaleRadioButton) {
                    registerRequest.setGender("f");
                }
                if (checkedId == R.id.maleRadioButton) {
                    registerRequest.setGender("m");
                }
            }
        });

        return v;
    }



    private void onSignInButtonClicked() {
        SignInTask signInTask = new SignInTask(this, context);
        signInTask.execute(loginRequest);
    }
    private void onRegisterButtonClicked(){
        RegisterTask registerTask = new RegisterTask(this, context);
        registerTask.execute(registerRequest);
    }

}