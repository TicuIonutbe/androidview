package com.wave.dagger.root;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.wave.dagger.R;
import com.wave.dagger.document.DocumentFragment;
import com.wave.dagger.document.documentfeatures.UploadPhotoFragment;
import com.wave.dagger.friendship.FriendshipFragment;
import com.wave.dagger.login.LoginFragment;
import com.wave.dagger.login.LoginMVP;
import com.wave.dagger.mainpage.MainFragment;
import com.wave.dagger.mainpage.MainMVPContract;
import com.wave.dagger.mainpage.updatemember.UpdateMemberFragment;
import com.wave.dagger.model.Member;
import com.wave.dagger.service.AuthServiceInterface;
import com.wave.dagger.service.AuthorizationService;
import com.wave.dagger.service.FileImageService;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class LoginActivity extends DaggerAppCompatActivity implements AuthServiceInterface.OnFinishedAuthService {

    @Inject
    LoginMVP.Presenter loginPresenter;

    @Inject
    AuthorizationService authorizationService;

    private static Context context;

    private static Member member;

    static FragmentManager fm;
    public static String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    public static Bitmap bitMapToSend;
    public static Uri pathToSend;
    public static boolean pictureMade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fm = getSupportFragmentManager();

        //we check if token is present
        if (!authorizationService.checkIfTokenPresent(this, this)) {
            onFailure(new Throwable("Token was not present, so we initiate the Login Fragment."));
        }

        context = this;

    }

    //listens to AuthService Positive answer
    @Override
    public void onFinished(Member member) {
        MainMVPContract.View fragment = new MainFragment(member);
        fragment.setLoginActivity(this);
        this.member = member;
        fragment.setPrefs(authorizationService.getPrefs());

        fm.beginTransaction().replace(R.id.fragment_container, (MainFragment) fragment).commitAllowingStateLoss();
        Log.e("TAGG", "Token is valid!: " + true);
    }

    //Listens to AuthService Negative answer
    @Override
    public void onFailure(Throwable t) {
        Fragment fragment = new LoginFragment();
        fm.beginTransaction().replace(R.id.fragment_container, fragment).commitAllowingStateLoss();
        Log.e("TAG", t.toString());
    }

    public static void changeFragment(Fragment newFragment) {
        fm.beginTransaction().replace(R.id.fragment_container, newFragment).addToBackStack(null).commitAllowingStateLoss();
    }

    public void onEditProfileClicked(View view) {
        changeFragment(new UpdateMemberFragment());
    }

    public void onMyDocumentsPressed(View view) {
        ActivityCompat.requestPermissions((LoginActivity)LoginActivity.getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        if (LoginActivity.getContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v("WRITE PERM","Permission is granted");
            //File write logic here
            changeFragment(new DocumentFragment());
        } else {
            Toast.makeText(context, "Permission is required for Documents", Toast.LENGTH_SHORT).show();
        }


    }

    public void onFastUploadPressed(View view) {
        changeFragment(new UploadPhotoFragment());
    }

    public void onMyFriendsListPressed(View view) {
        changeFragment(new FriendshipFragment());
    }

    public static Member getMember() {
        return member;
    }

    public static void setMember(Member incMember) {
        member = incMember;
    }

    public void openBackCamera() {
        dispatchTakePictureIntent();
    }


    //dispatch an intent to create a photo for result.
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = FileImageService.getFileImageService(this).createImageFile();
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

    //@After photo has been made this happens;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    File file = new File(mCurrentPhotoPath);
                    Bitmap bitmap = MediaStore.Images.Media
                            .getBitmap(this.getContentResolver(), Uri.fromFile(file));
                    if (bitmap != null) {
                        bitMapToSend = bitmap;
                        Bitmap currentbitmap = bitmap;
                        currentbitmap = FileImageService.getFileImageService(this).resize(currentbitmap, 400, 600);
                        pictureMade = true;
                        ((ImageView) findViewById(R.id.uploadedImage)).setImageBitmap(currentbitmap);
                    }
                }
            }

        } catch (Exception error) {
            error.printStackTrace();
        }
    }



    public Bitmap getBitMapToSend() {
        return bitMapToSend;
    }

    public void setBitMapToSend(Bitmap bitMapToSend) {
        this.bitMapToSend = bitMapToSend;
    }


    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        LoginActivity.context = context;
    }
}

