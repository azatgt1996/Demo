package com.javamaster.demo.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Phone {

    public static List<Phone> getPhones(JSONArray jsonArray) throws JSONException {
        List<Phone> phones = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject p = jsonArray.getJSONObject(i);
            int id = p.getInt("id");
            String phoneNumber = p.getString("phoneNumber");
            Phone phone = new Phone(id, phoneNumber);
            phones.add(phone);
        }

        return phones;
    }

    public static List<String> getPhoneNumList(List<Phone> list) {
        List<String> phoneNumList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Phone p = list.get(i);
            String phoneNumber = p.phoneNumber;
            phoneNumList.add(phoneNumber);
        }

        return phoneNumList;
    }

    public Phone(int id, String phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    private int id;
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
