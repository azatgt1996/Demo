package com.javamaster.demo.model.api;

import com.javamaster.demo.model.Phone;
import com.javamaster.demo.model.User;
import com.javamaster.demo.model.address.City;
import com.javamaster.demo.model.address.District;
import com.javamaster.demo.model.address.House;
import com.javamaster.demo.model.address.Street;

import java.util.List;

public class AbstractAPIListener implements APIListener {
    @Override
    public void onLogin(User user) {

    }

    @Override
    public void onRegistered(String mes) {

    }

    @Override
    public void onRecovered(String mes) {

    }

    @Override
    public void onPhonesLoaded(List<Phone> phones) {

    }

    @Override
    public void onPhoneAdded(String mes, int id) {

    }

    @Override
    public void onPhoneDeleted(String mes) {

    }

    @Override
    public void onAllPhonesDeleted(String mes) {

    }

    @Override
    public void onPhoneUpdated(String mes) {

    }

    @Override
    public void onFailed(String mes) {

    }

    @Override
    public void onDistrictsLoaded(List<District> districts) {

    }

    @Override
    public void onCitiesLoaded(List<City> cities) {

    }

    @Override
    public void onStreetsLoaded(List<Street> streets) {

    }

    @Override
    public void onHousesLoaded(List<House> houses) {

    }
}
