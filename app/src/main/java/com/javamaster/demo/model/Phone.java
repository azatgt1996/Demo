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

    public static boolean isExisted(List<Phone> list, String phoneNumber) {
        for (int i = 0; i < list.size(); i++) {
            Phone p = list.get(i);
            if (p.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }

        return false;
    }

    public static int getIndById(List<Phone> list, int phoneId) {
        for (int i = 0; i < list.size(); i++) {
            Phone p = list.get(i);
            if (p.getId() == phoneId) {
                return i;
            }
        }
        return -1;
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

    public boolean equals(Object o) {
        if (!(o instanceof Phone)) {
            return false;
        }
        Phone other = (Phone) o;
        return phoneNumber.equals(other.phoneNumber) && id ==other.id;
    }

    public int hashCode() {
        return phoneNumber.hashCode();
    }
}
