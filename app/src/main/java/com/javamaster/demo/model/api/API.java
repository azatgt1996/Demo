package com.javamaster.demo.model.api;

public interface API {
    void login(String login, String password, APIListener listener);
    void loadPhones(APIListener listener );
}
