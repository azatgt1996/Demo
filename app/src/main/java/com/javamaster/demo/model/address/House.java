package com.javamaster.demo.model.address;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class House {

    private int id;
    private String number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public House(int id, String name) {
        this.id = id;
        this.number = name;
    }

    @Override
    public String toString() {
        return number;
    }

    public static List<House> getHouses(JSONArray jsonArray) throws JSONException {
        List<House> houses = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject p = jsonArray.getJSONObject(i);
            int id = p.getInt("id");
            String houseNumber = p.getString("number");
            House house = new House(id, houseNumber);
            houses.add(house);
        }

        return houses;
    }
}
