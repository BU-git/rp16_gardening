package com.example.intratuin.dto;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Иван on 18.03.2016.
 */
public class Credentials {
    private String password;
    private String email;

    public Credentials() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}