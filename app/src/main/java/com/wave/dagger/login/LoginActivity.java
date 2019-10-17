package com.wave.dagger.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.wave.dagger.R;
import com.wave.dagger.document.DocumentFragment;
import com.wave.dagger.document.UploadPhotoFragment;
import com.wave.dagger.friendship.FriendshipFragment;
import com.wave.dagger.mainpage.MainFragment;
import com.wave.dagger.mainpage.MainMVPContract;
import com.wave.dagger.mainpage.UpdateMemberFragment;
import com.wave.dagger.model.Member;
import com.wave.dagger.service.AuthServiceInterface;
import com.wave.dagger.service.AuthorizationService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class LoginActivity extends DaggerAppCompatActivity implements AuthServiceInterface.OnFinishedAuthService {

    @Inject
    LoginMVP.Presenter loginPresenter;

    @Inject
    AuthorizationService authorizationService;

    Member member;

    FragmentManager fm;
    public static String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    public static Bitmap bitMapToSend;
    public static Uri pathToSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fm = getSupportFragmentManager();

        //we check if token is present
        if (!authorizationService.checkIfTokenPresent(this, this)) {
            onFailure(new Throwable("Token was not present, so we initiate the Login Fragment."));
        }


    }

    //listens to AuthService Positive answer
    @Override
    public void onFinished(Member member) {
        MainMVPContract.View fragment = new MainFragment(member);
        fragment.setLoginActivity(this);
        this.member = member;
        fragment.setPrefs(authorizationService.getPrefs());

        fm.beginTransaction().add(R.id.fragment_container, (MainFragment) fragment).commitAllowingStateLoss();
        Log.e("TAG", "Token is valid!: " + true);
    }

    //Listens to AuthService Negative answer
    @Override
    public void onFailure(Throwable t) {
        Fragment fragment = new LoginFragment();
        fm.beginTransaction().add(R.id.fragment_container, fragment).commitAllowingStateLoss();
        Log.e("TAG", t.toString());
    }

    public void changeFragment(Fragment newFragment) {
        fm.beginTransaction().replace(R.id.fragment_container, newFragment).addToBackStack(null).commitAllowingStateLoss();
    }

    public void onEditProfileClicked(View view) {
        changeFragment(new UpdateMemberFragment());
    }

    public void onMyDocumentsPressed(View view) {
        changeFragment(new DocumentFragment());
    }

    public void onFastUploadPressed(View view) {
        changeFragment(new UploadPhotoFragment());
    }

    public void onMyFriendsListPressed(View view) {
        changeFragment(new FriendshipFragment());
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void openBackCamera() {
        dispatchTakePictureIntent();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("IO", "error occured while creating file");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.wave.dagger.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case 1: {
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap = MediaStore.Images.Media
                                .getBitmap(this.getContentResolver(), Uri.fromFile(file));
                        if (bitmap != null) {
                            bitMapToSend = bitmap;
                            Bitmap currentbitmap = bitmap;
                            currentbitmap = resize(currentbitmap, 400, 600);
                            ((ImageView) findViewById(R.id.uploadedImage)).setImageBitmap(currentbitmap);
                        }
                    }
                    break;
                }
            }

        } catch (Exception error) {
            error.printStackTrace();
        }
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

    public Bitmap getBitMapToSend() {
        return bitMapToSend;
    }

    public void setBitMapToSend(Bitmap bitMapToSend) {
        this.bitMapToSend = bitMapToSend;
    }




}

