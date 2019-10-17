package com.wave.dagger.friendship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wave.dagger.R;
import com.wave.dagger.login.LoginActivity;
import com.wave.dagger.model.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FriendshipAdapter extends RecyclerView.Adapter<FriendshipAdapter.MyViewHolder> {
    private ArrayList<Document> mDataset;


    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout relativeLayout;
        public TextView documentName, documentType, documentId, documentVisibility, documentLink;
        public ImageView documentSmallImage;

        public MyViewHolder(RelativeLayout v) {
            super(v);
            relativeLayout = v;
            documentName = v.findViewById(R.id.documentName);
            documentType = v.findViewById(R.id.documentType);
            documentId = v.findViewById(R.id.documentId);
            documentVisibility = v.findViewById(R.id.documentVisibility);
            documentSmallImage = v.findViewById(R.id.documentSmallImage);
            documentLink = v.findViewById(R.id.documentLink);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FriendshipAdapter(ArrayList<Document> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FriendshipAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_card, parent, false);

        context = v.getContext();
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Document currentDoc = mDataset.get(position);

        holder.documentName.setText(currentDoc.getName());
        holder.documentType.setText(currentDoc.getType());
        holder.documentId.setText(String.valueOf(currentDoc.getId()));
        holder.documentVisibility.setText(String.valueOf(currentDoc.isVisibility()));


        String path = currentDoc.getPath();
        int pos = path.lastIndexOf("/") + 1;


        Path path2 = new Path();

        Bitmap pic = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try (InputStream is = new URL("http://18.195.132.215:8080/images/" + path.substring(pos)).openStream()) {
            pic = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.documentSmallImage.setImageBitmap(LoginActivity.resize(pic, 200, 200));

        final Bitmap finalPic = pic;
        holder.documentLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                

            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}