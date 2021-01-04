package com.javamaster.demo.ui.phones;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.javamaster.demo.db.DbManager;

import java.util.ArrayList;

public class PhonesViewModel extends ViewModel {

    private DbManager dbManager;
    private MutableLiveData<ArrayList<String>> mList = new MutableLiveData<>();
    private ArrayList<String> list;
    private boolean isInitialized = false;

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setDbManager(Context context) {
        dbManager = new DbManager(context);
    }

    public void init() {
        list = new ArrayList<>();
        list = (ArrayList<String>) dbManager.getAllPhones();
        mList.setValue(list);
        isInitialized = true;
    }
    public PhonesViewModel() {

    }

    public void openDb() {
        dbManager.openDb();
    }

    public void closeDb() {
        dbManager.closeDb();
    }

    public MutableLiveData<ArrayList<String>> getList() {
        return mList;
    }

    public void deleteItem(String phone) {
        list.remove(phone);
        mList.postValue(list);

        dbManager.deletePhone(phone);
    }

    public void deleteAll() {
        list = new ArrayList<>();
        mList.postValue(list);

        dbManager.deleteAllPhones();
    }

    public boolean addItem(String phone) {
        if (!list.contains(phone)) {
            list.add(phone);
            mList.postValue(list);

            dbManager.insertPhone(phone);
            return true;
        } else return false;
    }

    public boolean changeItem(final String oldPhone, final String changedPhone) {
        if (!list.contains(changedPhone)) {
            int pos = list.indexOf(oldPhone);
            list.set(pos, changedPhone);
            mList.postValue(list);

            dbManager.updatePhone(oldPhone, changedPhone);
            return true;
        } else return false;
    }
}