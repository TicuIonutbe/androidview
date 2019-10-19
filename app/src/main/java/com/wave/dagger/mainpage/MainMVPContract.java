package com.wave.dagger.mainpage;

import android.content.SharedPreferences;

import com.wave.dagger.root.LoginActivity;
import com.wave.dagger.model.Member;

public interface MainMVPContract {

    interface View {
        void setLoginActivity(LoginActivity loginActivity);
        void setPrefs(SharedPreferences prefs);
        void updateView(Member member);
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
