package com.javamaster.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.javamaster.demo.model.Model;
import com.javamaster.demo.model.User;
import com.javamaster.demo.model.api.AbstractAPIListener;

public class LoginActivity extends AppCompatActivity {

    private long back_pressed;
    private EditText loginText;
    private EditText passwordText;
    private Button loginButton;
    private View parentLayout;

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Snackbar.make(parentLayout, "Repeat to exit", 2000).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        parentLayout = findViewById(android.R.id.content).getRootView();
        loginText = findViewById(R.id.loginText);
        passwordText = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.LoginBtn);

        loginButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(final View v) {
                String login = loginText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                if (login.isEmpty()) {
                    Snackbar.make(v, "Login is empty", 2000).show();
                    return;
                }
                if (password.isEmpty()) {
                    Snackbar.make(v, "Password is empty", 2000).show();
                    return;
                }

                final Model model = Model.getInstance(LoginActivity.this.getApplication());
                if (model.isOnline(LoginActivity.this)) {

                    model.login(login, password, new AbstractAPIListener() {
                        @Override
                        public void onLogin(User user) {
                            if (user != null) {
                                model.setUser(user);

                                final Util util = Util.getInstance(LoginActivity.this);
                                util.saveUserInfo(user);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("fromLoginActivity", true);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailed(String mes) {
                            Snackbar.make(v, mes, 2000).show();
                        }
                    });
                } else {
                    Snackbar.make(v, "No internet connection!", 2000).show();
                }
            }
        });

        TextView registrationTextView = findViewById(R.id.textView_registration);
        registrationTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        TextView recoveryTextView = findViewById(R.id.textView_recovery);
        recoveryTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecoveryActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        passwordText.setText("");
    }
}
