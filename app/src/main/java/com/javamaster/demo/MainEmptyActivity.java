package com.javamaster.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.javamaster.demo.model.Model;
import com.javamaster.demo.model.User;

public class MainEmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent activityIntent;

        final Util util = Util.getInstance(this);
        User user = util.getUserInfo();

        if (user != null) {
            activityIntent = new Intent(this, MainActivity.class);
            final Model model = Model.getInstance(this.getApplication());
            model.setUser(user);
        }
        else {
            activityIntent = new Intent(this, LoginActivity.class);
        }

        startActivity(activityIntent);
        finish();
    }
}
