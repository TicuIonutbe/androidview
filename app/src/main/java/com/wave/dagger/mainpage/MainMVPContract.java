package com.wave.dagger.mainpage;

import android.content.SharedPreferences;

import com.wave.dagger.login.LoginActivity;
import com.wave.dagger.login.LoginMVP;
import com.wave.dagger.model.Member;

import okhttp3.ResponseBody;

public interface MainMVPContract {

    interface View {
        public void setLoginActivity(LoginActivity loginActivity);
        public void setPrefs(SharedPreferences prefs);
        public void updateView(Member member);
    }

    interface Presenter {
        void setView(MainFragment mainFragment);
        void deleteMember(Member member);
        void updateMember(Member member);

    }

    interface Model {

        interface OnFinishListener {
            void onSuccess(Object responseBody);
            void onFailure(String failureString);
            void onUpdateMember(Member member);
        }


        void deleteMember(Member member, MainMVPContract.Model.OnFinishListener onFinishedListener);

        void updateMember(Member member, MainMVPContract.Model.OnFinishListener onFinishListener);
    }
}
