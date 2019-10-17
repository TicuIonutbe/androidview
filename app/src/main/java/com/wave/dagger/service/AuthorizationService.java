package com.wave.dagger.service;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.wave.dagger.CardsAndFilesAPI.CardsAndFilesInterface;
import com.wave.dagger.model.Member;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthorizationService implements AuthServiceInterface {
    public static final String TOKEN = "tokenTicu";


    Retrofit retrofit;


    SharedPreferences prefs;

    @Inject
    public AuthorizationService(Retrofit retrofit, SharedPreferences prefs) {
        this.retrofit = retrofit;
        this.prefs = prefs;
    }

    @Override
    public boolean checkIfTokenPresent(Activity activity, OnFinishedAuthService listener) {
        String token = prefs.getString(TOKEN, null);

        if (token != null) {
            Log.e("AUTH SERVICE", "TOKEN FOUND NOW CHECKING IF VALID.");
            checkIfTokenValid(token, listener);
            return true;
        }
        Log.e("AUTH SERVICE", "TOKEN NOT FOUND, RETURNING");
        return false;
    }

    @Override
    public void checkIfTokenValid(String token, final AuthServiceInterface.OnFinishedAuthService listener) {

        CardsAndFilesInterface.LoginAPI service = retrofit.create(CardsAndFilesInterface.LoginAPI.class);
        Call<Member> call = service.getMemberFromToken("Bearer " + token);
        call.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                //token found we change fragments in LoginActivity class @onFinished()
                if (response.code() == 200) {
                    listener.onFinished(response.body());
                } else {
                    //server found but No member as result--->failed next step LoginActivity @Onfailure
                    listener.onFailure(new Throwable("Get request made but answer was not 200! It was: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                //Request failed! next step --- > LoginActivity @OnFailure
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void saveToken(String token) {
        prefs.edit().putString(TOKEN, token).apply();

    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public String getTokenFromPrefs() {
        return prefs.getString(TOKEN, null);
    }
}