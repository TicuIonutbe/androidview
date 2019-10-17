package com.wave.dagger.di;

import com.wave.dagger.login.LoginModel;
import com.wave.dagger.mainpage.MainFragment;
import com.wave.dagger.mainpage.MainMVPContract;
import com.wave.dagger.mainpage.MainModel;
import com.wave.dagger.mainpage.MainPresenter;
import com.wave.dagger.service.AuthorizationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainPageModule {

    @Provides
    @Singleton
    static MainMVPContract.Presenter providesMainPresenter(MainModel mainModel) {
        return new MainPresenter(mainModel);
    }

    @Provides
    @Singleton
    static MainModel provideMainModel(Retrofit retrofit, AuthorizationService authorizationService) {
        return new MainModel(retrofit, authorizationService);
    }
}
