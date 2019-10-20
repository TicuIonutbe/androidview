package com.wave.dagger.document;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wave.dagger.R;
import com.wave.dagger.model.Document;
import com.wave.dagger.root.LoginActivity;
import com.wave.dagger.service.FileImageService;
import com.wave.dagger.service.ImageFromNetwork;

import java.io.File;
import java.util.ArrayList;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.MyViewHolder> {
    private ArrayList<Document> documentList;
    private ImageFromNetwork imageFromNetwork;
    private DocumentFragment documentFragment;

    // Provide a suitable constructor (depends on the kind of dataset)
    public DocumentAdapter(ArrayList<Document> documentList,DocumentFragment documentFragment) {
        this.documentList = documentList;
        this.documentFragment = documentFragment;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public DocumentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_card, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Document currentDoc = documentList.get(position);

        imageFromNetwork = new ImageFromNetwork(holder.documentImage, currentDoc.getPath().substring(27), currentDoc);
        imageFromNetwork.execute();

        holder.documentName.setText(currentDoc.getName());
        holder.documentVisibility.setText(String.valueOf(currentDoc.isVisibility()));
        holder.documentType.setText(currentDoc.getType());
        holder.documentId.setText(String.valueOf(currentDoc.getId()));
        holder.documentViewLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                File checkFile = new File("/storage/emulated/0/Pictures/" + currentDoc.getPath().substring(27));
                String path;
                if (!checkFile.exists()) {
                    path = FileImageService.saveToInternalStorage(currentDoc.getBitmap(),currentDoc.getPath().substring(27));
                } else {
                    path = "/storage/emulated/0/Pictures/" + currentDoc.getPath().substring(27);
                }
                Uri myUri = Uri.parse(path);

                String newPath = FileImageService.contentToFile(myUri);

                intent.setDataAndType(Uri.parse(newPath), "image/*");
                LoginActivity.getContext().startActivity(intent);

            }
        });
        holder.documentDeleteLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentFragment.deleteDocument(currentDoc);
            }
        });

        holder.documentSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentFragment.dialogBuilder(currentDoc);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return documentList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView documentName, documentId, documentType, documentVisibility, documentDeleteLink, documentViewLink,
        documentSendEmail;
        private ImageView documentImage;

        public MyViewHolder(RelativeLayout v) {
            super(v);
            documentName = v.findViewById(R.id.documentName);
            documentId = v.findViewById(R.id.documentId);
            documentType = v.findViewById(R.id.documentType);
            documentVisibility = v.findViewById(R.id.documentVisibility);
            documentImage = v.findViewById(R.id.documentSmallImage);
            documentDeleteLink = v.findViewById(R.id.documentDelete);
            documentViewLink = v.findViewById(R.id.documentLink);
            documentSendEmail = v.findViewById(R.id.documentEmailLink);
        }
    }
}