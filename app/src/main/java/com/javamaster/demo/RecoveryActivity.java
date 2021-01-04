package com.javamaster.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                String login = loginText.getText().toString();
                String email = emailText.getText().toString();


                if (validate(login, email)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RecoveryActivity.this);
                    builder.setMessage(R.string.info_recovery);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    private boolean validate(String login, String email) {
        if (login == null || login.length() < 5) {
            Toast.makeText(RecoveryActivity.this, "Login is empty or too small", Toast.LENGTH_LONG).show();
            return false;
        }
        if (email == null || !email.matches("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")) {
            Toast.makeText(RecoveryActivity.this, "Email is empty or not valid", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
