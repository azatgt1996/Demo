package com.javamaster.demo.ui.phones;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.javamaster.demo.db.DbManager;
import com.javamaster.demo.model.Model;
import com.javamaster.demo.model.Phone;
import com.javamaster.demo.model.api.AbstractAPIListener;

import java.util.ArrayList;
import java.util.List;

public class PhonesViewModel extends ViewModel {

    private DbManager dbManager;
    private final MutableLiveData<ArrayList<Phone>> mList = new MutableLiveData<>();
    private ArrayList<Phone> list;
    private boolean isInitialized = false;
    private Model model;
    private Context mContext;

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setDbManager(Context context) {
        dbManager = new DbManager(context);
    }

    public void init(Activity activity, Context context) {
        mContext = context;
        list = new ArrayList<>();
        //list = (ArrayList<String>) dbManager.getAllPhones();
        model = Model.getInstance(activity.getApplication());
        model.loadPhones(new AbstractAPIListener(){
            @Override
            public void onPhonesLoaded(List<Phone> phones) {
                list = (ArrayList<Phone>) phones;
            }
        });
        mList.setValue(list);
        isInitialized = true;
    }
    public PhonesViewModel() {
        if (!isInitialized) {

        }
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

    public void deleteItem(Phone phone) {
        list.remove(phone);
        mList.postValue(list);

        //dbManager.deletePhone(phone);
        model.deletePhone(phone.getId(), new AbstractAPIListener() {
            @Override
            public void onPhoneDeleted(String mes) {
                Toast.makeText(mContext, "This phone is deleted!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteAll() {
        list = new ArrayList<>();
        mList.postValue(list);

        //dbManager.deleteAllPhones();
        model.deleteAllPhones(new AbstractAPIListener() {
            @Override
            public void onAllPhonesDeleted(String mes) {
                Toast.makeText(mContext, "All phones is deleted!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean addItem(Phone phone) {
        if (!list.contains(phone)) {
            list.add(phone);
            mList.postValue(list);

            //dbManager.insertPhone(phone);
            return true;
        } else return false;
    }

    public boolean changeItem(final Phone oldPhone, final Phone changedPhone) {
        if (!list.contains(changedPhone)) {
            int pos = list.indexOf(oldPhone);
            list.set(pos, changedPhone);
            mList.postValue(list);

            //dbManager.updatePhone(oldPhone, changedPhone);

            return true;
        } else return false;
    }
}