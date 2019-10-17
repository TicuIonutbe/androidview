package com.wave.dagger.login;

import android.util.Log;

import com.wave.dagger.CardsAndFilesAPI.CardsAndFilesInterface;
import com.wave.dagger.model.JwtRequest;
import com.wave.dagger.model.JwtResponse;
import com.wave.dagger.model.Member;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginModel implements LoginMVP.Model {

    @Inject
    Retrofit retrofit;

    @Inject
    public LoginModel(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    //gets token
    @Override
    public void getToken(JwtRequest jwtRequest, final OnFinishedListener onFinishedListener) {

        CardsAndFilesInterface.LoginAPI service = retrofit.create(CardsAndFilesInterface.LoginAPI.class);

        Call<JwtResponse> call = service.authenticate(jwtRequest);

        call.enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                if (response.code() == 200) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onFailure("Credentials not ok!");
                }
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {
                onFinishedListener.onFailure("Credentials not ok!");
            }
        });
    }

    //gets the member from token
    @Override
    public void getMember(String token, final OnFinishedListener onFinishedListener) {
        CardsAndFilesInterface.LoginAPI service = retrofit.create(CardsAndFilesInterface.LoginAPI.class);

        Call<Member> call = service.getMemberFromToken("Bearer " + token);

        call.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if (response.code() == 200) {
                    onFinishedListener.onMemberResponse(response.body());
                } else {
                    onFinishedListener.onFailure("Credentials not ok!");
                }
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                onFinishedListener.onFailure("Server is not available, please try again later!");
            }
        });
    }

    @Override
    public void register(Member member, final OnFinishedListener onFinishedListener) {
        CardsAndFilesInterface.LoginAPI service = retrofit.create(CardsAndFilesInterface.LoginAPI.class);
        Call<String> call = service.registerMember(member);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    onFinishedListener.onRegisterResponse(response.body());
                } else {
                    onFinishedListener.onFailure("Credentials not ok!");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                onFinishedListener.onFailure("Server is not available, please try again later!");
            }
        });
    }

    @Override
    public void recovery(String email, final OnFinishedListener onFinishedListener) {
        CardsAndFilesInterface.LoginAPI service = retrofit.create(CardsAndFilesInterface.LoginAPI.class);

        Call<String> call = service.goRecovery(email);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                onFinishedListener.onEmailRecovery(response.code());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                onFinishedListener.onFailure("Server is not available, please try again later!");
            }
        });
    }
}
