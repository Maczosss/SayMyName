package com.example.saymyname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.datastore.DataStoreException;
import com.amplifyframework.datastore.DataStoreItemChange;
import com.amplifyframework.datastore.generated.model.User;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
    }

    public void onConfirmButtonPressed(View view) {
        EditText code = findViewById(R.id.confirmationCode);

        String txtCode = code.getText().toString();
        Amplify.Auth.confirmSignUp(
                getEmail(),
                txtCode,
                this::confirmationSuccess,
                this::confirmationError
        );
    }

    private void confirmationSuccess(AuthSignUpResult authSignUpResult) {
        reloginAfterConfirmation();
    }

    private void reloginAfterConfirmation() {
        String email = getEmail();
        String password = getPassword();

        Amplify.Auth.signIn(
                email,
                password,
                this::onReloginSuccess,
                this::confirmationError
        );
    }

    private void onReloginSuccess(AuthSignInResult authSignInResult) {

        String currentUserID = Amplify.Auth.getCurrentUser().getUserId();

        Amplify.DataStore.save(
                User.builder().id(currentUserID)
                        .name(getName())
                        .surname(getSurname())
                        .phoneno(getPhoneNo())
                        .build(),
                this::onSaveSuccess,
                this::onError
        );

    }

    private void onError(DataStoreException e) {
        this.runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    private <T extends Model> void onSaveSuccess(DataStoreItemChange<T> tDataStoreItemChange) {
        Intent intent = new Intent(this, RecordingActivity.class);
        startActivity(intent);
    }

    private void confirmationError(AuthException e) {
        this.runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    private String getEmail() {
        return getIntent().getStringExtra("email");
    }

    private String getPassword() {return getIntent().getStringExtra("password");}

    private String getName() {return getIntent().getStringExtra("name");}

    private String getSurname() {return getIntent().getStringExtra("surname");}

    private String getPhoneNo() {return getIntent().getStringExtra("phoneNo");}
}