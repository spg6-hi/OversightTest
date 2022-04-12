package com.example.oversighttest.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oversighttest.R;
import com.example.oversighttest.entities.Session;
import com.example.oversighttest.entities.User;
import com.example.oversighttest.network.NetworkCallback;
import com.example.oversighttest.network.NetworkManager;
import com.example.oversighttest.services.TokenSaver;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText mUserName;
    private EditText mPassword;
    private Button mConfirmLogin;
    private Button mSignUp;
    private static final Base64.Encoder base64Encoder = Base64.getEncoder();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserName = (EditText)findViewById(R.id.mNewUserName);
        mPassword = (EditText)findViewById(R.id.mNewPassword);

        mConfirmLogin = (Button)findViewById(R.id.mconfirmSignup);
        mConfirmLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mUserName.getText().toString();
                String password = mPassword.getText().toString();

                String userToken = TokenSaver.generateUserToken(userName, password);
                //TokenSaver.setToken(getApplicationContext(), userToken);

                NetworkManager nm = NetworkManager.getInstance(getApplicationContext());
                nm.loginUser(userToken, new NetworkCallback<User>(){
                    @Override
                    public void onSuccess(User result){
                        if (result != null){
                            Session.setLoggedIn(result);
                            openMainActivity();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(String errorString){
                        System.out.println(errorString);
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mSignUp = (Button)findViewById(R.id.mLogIn);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
    }

    public static Intent newIntent(Context packageContext){
        Intent i = new Intent(packageContext, LoginActivity.class);
        return i;
    }

    public void signUp(){
        Intent intent = SignUpActivity.newIntent(this);
        startActivity(intent);
    }

}
