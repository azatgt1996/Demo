package com.javamaster.demo.model.address;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class District {

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

    public District(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<District> getDistricts(JSONArray jsonArray) throws JSONException {
        List<District> districts = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject p = jsonArray.getJSONObject(i);
            int id = p.getInt("id");
            String districtName = p.getString("name");
            District district = new District(id, districtName);
            districts.add(district);
        }

        return districts;
    }
}
