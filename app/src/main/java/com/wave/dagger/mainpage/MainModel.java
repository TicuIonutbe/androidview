package com.wave.dagger.mainpage;

import android.util.Log;

import com.wave.dagger.CardsAndFilesAPI.CardsAndFilesInterface;
import com.wave.dagger.login.LoginMVP;
import com.wave.dagger.model.Member;
import com.wave.dagger.service.AuthorizationService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainModel implements MainMVPContract.Model {


    private Retrofit retrofit;
    AuthorizationService authorizationService;
    private String token;

    @Inject
    public MainModel(Retrofit retrofit, AuthorizationService authorizationService) {
        this.retrofit = retrofit;
        this.authorizationService = authorizationService;
        this.token = "Bearer " + authorizationService.getTokenFromPrefs();
    }

    @Override
    public void deleteMember(Member member, final MainMVPContract.Model.OnFinishListener onFinishedListener) {
        CardsAndFilesInterface.MemberProfile service = retrofit.create(CardsAndFilesInterface.MemberProfile.class);
        Call<String> call = service.deleteMember(token);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                onFinishedListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                onFinishedListener.onFailure("Server is not available at the moment!");
            }
        });
    }

    @Override
    public void updateMember(Member member, final OnFinishListener onFinishListener) {
        CardsAndFilesInterface.MemberProfile service = retrofit.create(CardsAndFilesInterface.MemberProfile.class);
        Call<Member> call = service.updateMember(token,member);

        call.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                onFinishListener.onUpdateMember(response.body());
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
            onFinishListener.onFailure("Server is not available at the moment, please try again later!");
            }
        });
    }
}
