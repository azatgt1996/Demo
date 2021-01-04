package com.javamaster.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public static User getUser(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString("name");
        String login = jsonObject.getString("login");
        String token = jsonObject.getString("token");
        String email = jsonObject.getString("email");
        User user = new User(name, login, token, email);

        return user;
    }

    private String name;
    private String login;
    private String token;
    private String email;

    public User(String name, String login, String token, String email) {
        this.name = name;
        this.login = login;
        this.token = token;
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        if (obj != null && obj instanceof User) {
            User that = (User) obj;
            if (this.login.equalsIgnoreCase(that.login)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return this.name + "(" + this.login + ", " + this.email + ")";
    }
}
