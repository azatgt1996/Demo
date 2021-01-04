package com.javamaster.demo.ui.phones;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PhonesViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> mList = new MutableLiveData<>();
    private ArrayList<String> list;
    private boolean isInitialized = false;

    public void init() {
        if (!isInitialized) {
            list = new ArrayList<>();
            list.add("8 908 456 82 55");
            list.add("8 908 456 82 44");
            list.add("8 908 456 82 33");
            mList.setValue(list);
            isInitialized = true;
        }
    }
    public PhonesViewModel() {

    }

    public MutableLiveData<ArrayList<String>> getList() {
        return mList;
    }

    public void deleteItem(String phone) {
        list.remove(phone);
        mList.postValue(list);
    }

    public void deleteAll() {
        list = new ArrayList<>();
        mList.postValue(list);
    }

    public void addItem(String phone) {
        list.add(phone);
        mList.postValue(list);
    }

    public void changeItem(final String changedPhoneNum, final String oldPhone) {
        int pos = list.indexOf(oldPhone);
        list.set(pos, changedPhoneNum);
        mList.postValue(list);
    }
}