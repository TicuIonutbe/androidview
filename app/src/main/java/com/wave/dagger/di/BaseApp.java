package com.wave.dagger.di;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApp extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).injectLoginActivity(getApplicationContext()).build();
    }
}
