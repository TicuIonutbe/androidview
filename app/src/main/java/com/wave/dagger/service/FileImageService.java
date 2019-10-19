package com.wave.dagger.service;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.wave.dagger.root.LoginActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileImageService {

    private static LoginActivity activity;
    private static FileImageService fileImageService;

    private FileImageService(LoginActivity activity) {
        FileImageService.activity = activity;
    }


    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        LoginActivity.mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }


    public static String saveToInternalStorage(Bitmap bitmapImage, String name) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/req_images");
        myDir.mkdirs();
        File file = new File(myDir, name);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return file.getAbsolutePath().toString();
    }

    public static FileImageService getFileImageService(LoginActivity loginActivity) {
        if (fileImageService == null) {
            fileImageService = new FileImageService(loginActivity);
        }

        return fileImageService;
    }

    public static void deleteAllFiles() {

        File dir = new File(Environment.getExternalStorageDirectory() + "/req_images");
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }

    public static boolean deleteFile(String fname) {
        String root = Environment.getExternalStorageDirectory() + "/req_images/";
        File fdelete = new File(root + fname);
        if (fdelete.exists()) {
            return fdelete.delete();
        }

        return false;
    }

    public static String contentToFile(Uri uri) {
        String filePath = "";
        if (uri != null && "content".equals(uri.getScheme())) {
            Cursor cursor = LoginActivity.getContext().getContentResolver().query(uri, new String[]{android.provider.MediaStore.Images.ImageColumns.DATA}, null, null, null);
            cursor.moveToFirst();
            filePath = cursor.getString(0);
            cursor.close();
        } else {
            filePath = uri.getPath();
        }


        return filePath;
    }
}
