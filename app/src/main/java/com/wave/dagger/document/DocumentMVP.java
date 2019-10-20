package com.wave.dagger.document;

import com.wave.dagger.document.documentfeatures.DocumentUpdateFragment;
import com.wave.dagger.document.documentfeatures.UploadPhotoFragment;
import com.wave.dagger.model.Document;

import java.util.ArrayList;

public interface DocumentMVP {
    interface View {

        void sendDocumentOnEmail(String email, Document doc);

        void updateView();
    }

    interface Presenter {
        void sendDocumentOnEmail(String email, Document doc);

        void uploadDocument(String fileNamem, String fileType);

        void setUploadPhotoFragment(UploadPhotoFragment uploadPhotoFragment);

        void setDocumentUpdateFragment(DocumentUpdateFragment documentUpdateFragment);

        void setDocumentFragment(DocumentFragment documentFragment);

        void deleteDocument(Document currentDoc);

        void getUpdatedDocumentListToMember();

        void updateDocument(Document document);
    }

    interface Model {

        interface DocumentListener {

            void onDeleteDocument(String deleteResponse);

            void onGetDocuments(ArrayList<Document> listDocuments);

            void onSendDocumentOnEmail(String answer);

            void onUploadDocument(String answer);

            void onUpdateDocument(String answer);

            void onFailure(String answer);
        }

        void deleteDocument(Document currentDocument, DocumentListener documentListener);

        void getUpdatedDocumentListToMember(DocumentListener documentListener);

        void sendDocumentOnEmail(Document document, String email, DocumentListener documentListener);

        void uploadDocument(String fileName, String fileType, DocumentListener documentListener);

        void updateDocument(Document document, DocumentListener documentListener);

    }
}
