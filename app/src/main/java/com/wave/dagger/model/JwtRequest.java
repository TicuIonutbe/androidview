package com.wave.dagger.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


//Class used to send http request with user and password
public class JwtRequest {

    private static final long serialVersionUID = 5926468583005150707L;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;


    //need default constructor for JSON Parsing
    public JwtRequest() {

    }

    public JwtRequest(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "JwtRequest{" + "email=" + email + ", password=" + password + '}';
    }

}