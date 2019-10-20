package com.wave.dagger.document.documentfeatures;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.wave.dagger.R;
import com.wave.dagger.document.DocumentAdapter;
import com.wave.dagger.document.DocumentMVP;
import com.wave.dagger.model.Document;
import com.wave.dagger.root.LoginActivity;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class DocumentUpdateFragment extends Fragment implements View.OnClickListener {
    private EditText documentName;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Switch visible;
    private Button btnUpdate;
    private View v;
    @Inject
    DocumentMVP.Presenter documentPresenter;
    private Document doc;


    public DocumentUpdateFragment() {
        this.doc = DocumentAdapter.currentDocument;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_document_update, container, false);

        visible = v.findViewById(R.id.visible);
        documentName = v.findViewById(R.id.updateName);
        radioGroup = v.findViewById(R.id.radiogrpUpdateDoc);
        btnUpdate = v.findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(this);

        documentName.setText(doc.getName());
        visible.setChecked(doc.isVisibility());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        documentPresenter.setDocumentUpdateFragment(this);
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
        if (v == btnUpdate) {
            if (documentName.getText().toString().length() > 1) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioButton = this.v.findViewById(selectedId);
                doc.setName(documentName.getText().toString());
                doc.setType(radioButton.getText().toString());
                doc.setVisibility(visible.isChecked());

                documentPresenter.updateDocument(doc);

            }
        }
    }
}
