package com.javamaster.demo.ui.phones;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
    private ArrayList<Phone> list;
    private Model model;
    private Context mContext;
    private CustomFragmentListener customFragmentListener;

    public void init(Activity activity, Context context) {
        mContext = context;
        list = new ArrayList<>();
//            dbManager = new DbManager(context);
//            list = (ArrayList<Phone>) dbManager.getAllPhones();
        model = Model.getInstance(activity.getApplication());
        if (model.isOnline(mContext)) {
            model.loadPhones(new AbstractAPIListener(){
                @Override
                public void onPhonesLoaded(List<Phone> phones) {
                    list = (ArrayList<Phone>) phones;
                    mList.setValue(list);
                }
            });
        } else {
            Toast.makeText(mContext, "No internet connection!", Toast.LENGTH_LONG).show();
        }
    }

    public PhonesViewModel() {
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

                    //dbManager.deletePhone(phone);
                    Toast.makeText(mContext, mes, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(mContext, "No internet connection!", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteAll() {
        if (model.isOnline(mContext)) {
            model.deleteAllPhones(new AbstractAPIListener() {
                @Override
                public void onAllPhonesDeleted(String mes) {
                    list = new ArrayList<>();
                    mList.setValue(list);

                    //dbManager.deleteAllPhones();
                    Toast.makeText(mContext, mes, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(mContext, "No internet connection!", Toast.LENGTH_LONG).show();
        }
    }

    public void addItem(final Phone phone) {
        if (!Phone.isExisted(list, phone.getPhoneNumber())) {
            if (model.isOnline(mContext)) {
                model.addPhone(phone.getPhoneNumber(), new AbstractAPIListener() {
                    @Override
                    public void onPhoneAdded(String mes, int id) {
                        phone.setId(id);
                        list.add(phone);
                        mList.setValue(list);

                        //dbManager.insertPhone(phone);
                        Toast.makeText(mContext, mes, Toast.LENGTH_SHORT).show();
                        customFragmentListener.onBackPressed();
                    }
                });
            } else {
                Toast.makeText(mContext, "No internet connection!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(mContext, "This phone exists!", Toast.LENGTH_LONG).show();
        }
    }

    public void changeItem(final Phone changedPhone) {
        if (!Phone.isExisted(list, changedPhone.getPhoneNumber())) {

            if (model.isOnline(mContext)) {
                model.updatePhone(changedPhone.getId(), changedPhone.getPhoneNumber(), new AbstractAPIListener() {
                    @Override
                    public void onPhoneUpdated(String mes) {
                        int pos = Phone.getIndById(list, changedPhone.getId());
                        if (pos != -1) {
                            list.set(pos, new Phone(changedPhone.getId(), changedPhone.getPhoneNumber()));
                            mList.setValue(list);

                            //dbManager.updatePhone(oldPhone, changedPhone);
                            Toast.makeText(mContext, mes, Toast.LENGTH_SHORT).show();
                            customFragmentListener.onCommitted();
                        }
                    }
                });
            } else {
                Toast.makeText(mContext, "No internet connection!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(mContext, "This phone exists!", Toast.LENGTH_LONG).show();
        }
    }

    public void setListener(CustomFragmentListener listener){
        customFragmentListener = listener;
    }
}