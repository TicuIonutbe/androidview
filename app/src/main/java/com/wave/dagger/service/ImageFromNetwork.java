package com.wave.dagger.service;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.wave.dagger.model.Document;

public class ImageFromNetwork extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private String imageName;
    private Document document;

    public ImageFromNetwork(ImageView bmImage, String imageName,Document document) {
        this.bmImage = bmImage;
        this.imageName = imageName;
        this.document = document;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String website = "http://18.195.132.215:8080/images/";
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(website + imageName).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        document.setBitmap(mIcon11);
        return mIcon11;
    }
    @Override
    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(FileImageService.resize(result, 500, 500));
    }


}