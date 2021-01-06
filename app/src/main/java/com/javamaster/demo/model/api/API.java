package com.javamaster.demo.model.api;

public interface API {
    void login(String login, String password, APIListener listener);
    void loadPhones(APIListener listener);
    void addPhone(String phoneNumber, APIListener listener);
    void deletePhone(int idPhone, APIListener listener);
    void deleteAllPhones(APIListener listener);
    void updatePhone(int idPhone, String phoneNumber, APIListener listener);

}
