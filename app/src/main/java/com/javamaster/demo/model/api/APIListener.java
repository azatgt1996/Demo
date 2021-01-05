package com.javamaster.demo.model.api;

import com.javamaster.demo.model.User;

import java.util.List;

public interface APIListener {
    void onLogin(User user);
    void onPhonesLoaded(List<String> phones);
}
