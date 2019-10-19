package com.wave.dagger.document;

import android.graphics.Bitmap;

import com.wave.dagger.CardsAndFilesAPI.CardsAndFilesInterface;
import com.wave.dagger.root.LoginActivity;
import com.wave.dagger.model.Document;
import com.wave.dagger.service.AuthorizationService;
import com.wave.dagger.service.FileImageService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DocumentModel implements DocumentMVP.Model {

    private Retrofit retrofit;
    private AuthorizationService authorizationService;
    private String token;

    @Inject
    public DocumentModel(Retrofit retrofit, AuthorizationService authorizationService) {
        this.retrofit = retrofit;
        this.authorizationService = authorizationService;
        this.token = "Bearer " + authorizationService.getTokenFromPrefs();
    }


    @Override
    public void deleteDocument(final Document currentDocument, final DocumentListener documentListener) {
        CardsAndFilesInterface.DocumentAPI service = retrofit.create(CardsAndFilesInterface.DocumentAPI.class);
        Call<String> call = service.deleteDocument("Bearer " + authorizationService.getTokenFromPrefs(), currentDocument.getId());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    documentListener.onDeleteDocument(response.body());
                    FileImageService.deleteFile(currentDocument.getPath().substring(27));
                    LoginActivity.getMember().getDocumentList().remove(currentDocument);
                } else {documentListener.onFailure("Something went wrong, try again later!");}
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                    documentListener.onFailure("Server is not disponible, try again later!");
            }
        });

    }

    @Override
    public void getDocuments() {

    }

    @Override
    public void sendDocumentOnEmail(int documentId) {

    }

    @Override
    public void uploadDocument(String fileName, String fileType, final DocumentListener documentListener) {

        CardsAndFilesInterface.DocumentAPI service = retrofit.create(CardsAndFilesInterface.DocumentAPI.class);

        File file = new File(LoginActivity.mCurrentPhotoPath);
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LoginActivity.bitMapToSend.compress(Bitmap.CompressFormat.JPEG, 40, os);
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);
        Call<String> call = service.uploadDocument(token, part, fileName, fileType);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                documentListener.onUploadDocument(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                documentListener.onFailure("Server is not available at the moment!");
            }
        });


    }

    @Override
    public void updateDocument(Document document) {

    }
}
