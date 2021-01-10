package com.javamaster.demo.ui.info;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.javamaster.demo.model.Model;
import com.javamaster.demo.model.User;

public class UserInfoViewModel extends AndroidViewModel {

    private View mView;
    private User user;
    private MutableLiveData<String> mLogin;
    private MutableLiveData<String> mName;
    private MutableLiveData<String> mEmail;

    public UserInfoViewModel(Application application, View view) {
        super(application);
        mLogin = new MutableLiveData<>();
        mName = new MutableLiveData<>();
        mEmail = new MutableLiveData<>();
        mView = view;

        final Model model = Model.getInstance(this.getApplication());
        user = model.getUser();

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