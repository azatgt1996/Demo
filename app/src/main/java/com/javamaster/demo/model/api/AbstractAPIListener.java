package com.javamaster.demo.model.api;

import com.javamaster.demo.model.User;

import java.util.List;

public class AbstractAPIListener implements APIListener {
    @Override
    public void onLogin(User user) {

    }

    @Override
    public void onPhonesLoaded(List<String> phones) {

    }
}
