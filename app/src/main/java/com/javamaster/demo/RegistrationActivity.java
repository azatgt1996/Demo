package com.javamaster.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.javamaster.demo.model.Model;
import com.javamaster.demo.model.User;
import com.javamaster.demo.model.api.AbstractAPIListener;

import static java.util.Objects.isNull;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EditText nameText = findViewById(R.id.textName);
        final EditText loginText = findViewById(R.id.textLogin);
        final EditText emailText = findViewById(R.id.textEmail);

        final EditText passwordText = findViewById(R.id.textPassword1);
        final EditText passwordConfirmText = findViewById(R.id.textPassword2);

        Button registrationButton = findViewById(R.id.button_registration);
        registrationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = nameText.getText().toString().trim();
                String login = loginText.getText().toString().trim();
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String confirmPassword = passwordConfirmText.getText().toString().trim();

                if (validate(name, login, email, password, confirmPassword)) {

                    final Model model = Model.getInstance(RegistrationActivity.this.getApplication());
                    if (model.isOnline(RegistrationActivity.this)) {

                        model.register(login, name, email, password, new AbstractAPIListener() {
                            @Override
                            public void onRegistered(String mes) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                                builder.setMessage(mes);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                builder.show();
                            }
                        });
                    } else {
                        Toast.makeText(RegistrationActivity.this, "No internet connection!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean validate(String name, String login, String email, String password, String confirmPassword ) {
        if (login == null || login.length() < 5) {
            Toast.makeText(RegistrationActivity.this, "Login is empty or too small", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password == null || password.length() < 8) {
            Toast.makeText(RegistrationActivity.this, "Password is too insecure", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(RegistrationActivity.this, "You entered two different passwords", Toast.LENGTH_LONG).show();
            return false;
        }
        if (email == null || !email.matches("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")) {
            Toast.makeText(RegistrationActivity.this, "Email is empty or not valid", Toast.LENGTH_LONG).show();
            return false;
        }
        if (name == null || name.length() < 2) {
            Toast.makeText(RegistrationActivity.this, "Name is empty or too small", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
