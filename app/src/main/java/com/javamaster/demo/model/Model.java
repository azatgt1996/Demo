package com.javamaster.demo.model;

import android.app.Application;

import com.javamaster.demo.model.api.API;
import com.javamaster.demo.model.api.APIListener;
import com.javamaster.demo.model.api.WebAPI;

public class Model {
    private static Model sInstance = null;
    private final API mApi;
    private User mUser;

    public static Model getInstance(Application application) {
        if (sInstance == null) {
            sInstance = new Model(application);
        }
        return sInstance;
    }

    private Model(Application application) {
        mApplication = application;
        mApi = new WebAPI(mApplication);
    }

    private final Application mApplication;

    public Application getApplication() {
        return mApplication;
    }

    public void login(String login, String password, APIListener listener) {
        mApi.login(login, password, listener);
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;
    }
}
