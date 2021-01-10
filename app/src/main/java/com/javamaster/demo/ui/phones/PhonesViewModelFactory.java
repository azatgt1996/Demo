package com.javamaster.demo.ui.phones;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PhonesViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private Context mContext;
    private View mView;

    public PhonesViewModelFactory(Application application, Context context, View view) {
        mApplication = application;
        mView = view;
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PhonesViewModel(mApplication, mContext, mView);
    }
}
