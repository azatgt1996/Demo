package com.javamaster.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.javamaster.demo.model.Model;
import com.javamaster.demo.model.api.AbstractAPIListener;

public class RecoveryActivity extends AppCompatActivity {
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        final EditText loginText = findViewById(R.id.textRLogin);
        final EditText emailText = findViewById(R.id.textREmail);

        Button recoveryButton = findViewById(R.id.button_recovery);
        recoveryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String login = loginText.getText().toString().trim();
                String email = emailText.getText().toString().trim();
                view = v;

                if (validate(login, email)) {

                    final Model model = Model.getInstance(RecoveryActivity.this.getApplication());
                    if (model.isOnline(RecoveryActivity.this)) {

                        model.recovery(login, email, new AbstractAPIListener() {
                            @Override
                            public void onRecovered(String mes) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RecoveryActivity.this);
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

    private boolean validate(String login, String email) {
        if (login == null || login.length() < 5) {
            Snackbar.make(view, "Login is small (min 5 symbols)", 2000).show();
            return false;
        }
        if (email == null || !email.matches("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")) {
            Snackbar.make(view, "Email is not valid", 2000).show();
            return false;
        }
        return true;
    }
}
