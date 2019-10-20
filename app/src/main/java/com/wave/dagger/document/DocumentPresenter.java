package com.wave.dagger.document;

import android.content.Intent;
import android.widget.Toast;

import com.wave.dagger.document.documentfeatures.DocumentUpdateFragment;
import com.wave.dagger.document.documentfeatures.UploadPhotoFragment;
import com.wave.dagger.model.Document;
import com.wave.dagger.root.LoginActivity;

import java.util.ArrayList;

import javax.inject.Inject;

public class DocumentPresenter implements DocumentMVP.Presenter, DocumentMVP.Model.DocumentListener {
    @Inject
    public UploadPhotoFragment uploadPhotoFragment;
    private DocumentModel documentModel;
    private DocumentFragment documentFragment;

    public DocumentUpdateFragment documentUpdateFragment;

    @Inject
    public DocumentPresenter(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    @Override
    public void onDeleteDocument(String deleteResponse) {
            Toast.makeText(LoginActivity.getContext(),deleteResponse,Toast.LENGTH_SHORT).show();
            documentFragment.restartFragment();
    }

    @Override
    public void onGetDocuments(ArrayList<Document> listDocuments) {
        LoginActivity.getMember().setDocumentList(listDocuments);
        documentFragment.updateView();

    }

    @Override
    public void onSendDocumentOnEmail(String answer) {
        Toast.makeText(uploadPhotoFragment.getActivity(), answer, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadDocument(String answer) {
        Toast.makeText(uploadPhotoFragment.getActivity(), "Document uploaded with success!", Toast.LENGTH_SHORT).show();
        uploadPhotoFragment.startActivity(Intent.makeRestartActivityTask(uploadPhotoFragment.getActivity().getIntent().getComponent()));
        LoginActivity.pictureMade = false;
    }

    @Override
    public void onUpdateDocument(String answer) {
        Toast.makeText(documentFragment.getActivity(), answer, Toast.LENGTH_SHORT).show();
        documentUpdateFragment.startActivity(Intent.makeRestartActivityTask(documentUpdateFragment.getActivity().getIntent().getComponent()));
    }

    @Override
    public void onFailure(String answer) {
        Toast.makeText(documentFragment.getActivity(), answer, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendDocumentOnEmail(String email, Document doc) {
        documentModel.sendDocumentOnEmail(doc,email,this);
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

    @Override
    public void setDocumentUpdateFragment(DocumentUpdateFragment documentUpdateFragment) {
        this.documentUpdateFragment = documentUpdateFragment;
    }

    @Override
    public void deleteDocument(Document currentDoc) {
        documentModel.deleteDocument(currentDoc,this);
    }

    @Override
    public void getUpdatedDocumentListToMember() {
       documentModel.getUpdatedDocumentListToMember(this);

    }

    @Override
    public void updateDocument(Document document) {
        documentModel.updateDocument(document, this);
    }

    @Override
    public void setDocumentFragment(DocumentFragment documentFragment) {
        this.documentFragment = documentFragment;
    }
}
