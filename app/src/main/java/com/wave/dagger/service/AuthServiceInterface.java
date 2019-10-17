package com.wave.dagger.service;

import android.app.Activity;

import com.wave.dagger.login.LoginMVP;
import com.wave.dagger.model.JwtResponse;
import com.wave.dagger.model.Member;

public interface AuthServiceInterface {


    boolean checkIfTokenPresent(Activity activity, OnFinishedAuthService listener);


    void checkIfTokenValid(String token, OnFinishedAuthService listener);

    void saveToken(String token);

    interface OnFinishedAuthService {

        void onFinished(Member member);

        void onFailure(Throwable t);
    }
}
