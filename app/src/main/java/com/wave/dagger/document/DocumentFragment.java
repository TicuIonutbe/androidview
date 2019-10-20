package com.wave.dagger.document;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wave.dagger.R;
import com.wave.dagger.model.Document;
import com.wave.dagger.root.LoginActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class DocumentFragment extends Fragment implements DocumentMVP.View {


    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private String m_Text = "";
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
        documentPresenter.getUpdatedDocumentListToMember();
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

    public void restartFragment() {
        ((LoginActivity) getActivity()).changeFragment(new DocumentFragment());
    }

    public void dialogBuilder(final Document doc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Email:");

        // Set up the input
        final EditText input = new EditText(getContext());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().length() == 0) {
                    dialogBuilder(doc);
                } else {
                    m_Text = input.getText().toString();
                    sendDocumentOnEmail(m_Text, doc);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void sendDocumentOnEmail(String email, Document doc) {
        documentPresenter.sendDocumentOnEmail(email, doc);
    }

    @Override
    public void updateView() {



        DocumentAdapter dAdapter = new DocumentAdapter(LoginActivity.getMember().getDocumentList(), this);

        recyclerView.setAdapter(dAdapter);

    }
}
