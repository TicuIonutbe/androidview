package com.wave.dagger.document;

import android.content.Intent;
import android.widget.Toast;

import com.wave.dagger.R;
import com.wave.dagger.login.LoginActivity;
import com.wave.dagger.model.Document;

import java.util.List;

import javax.inject.Inject;

public class DocumentPresenter implements DocumentMVP.Presenter, DocumentMVP.Model.DocumentListener {

    private UploadPhotoFragment uploadPhotoFragment;
    private DocumentModel documentModel;

    @Inject
    public DocumentPresenter(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    @Override
    public void onDeleteDocument(String deleteResponse) {

    }

    @Override
    public void onGetDocuments(List<Document> listDocuments) {

    }

    @Override
    public void onSendDocumentOnEmail(String answer) {

    }

    @Override
    public void onUploadDocument(String answer) {
        Toast.makeText(uploadPhotoFragment.getActivity(), "Document uploaded with success!", Toast.LENGTH_SHORT).show();
        uploadPhotoFragment.startActivity(Intent.makeRestartActivityTask(uploadPhotoFragment.getActivity().getIntent().getComponent()));
    }

    @Override
    public void onUpdateDocument(String answer) {

    }

    @Override
    public void onFailure(String answer) {
        Toast.makeText(uploadPhotoFragment.getActivity(), answer, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uploadDocument(String fileName, String fileType) {
        documentModel.uploadDocument(fileName, fileType, this);
    }

    public UploadPhotoFragment getUploadPhotoFragment() {
        return uploadPhotoFragment;
    }

    @Override
    public void setUploadPhotoFragment(UploadPhotoFragment uploadPhotoFragment) {
        this.uploadPhotoFragment = uploadPhotoFragment;
    }
}
