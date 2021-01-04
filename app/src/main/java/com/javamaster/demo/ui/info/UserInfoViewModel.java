package com.javamaster.demo.ui.info;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.javamaster.demo.Util;
import com.javamaster.demo.model.User;

public class UserInfoViewModel extends AndroidViewModel {

    private User user;
    private MutableLiveData<String> mLogin;
    private MutableLiveData<String> mName;
    private MutableLiveData<String> mEmail;

    public UserInfoViewModel(final Application application) {
        super(application);
        mLogin = new MutableLiveData<>();
        mName = new MutableLiveData<>();
        mEmail = new MutableLiveData<>();


        final Util util = Util.getInstance(application.getApplicationContext());
        user = util.getUserInfo();

        String login = user.getLogin();
        String name = user.getName();
        String email = user.getEmail();

        mLogin.setValue(login);
        mName.setValue(name);
        mEmail.setValue(email);

    }

    public LiveData<String> getLogin() {
        return mLogin;
    }

    public LiveData<String> getName() {
        return mName;
    }

    public LiveData<String> getEmail() {
        return mEmail;
    }
}