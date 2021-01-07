package com.javamaster.demo.model.api;

import com.javamaster.demo.model.Phone;
import com.javamaster.demo.model.User;

import java.util.List;

public interface APIListener {
    void onLogin(User user);
    void onRegistered(String mes);
    void onRecovered(String mes);
    void onPhonesLoaded(List<Phone> phones);
    void onPhoneAdded(String mes, int id);
    void onPhoneDeleted(String mes);
    void onAllPhonesDeleted(String mes);
    void onPhoneUpdated(String mes);
}
