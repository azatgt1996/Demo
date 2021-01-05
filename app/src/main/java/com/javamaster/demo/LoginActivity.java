package com.javamaster.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.javamaster.demo.model.Model;
import com.javamaster.demo.model.User;
import com.javamaster.demo.model.api.AbstractAPIListener;

public class LoginActivity extends AppCompatActivity {

    private long back_pressed;

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Repeat to exit", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText loginText = findViewById(R.id.loginText);
        final EditText passwordText = findViewById(R.id.passwordText);
        Button loginButton = findViewById(R.id.LoginBtn);

        loginButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                String login = loginText.getText().toString();
                String password = passwordText.getText().toString();

                final Model model = Model.getInstance(LoginActivity.this.getApplication());
                model.login(login, password, new AbstractAPIListener() {

                    @Override
                    public void onLogin(User user) {
                        if (user != null) {
                            model.setUser(user);
                            Toast.makeText(LoginActivity.this, "User " + user.getName() + " logged in!", Toast.LENGTH_LONG).show();

                            final Util util = Util.getInstance(LoginActivity.this);
                            util.saveUserInfo(user);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Invalid login/password!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
}
