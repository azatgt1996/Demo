package com.javamaster.demo.model.api;

public interface API {
    void login(String login, String password, APIListener listener);
    void register(String login, String name, String email, String password, int houseId, APIListener listener);
    void recovery(String login, String email, APIListener listener);
    void loadPhones(APIListener listener);
    void addPhone(String phoneNumber, APIListener listener);
    void deletePhone(int idPhone, APIListener listener);
    void deleteAllPhones(APIListener listener);
    void updatePhone(int idPhone, String phoneNumber, APIListener listener);

    void getDistricts(APIListener listener);
    void getCities(int districtId, APIListener listener);
    void getStreets(int districtId, int cityId, APIListener listener);
    void getHouses(int districtId, int cityId, int streetId, APIListener listener);

}
