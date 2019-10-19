package com.wave.dagger.mainpage.updatemember;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.wave.dagger.R;
import com.wave.dagger.mainpage.MainMVPContract;
import com.wave.dagger.root.LoginActivity;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class UpdateMemberFragment extends Fragment implements View.OnClickListener {
    private LoginActivity activity;
    private Button btnConfirmUpdateProfile;

    @Inject
    private MainMVPContract.Presenter mainPresenter;

    public UpdateMemberFragment() {
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
        View v = inflater.inflate(R.layout.fragment_update_member, container, false);
        btnConfirmUpdateProfile = v.findViewById(R.id.btnUpdateProfile);
        btnConfirmUpdateProfile.setOnClickListener(this);
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
        activity = (LoginActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        if (v == btnConfirmUpdateProfile) {
            String repeatPassword = ((EditText) getActivity().findViewById(R.id.updateProfilePassword2)).getText().toString();
            String password = ((EditText) getActivity().findViewById(R.id.updateProfilePassword)).getText().toString();
            String firstName = ((EditText) getActivity().findViewById(R.id.updateProfileFirstName)).getText().toString();
            String lastName = ((EditText) getActivity().findViewById(R.id.updateProfileLastName)).getText().toString();

            if (password.equals(repeatPassword) && repeatPassword.length() > 3 && password.length() > 3 && firstName.length() > 3 && lastName.length() > 3) {
                activity.getMember().setPassword(password);
                activity.getMember().setFirstName(firstName);
                activity.getMember().setLastName(lastName);
                mainPresenter.updateMember(activity.getMember());
            } else {
                Log.e("ERROR",repeatPassword + password + firstName + lastName);
                Toast.makeText(getContext(), "Credentials are not ok!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
