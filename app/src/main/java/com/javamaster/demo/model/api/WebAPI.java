package com.javamaster.demo.model.api;


import android.app.Application;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.javamaster.demo.R;
import com.javamaster.demo.Util;
import com.javamaster.demo.model.Phone;
import com.javamaster.demo.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebAPI implements API {

    private static String BASE_URL;
    private final Application mApplication;
    private final Util mUtil;

    private RequestQueue mRequestQueue;

    public WebAPI(Application application) {
        mApplication = application;
        mRequestQueue = Volley.newRequestQueue(application);
        BASE_URL = mApplication.getString(R.string.base_url);
        mUtil = Util.getInstance(application);
    }

    public void login(String login, String password, final APIListener listener) {
        String url = BASE_URL + "auth";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("login", login);
            jsonObject.put("password", password);

            Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        User user = User.getUser(response);
                        listener.onLogin(user);
                    }
                    catch (JSONException e) {
                        Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_LONG).show();
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication, "Error response", Toast.LENGTH_LONG).show();
                }
            };

//            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, successListener, errorListener);
//            mRequestQueue.add(request);
            // for testing
            {
                JSONObject response = new JSONObject();
                response.put("name", "Maxim Petrov");
                response.put("login", "azatgt");
                response.put("email", "azat12@mail.com");
                response.put("token", "sf45d4f3sert.1842312dfg12ki.ui843x21w");

                User user = User.getUser(response);
                listener.onLogin(user);
            }
        }
        catch (JSONException e) {
            Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void loadPhones(final APIListener listener) {
        String url = BASE_URL + "phones";

        try {
            Response.Listener<JSONArray> successListener = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        List<String> phones = Phone.getPhones(response);
                        if (listener != null) {
                            listener.onPhonesLoaded(phones);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_LONG).show();
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication, "Error response", Toast.LENGTH_LONG).show();
                }
            };

//            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, successListener, errorListener) {
//                @Override
//                public Map<String, String> getHeaders() {
//                    Map<String, String> headers = new HashMap<>();
//                    headers.put("Content-Type", "application/json");
//                    headers.put("Accept", "application/json");
//                    headers.put("Authorization", "Bearer " + mUtil.getUserInfo().getToken());
//                    return headers;
//                }
//            };
//            mRequestQueue.add(request);
            // for testing
            {
                JSONArray jsonArray = new JSONArray();
                JSONObject p1 = new JSONObject();
                p1.put("id", 1);
                p1.put("phoneNumber", "255 96 33");
                JSONObject p2 = new JSONObject();
                p2.put("id", 2);
                p2.put("phoneNumber", "276-88-99");
                JSONObject p3 = new JSONObject();
                p3.put("id", 3);
                p3.put("phoneNumber", "8 908 999 66 33");

                jsonArray.put(p1);
                jsonArray.put(p2);
                jsonArray.put(p3);
                List<String> phones = Phone.getPhones(jsonArray);
                if (listener != null) {
                    listener.onPhonesLoaded(phones);
                }
            }
        } catch (JSONException e) {
            Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_LONG).show();
        }
    }
}
