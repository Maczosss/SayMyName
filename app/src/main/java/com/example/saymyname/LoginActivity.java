package com.example.saymyname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoauth.Auth;
import com.amazonaws.services.cognitoidentityprovider.model.UserNotConfirmedException;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Recording;
import com.amplifyframework.datastore.generated.model.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onPressLogin(View view) {
        EditText email = findViewById(R.id.txtEmail);
        EditText password = findViewById(R.id.txtPassword);
        EditText name = findViewById(R.id.txtName);

        String txtEmail = email.getText().toString();
        String txtPassword = password.getText().toString();

        if(txtEmail.isEmpty()||txtPassword.isEmpty()) {
            this.runOnUiThread(() -> Toast.makeText(
                    getApplicationContext(),
                    "Please provide email and password",
                    Toast.LENGTH_LONG).show());
        }

        Amplify.Auth.signIn(
                email.getText().toString(),
                password.getText().toString(),
                this::onLoginSuccess,
                this::onLoginError
        );
    }

    private void onLoginError(AuthException e) {
        this.runOnUiThread(() -> Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void onLoginSuccess(AuthSignInResult authSignInResult) {
        //go to record activity
        //Intent intent = new Intent(getApplicationContext(), RecordingActivity.class);
        startActivity(new Intent(getApplicationContext(), RecordingActivity.class));
    }

    public void onPressJoinPressed(View view) {
        startActivity(new Intent(getApplicationContext(), JoinActivity.class));
    }
}