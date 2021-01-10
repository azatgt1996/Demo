package com.javamaster.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.javamaster.demo.model.Model;
import com.javamaster.demo.model.api.AbstractAPIListener;

public class RegistrationActivity extends AppCompatActivity {
    private View view;
    private View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        parentLayout = findViewById(android.R.id.content).getRootView();

        final EditText nameText = findViewById(R.id.textName);
        final EditText loginText = findViewById(R.id.textLogin);
        final EditText emailText = findViewById(R.id.textEmail);

        final EditText passwordText = findViewById(R.id.textPassword1);
        final EditText passwordConfirmText = findViewById(R.id.textPassword2);

        Button registrationButton = findViewById(R.id.button_registration);
        registrationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hideKeyboard();
                String name = nameText.getText().toString().trim();
                String login = loginText.getText().toString().trim();
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String confirmPassword = passwordConfirmText.getText().toString().trim();
                view = v;

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

                            @Override
                            public void onFailed(String mes) {
                                Snackbar.make(view, mes, 2000).show();
                            }
                        });
                    } else {
                        Snackbar.make(v, "No internet connection!", 2000).show();
                    }
                }
            }
        });
    }

    private boolean validate(String name, String login, String email, String password, String confirmPassword) {
        if (login == null || login.length() < 5) {
            Snackbar.make(view, "Login is small (min 5 symbols)", 2000).show();
            return false;
        }
        if (password == null || !password.matches("(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{8,15})$")) {
            Snackbar.make(view, "Password is insecure", 2000).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            Snackbar.make(view, "You entered two different passwords", 2000).show();
            return false;
        }
        if (email == null || !email.matches("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")) {
            Snackbar.make(view, "Email is not valid", 2000).show();
            return false;
        }
        if (name == null || name.length() < 2) {
            Snackbar.make(view, "Name is small (min 2 symbols)", 2000).show();
            return false;
        }
        return true;
    }

    private void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(parentLayout.getWindowToken(), 0);
    }
}
