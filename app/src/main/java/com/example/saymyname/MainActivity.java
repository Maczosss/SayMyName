package com.example.saymyname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoauth.Auth;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.tokens.CognitoAccessToken;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.StorageException;
import com.amplifyframework.storage.result.StorageUploadFileResult;

import java.io.File;

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
//        try {
//            CognitoAccessToken at = new CognitoAccessToken("token");
//            File temp =
//                    new File("/storage/emulated/0/Android/data/com.example.saymyname/files/Maciej_Jaremowicz_COME_WITH_ME-2021_05_11_11_38_21.3gp");
//            Amplify.Storage.uploadFile("arn:aws:s3:::recordings220358-dev",
//                    temp,
//                    this::onSendSuccess,
//                    this::onSendError);
//        }catch(Exception e ){
//            Log.i("error", e.getMessage());
//        }
        startActivity(intent);
        finish();
    }

    private void onSendError(StorageException e) {
        this.runOnUiThread(() -> Toast.makeText(getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void onSendSuccess(StorageUploadFileResult storageUploadFileResult) {
        this.runOnUiThread(() -> Toast.makeText(getApplicationContext(), "file send: " + storageUploadFileResult.getKey(), Toast.LENGTH_LONG).show());
    }
}