package com.example.saymyname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }

    public void onJoinPressed(View view) {
        EditText email = findViewById(R.id.txtEmail);
        EditText password = findViewById(R.id.txtPassword);
        EditText name = findViewById(R.id.txtName);

        String txtEmail = email.getText().toString();
        String txtPassword = password.getText().toString();

        if (txtEmail.isEmpty() || txtPassword.isEmpty()) {
            this.runOnUiThread(() -> Toast.makeText(
                    getApplicationContext(),
                    "Please provide email and password",
                    Toast.LENGTH_LONG).show());
        }

        Amplify.Auth.signUp(
                email.getText().toString(),
                password.getText().toString(),
                AuthSignUpOptions.builder().userAttribute(
                        AuthUserAttributeKey.email(),
                        txtEmail
                ).build(),
                this::onSignUpSuccess,
                this::onSignUpError
        );
    }

    private void onSignUpError(AuthException e) {
        this.runOnUiThread(() -> Toast.makeText(
                getApplicationContext(),
                e.getMessage(),
                Toast.LENGTH_LONG).show());
    }

    private void onSignUpSuccess(AuthSignUpResult authSignUpResult) {
        Intent intent = new Intent(getApplicationContext(), ConfirmationActivity.class);
        EditText email = findViewById(R.id.txtEmail);
        EditText password = findViewById(R.id.txtPassword);
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("password", password.getText().toString());
        startActivity(intent);
    }
}