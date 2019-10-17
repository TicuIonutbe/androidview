package com.wave.dagger.di;

import com.wave.dagger.document.DocumentMVP;
import com.wave.dagger.document.DocumentModel;
import com.wave.dagger.document.DocumentPresenter;
import com.wave.dagger.mainpage.MainMVPContract;
import com.wave.dagger.mainpage.MainModel;
import com.wave.dagger.mainpage.MainPresenter;
import com.wave.dagger.service.AuthorizationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class DocumentModule {

    @Provides
    @Singleton
    static DocumentMVP.Presenter providesMainPresenter(DocumentModel documentModel) {
        return new DocumentPresenter(documentModel);
    }

    @Provides
    @Singleton
    static DocumentModel provideDocumentModel(Retrofit retrofit, AuthorizationService authorizationService) {
        return new DocumentModel(retrofit, authorizationService);
    }
}
