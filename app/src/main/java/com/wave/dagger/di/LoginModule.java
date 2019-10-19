package com.wave.dagger.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.wave.dagger.BuildConfig;
import com.wave.dagger.login.LoginMVP;
import com.wave.dagger.login.LoginModel;
import com.wave.dagger.login.LoginPresenter;
import com.wave.dagger.service.AuthorizationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
class LoginModule {
    private static final String APP_NAME = BuildConfig.APPLICATION_ID;

    @Provides
    @Singleton
    static LoginMVP.Presenter provideLoginPresenter(LoginModel model, AuthorizationService authorizationService) {
        return new LoginPresenter(model, authorizationService);
    }


    @Provides
    @Singleton
    static LoginModel provideLoginModel(Retrofit retrofit) {
        return new LoginModel(retrofit);
    }

    @Provides
    @Singleton
    static AuthorizationService provideAuthService(Retrofit retrofit, SharedPreferences sharedPreferences) {
        return new AuthorizationService(retrofit, sharedPreferences);
    }

    @Provides
    @Singleton
    static SharedPreferences provideSharedPrefs(Context context) {
        return context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    }

}
