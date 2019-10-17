package com.wave.dagger.document;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.wave.dagger.R;
import com.wave.dagger.login.LoginActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class UploadPhotoFragment extends Fragment implements View.OnClickListener {

    Button btnUploadPhoto, btnMakePhoto;
    @Inject
    DocumentMVP.Presenter documentPresenter;
    private EditText fileName;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private View view;

    public UploadPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload_photo, container, false);
        view = v;
        btnUploadPhoto = v.findViewById(R.id.btnUploadPhoto);
        btnMakePhoto = v.findViewById(R.id.btnMakePhoto);
        fileName = v.findViewById(R.id.documentAddName);
        btnMakePhoto.setOnClickListener(this);
        btnUploadPhoto.setOnClickListener(this);
        radioGroup = (RadioGroup) v.findViewById(R.id.typeRadio);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        documentPresenter.setUploadPhotoFragment(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        if (v == btnUploadPhoto) {
            if (fileName.getText().toString().length() > 1) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) view.findViewById(selectedId);
                documentPresenter.uploadDocument(fileName.getText().toString(),radioButton.getText().toString());
            } else {
                Toast.makeText(getActivity(), "Details are not ok!", Toast.LENGTH_SHORT).show();
            }
        } else if (v == btnMakePhoto) {
            ((LoginActivity) getActivity()).openBackCamera();
        }
    }


}
