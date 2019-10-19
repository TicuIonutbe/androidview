package com.wave.dagger.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuildersModule.class,
        AppModule.class,
        LoginModule.class,
        MainPageModule.class,
        DocumentModule.class
})
public interface AppComponent extends AndroidInjector<BaseApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder injectLoginActivity(Context context);

        AppComponent build();
    }
}
