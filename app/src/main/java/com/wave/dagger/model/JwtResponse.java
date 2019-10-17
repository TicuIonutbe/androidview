package com.wave.dagger.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    @SerializedName("token")
    @Expose
    private String jwttoken;

    public JwtResponse() {
        Log.e("cnst no argument: ", "");
    }

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }


    public String getJwttoken() {
        return jwttoken;
    }

    public void setJwttoken(String jwttoken) {
        this.jwttoken = jwttoken;
    }
}