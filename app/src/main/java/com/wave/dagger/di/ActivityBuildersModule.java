package com.wave.dagger.di;

import com.wave.dagger.document.UploadPhotoFragment;
import com.wave.dagger.login.LoginActivity;
import com.wave.dagger.login.LoginFragment;
import com.wave.dagger.mainpage.MainFragment;
import com.wave.dagger.mainpage.UpdateMemberFragment;
import com.wave.dagger.service.AuthorizationService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();

    @ContributesAndroidInjector
    abstract MainFragment contributeMainFragment();

    @ContributesAndroidInjector
    abstract UpdateMemberFragment contributeUpdateMemberFragment();

    @ContributesAndroidInjector
    abstract UploadPhotoFragment contributeUploadPhotoFragment();

}
