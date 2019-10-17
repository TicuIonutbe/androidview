package com.wave.dagger.login;

import android.view.View;

import com.wave.dagger.CardsAndFilesAPI.CardsAndFilesInterface;
import com.wave.dagger.model.JwtRequest;
import com.wave.dagger.model.JwtResponse;
import com.wave.dagger.model.Member;

public interface LoginMVP {

    interface View {
        public void onClick(android.view.View v);

        public String getEmail();

        public String getPassword();

        public void updateToken(String token);

    }

    interface Presenter {

        void authenticate();

        void setView(LoginFragment view);

        void register(Member member);

        void goRecovery(String email);


    }

    interface Model {

        interface OnFinishedListener {
            void onFinished(JwtResponse jwtResponse);

            void onMemberResponse(Member member);

            void onRegisterResponse(String answer);

            void onEmailRecovery(int httpCode);

            void onFailure(String answer);
        }

        void getToken(JwtRequest jwtRequest, OnFinishedListener onFinishedListener);

        void getMember(String token, OnFinishedListener onFinishedListener);

        void register(Member member, OnFinishedListener onFinishedListener);

        void recovery(String email,OnFinishedListener onFinishedListener);
    }
}
