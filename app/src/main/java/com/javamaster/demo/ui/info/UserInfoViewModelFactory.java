package com.javamaster.demo.ui.info;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserInfoViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private View mView;

    public UserInfoViewModelFactory(Application application, View view) {
        mApplication = application;
        mView = view;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserInfoViewModel(mApplication, mView);
    }
}
