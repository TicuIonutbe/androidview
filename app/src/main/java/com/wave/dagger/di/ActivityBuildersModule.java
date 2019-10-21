package com.wave.dagger.di;

import com.wave.dagger.document.DocumentFragment;
import com.wave.dagger.document.documentfeatures.DocumentUpdateFragment;
import com.wave.dagger.document.documentfeatures.UploadPhotoFragment;
import com.wave.dagger.friendship.FriendshipFragment;
import com.wave.dagger.root.LoginActivity;
import com.wave.dagger.login.LoginFragment;
import com.wave.dagger.mainpage.MainFragment;
import com.wave.dagger.mainpage.updatemember.UpdateMemberFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuildersModule {

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

    @ContributesAndroidInjector
    abstract DocumentFragment contributeDocumentFragment();

    @ContributesAndroidInjector
    abstract DocumentUpdateFragment contributeDocumentUpdateFragment();

    @ContributesAndroidInjector
    abstract FriendshipFragment contributeFriendshipFragment();

}
