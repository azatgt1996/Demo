package com.javamaster.demo.ui.phones;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.Snackbar;
import com.javamaster.demo.CustomFragmentListener;
import com.javamaster.demo.db.DbManager;
import com.javamaster.demo.model.Model;
import com.javamaster.demo.model.Phone;
import com.javamaster.demo.model.api.AbstractAPIListener;

import java.util.ArrayList;
import java.util.List;

public class PhonesViewModel extends ViewModel {

    private DbManager dbManager;
    private final MutableLiveData<ArrayList<Phone>> mList = new MutableLiveData<>();
    private ArrayList<Phone> list = new ArrayList<>();
    private final Model model;
    private final Application mApplication;
    private final Context mContext;
    private final View mView;
    private CustomFragmentListener customFragmentListener;

    public void init() {
//        list = (ArrayList<Phone>) dbManager.getAllPhones();
        if (model.isOnline(mContext)) {
            model.loadPhones(new AbstractAPIListener(){
                @Override
                public void onPhonesLoaded(List<Phone> phones) {
                    list = (ArrayList<Phone>) phones;
                    mList.setValue(list);
                }
            });
        } else {
            Snackbar.make(mView, "No internet connection!", 2000).show();
        }
    }

    public PhonesViewModel(Application application, Context context, View view) {
        mApplication = application;
        mContext = context;
        mView = view;
        model = Model.getInstance(mApplication);
//        dbManager = new DbManager(context);
    }

    public void openDb() {
        dbManager.openDb();
    }

    public void closeDb() {
        dbManager.closeDb();
    }

    public MutableLiveData<ArrayList<Phone>> getList() {
        return mList;
    }

    public void deleteItem(final Phone phone) {
        if (model.isOnline(mContext)) {
            model.deletePhone(phone.getId(), new AbstractAPIListener() {
                @Override
                public void onPhoneDeleted(String mes) {
                    list.remove(phone);
                    mList.setValue(list);

//                    dbManager.deletePhone(phone);
                    Snackbar.make(mView, mes, 2000).show();
                }
            });
        } else {
            Snackbar.make(mView, "No internet connection!", 2000).show();
        }
    }

    public void deleteAll() {
        if (model.isOnline(mContext)) {
            model.deleteAllPhones(new AbstractAPIListener() {
                @Override
                public void onAllPhonesDeleted(String mes) {
                    list = new ArrayList<>();
                    mList.setValue(list);

//                    dbManager.deleteAllPhones();
                    Snackbar.make(mView, mes, 2000).show();
                }
            });
        } else {
            Snackbar.make(mView, "No internet connection!", 2000).show();
        }
    }

    public void addItem(final Phone phone) {
        if (!Phone.isExisted(list, phone.getPhoneNumber())) {
            if (model.isOnline(mContext)) {
                if (!validate(phone)) return;
                model.addPhone(phone.getPhoneNumber(), new AbstractAPIListener() {
                    @Override
                    public void onPhoneAdded(String mes, int id) {
                        phone.setId(id);
                        list.add(phone);
                        mList.setValue(list);

//                        dbManager.insertPhone(phone);

//                        Snackbar.make(mView, mes, 2000).show();
                        customFragmentListener.onBackPressed();
                    }
                });
            } else {
                Snackbar.make(mView, "No internet connection!", 2000).show();
            }
        } else {
            Snackbar.make(mView, "This phone already exists!", 2000).show();
        }
    }

    private boolean validate(Phone phone) {
        if (phone.getPhoneNumber().length() < 5) {
            Snackbar.make(mView, "Minimum 5 symbols", 2000).show();
            return false;
        }
        if (!phone.getPhoneNumber().matches("^[-0-9+() ]+$")) {
            Snackbar.make(mView, "Phone number isn't valid (1-9+- ())", 2000).show();
            return false;
        }
        return true;
    }

    public void changeItem(final Phone changedPhone) {
        if (!Phone.isExisted(list, changedPhone.getPhoneNumber())) {
            if (model.isOnline(mContext)) {
                if (!validate(changedPhone)) return;
                model.updatePhone(changedPhone.getId(), changedPhone.getPhoneNumber(), new AbstractAPIListener() {
                    @Override
                    public void onPhoneUpdated(String mes) {
                        int pos = Phone.getIndById(list, changedPhone.getId());
                        if (pos != -1) {
                            list.set(pos, new Phone(changedPhone.getId(), changedPhone.getPhoneNumber()));
                            mList.setValue(list);

//                            dbManager.updatePhone(oldPhone, changedPhone);
                            Snackbar.make(mView, mes, 2000).show();
                            customFragmentListener.onCommitted();
                        }
                    }
                });
            } else {
                Snackbar.make(mView, "No internet connection!", 2000).show();
            }
        } else {
            Snackbar.make(mView, "This phone already exists!", 2000).show();
        }
    }

    public void setListener(CustomFragmentListener listener){
        customFragmentListener = listener;
    }
}