package com.javamaster.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.javamaster.demo.model.Model;
import com.javamaster.demo.model.api.AbstractAPIListener;

public class RecoveryActivity extends AppCompatActivity {

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
                        });
                    } else {
                        Toast.makeText(RecoveryActivity.this, "No internet connection!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean validate(String login, String email) {
        if (login == null || login.length() < 5) {
            Toast.makeText(RecoveryActivity.this, "Login is small (min 5 symbols)", Toast.LENGTH_LONG).show();
            return false;
        }
        if (email == null || !email.matches("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")) {
            Toast.makeText(RecoveryActivity.this, "Email is not valid", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
