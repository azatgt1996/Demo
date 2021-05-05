package com.javamaster.demo.model.address;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class City {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<City> getCities(JSONArray jsonArray) throws JSONException {
        List<City> cities = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject p = jsonArray.getJSONObject(i);
            int id = p.getInt("id");
            String cityName = p.getString("name");
            City city = new City(id, cityName);
            cities.add(city);
        }

        return cities;
    }
}
