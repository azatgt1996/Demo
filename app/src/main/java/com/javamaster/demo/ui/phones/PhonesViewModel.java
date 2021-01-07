package com.javamaster.demo.ui.phones;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.javamaster.demo.db.DbManager;
import com.javamaster.demo.model.Model;
import com.javamaster.demo.model.Phone;
import com.javamaster.demo.model.api.AbstractAPIListener;

import java.util.ArrayList;
import java.util.Iterator;
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
                mList.setValue(list);
                isInitialized = true;
            }
        });
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

    public void deleteItem(final Phone phone) {

        model.deletePhone(phone.getId(), new AbstractAPIListener() {
            @Override
            public void onPhoneDeleted(String mes) {
                list.remove(phone);
                mList.setValue(list);

                //dbManager.deletePhone(phone);
                Toast.makeText(mContext, mes, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteAll() {
        model.deleteAllPhones(new AbstractAPIListener() {
            @Override
            public void onAllPhonesDeleted(String mes) {
                list = new ArrayList<>();
                mList.setValue(list);

                //dbManager.deleteAllPhones();
                Toast.makeText(mContext, mes, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean addItem(final Phone phone) {
        if (!Phone.isExisted(list, phone.getPhoneNumber())) {
            model.addPhone(phone.getPhoneNumber(), new AbstractAPIListener() {
                @Override
                public void onPhoneAdded(String mes, int id) {
                    phone.setId(id);
                    list.add(phone);
                    mList.setValue(list);

                    //dbManager.insertPhone(phone);
                    Toast.makeText(mContext, mes, Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        } else return false;
    }

    public boolean changeItem(final Phone changedPhone) {
        if (!Phone.isExisted(list, changedPhone.getPhoneNumber())) {

            model.updatePhone(changedPhone.getId(), changedPhone.getPhoneNumber(), new AbstractAPIListener() {
                @Override
                public void onPhoneUpdated(String mes) {
                    int pos = Phone.getIndById(list, changedPhone.getId());
                    if (pos != -1) {
                        list.set(pos, changedPhone);
                        mList.setValue(list);

                        //dbManager.updatePhone(oldPhone, changedPhone);
                        Toast.makeText(mContext, mes, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return true;
        } else return false;
    }
}