package com.wave.dagger.document;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wave.dagger.R;
import com.wave.dagger.login.LoginActivity;


public class DocumentViewFragment extends Fragment {
    View v;
    Bitmap image;
    ImageView imageView;


    public DocumentViewFragment() {
        // Required empty public constructor
    }

    public DocumentViewFragment(Bitmap image) {
        this.image = image;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_document_view, container, false);
        imageView = v.findViewById(R.id.imgShowBigDocument);

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();

        imageView.setImageBitmap(LoginActivity.resize(image,600,1000));
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
