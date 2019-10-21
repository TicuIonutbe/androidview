package com.wave.dagger.di;

import com.wave.dagger.friendship.FriendshipMVP;
import com.wave.dagger.friendship.FriendshipModel;
import com.wave.dagger.friendship.FriendshipPresenter;
import com.wave.dagger.service.AuthorizationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class FriendshipModule {


    @Provides
    @Singleton
    static FriendshipMVP.Presenter providesFriendshipPresenter(FriendshipModel friendshipModel) {
        return new FriendshipPresenter(friendshipModel);
    }

    @Provides
    @Singleton
    static FriendshipModel providesFriendshipModel(Retrofit retrofit, AuthorizationService authorizationService) {
        return new FriendshipModel(retrofit, authorizationService);
    }
}
