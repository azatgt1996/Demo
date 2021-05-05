package com.javamaster.demo.model.api;

import com.javamaster.demo.model.Phone;
import com.javamaster.demo.model.User;
import com.javamaster.demo.model.address.City;
import com.javamaster.demo.model.address.District;
import com.javamaster.demo.model.address.House;
import com.javamaster.demo.model.address.Street;

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
    void onFailed(String mes);

    void onDistrictsLoaded(List<District> districts);
    void onCitiesLoaded(List<City> cities);
    void onStreetsLoaded(List<Street> streets);
    void onHousesLoaded(List<House> houses);
}
