package com.wave.dagger.document;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wave.dagger.R;
import com.wave.dagger.model.Document;
import com.wave.dagger.root.LoginActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class DocumentFragment extends Fragment {


    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Inject
    public DocumentMVP.Presenter documentPresenter;


    public DocumentFragment() {
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
        view = inflater.inflate(R.layout.fragment_document, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DocumentAdapter dAdapter = new DocumentAdapter(((LoginActivity)getActivity()).getMember().getDocumentList(),this);

        recyclerView.setAdapter(dAdapter);


        return view;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
        documentPresenter.setDocumentFragment(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void deleteDocument(Document currentDoc) {
        documentPresenter.deleteDocument(currentDoc);
    }

    public void restartFragment(){
        ((LoginActivity) getActivity()).changeFragment(new DocumentFragment());
    }
}
