package com.javamaster.demo;

import android.content.Context;
import android.content.SharedPreferences;

import com.javamaster.demo.model.User;

public class Util {
    private static Util instance = null;

    private static final String APP_PREFERENCES = "settings";
    private static final String APP_PREFERENCES_TOKEN = "token";
    private static final String APP_PREFERENCES_LOGIN = "login";
    private static final String APP_PREFERENCES_NAME = "name";
    private static final String APP_PREFERENCES_EMAIL = "email";
    private final SharedPreferences mSettings;
    private final SharedPreferences.Editor editor;

    private final Context context;

    public Context getContext() {
        return context;
    }

    public static Util getInstance(Context context) {
        if (instance == null) {
            instance = new Util(context);
        }
        return instance;
    }

    private Util(Context context) {
        this.context = context;
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        editor = mSettings.edit();
    }

    public void saveUserInfo(User user) {
        editor.putString(APP_PREFERENCES_LOGIN, user.getLogin());
        editor.putString(APP_PREFERENCES_NAME, user.getName());
        editor.putString(APP_PREFERENCES_EMAIL, user.getEmail());
        editor.putString(APP_PREFERENCES_TOKEN, user.getToken());
        editor.apply();
    }

    public User getUserInfo() {
        if(mSettings.contains(APP_PREFERENCES_TOKEN)) {
            String login = mSettings.getString(APP_PREFERENCES_LOGIN, "");
            String name = mSettings.getString(APP_PREFERENCES_NAME, "");
            String email = mSettings.getString(APP_PREFERENCES_EMAIL, "");
            String token = mSettings.getString(APP_PREFERENCES_TOKEN, "");

            return new User(name, login, token, email) ;
        }
        return null;
    }

    public void removeUserInfo() {
        editor.remove(APP_PREFERENCES_LOGIN);
        editor.remove(APP_PREFERENCES_NAME);
        editor.remove(APP_PREFERENCES_EMAIL);
        editor.remove(APP_PREFERENCES_TOKEN);
        editor.commit();
    }

}
