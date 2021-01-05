package com.javamaster.demo.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Phone {

    public static List<String> getPhones(JSONArray jsonArray) throws JSONException {
        List<String> phones = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject p = jsonArray.getJSONObject(i);
            String phone = p.getString("phoneNumber");
            phones.add(phone);
        }

        return phones;
    }
}
