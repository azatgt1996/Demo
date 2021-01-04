package com.javamaster.demo.model.api;


import android.app.Application;
import android.content.res.Resources;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.javamaster.demo.R;
import com.javamaster.demo.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class WebAPI implements API {

    private static String BASE_URL;
    private final Application mApplication;

    private RequestQueue mRequestQueue;

    public WebAPI(Application application) {
        mApplication = application;
        mRequestQueue = Volley.newRequestQueue(application);
        BASE_URL = mApplication.getString(R.string.base_url);
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

            //JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, successListener, errorListener);
            //mRequestQueue.add(request);

            // for testing
            JSONObject response = new JSONObject();
            response.put("name", "Azat Gal");
            response.put("login", "azatgt");
            response.put("email", "azat12@mail.com");
            response.put("token", "sf45d4f3sert.1842312dfg12ki.ui843x21w");

            User user = User.getUser(response);
            listener.onLogin(user);
        }
        catch (JSONException e) {
            Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_LONG).show();
        }

    }
}
