package com.javamaster.demo.model.address;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Street {

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

    public Street(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<Street> getStreets(JSONArray jsonArray) throws JSONException {
        List<Street> streets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject p = jsonArray.getJSONObject(i);
            int id = p.getInt("id");
            String streetName = p.getString("name");
            Street street = new Street(id, streetName);
            streets.add(street);
        }

        return streets;
    }
}
