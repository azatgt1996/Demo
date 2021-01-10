package com.javamaster.demo.model;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.javamaster.demo.model.api.API;
import com.javamaster.demo.model.api.APIListener;
import com.javamaster.demo.model.api.WebAPI;

public class Model {
    private static Model sInstance = null;
    private final API mApi;
    private User mUser;
    private final Application mApplication;

    public static Model getInstance(Application application) {
        if (sInstance == null) {
            sInstance = new Model(application);
        }
        return sInstance;
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private Model(Application application) {
        mApplication = application;
        mApi = new WebAPI(mApplication, this);
    }

    public Application getApplication() {
        return mApplication;
    }

    public void login(String login, String password, APIListener listener) {
        mApi.login(login, password, listener);
    }

    public void register(String login, String name, String email, String password, APIListener listener) {
        mApi.register(login, name, email, password, listener);
    }

    public void recovery(String login, String email, APIListener listener) {
        mApi.recovery(login, email, listener);
    }

    public void loadPhones(APIListener listener) {
        mApi.loadPhones(listener);
    }

    public void addPhone(String phoneNumber, APIListener listener) {
        mApi.addPhone(phoneNumber, listener);
    }

    public void deletePhone(int idPhone, APIListener listener) {
        mApi.deletePhone(idPhone, listener);
    }

    public void deleteAllPhones(APIListener listener) {
        mApi.deleteAllPhones(listener);
    }

    public void updatePhone(int idPhone, String phoneNumber, APIListener listener) {
        mApi.updatePhone(idPhone, phoneNumber, listener);
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;
    }
}
