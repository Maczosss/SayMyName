package com.example.saymyname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.amazonaws.mobileconnectors.cognitoauth.Auth;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AuthUser currentUser = Amplify.Auth.getCurrentUser();

        Intent intent;
        if(currentUser==null){
            //Login activity
            intent = new Intent(getApplicationContext(), LoginActivity.class);

        }
        else{
            //Go to the program
            intent = new Intent(getApplicationContext(), RecordingActivity.class);

        }
        startActivity(intent);
        finish();
    }
}