package com.wave.dagger.document;

import com.wave.dagger.document.documentupload.UploadPhotoFragment;
import com.wave.dagger.model.Document;

import java.util.List;

public interface DocumentMVP {
    interface View {

    }

    interface Presenter {

        void uploadDocument(String fileNamem,String fileType);
        void setUploadPhotoFragment(UploadPhotoFragment uploadPhotoFragment);
    }

    interface Model {

        interface DocumentListener{

            void onDeleteDocument(String deleteResponse);
            void onGetDocuments(List<Document> listDocuments);
            void onSendDocumentOnEmail(String answer);
            void onUploadDocument(String answer);
            void onUpdateDocument(String answer);
            void onFailure(String answer);
        }

        void deleteDocument(int documentId, DocumentListener documentListener);

        void getDocuments();

        void sendDocumentOnEmail(int documentId);

        void uploadDocument(String fileName,String fileType,DocumentListener documentListener);

        void updateDocument(Document document);

    }
}
