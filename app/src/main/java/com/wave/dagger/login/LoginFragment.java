package com.wave.dagger.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.wave.dagger.R;
import com.wave.dagger.model.Member;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class LoginFragment extends Fragment implements LoginMVP.View, View.OnClickListener {

    private EditText email, password, recoveryEmail, registerEmail, registerFirstName, registerLastName, registerPassword, register2Password;
    private TextView token, registerBack, recoveryBtnBack;
    private LinearLayout loginLayout, registerLayout, recoveryLayout;
    private Button btnRegister, btnRecoveryPage, btnGoRecover;


    @Inject
    LoginMVP.Presenter loginPresenter;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        email = v.findViewById(R.id.email);
        password = v.findViewById(R.id.password);
        token = (TextView) v.findViewById(R.id.token);
        token.setOnClickListener(this);
        registerBack = v.findViewById(R.id.registerBack);
        registerBack.setOnClickListener(this);
        v.findViewById(R.id.btnChange).setOnClickListener(this);
        loginLayout = v.findViewById(R.id.loginLayout);
        registerLayout = v.findViewById(R.id.registerLayout);
        recoveryLayout = v.findViewById(R.id.recoveryLayout);
        btnRecoveryPage = v.findViewById(R.id.btnRecoveryPage);
        recoveryBtnBack = v.findViewById(R.id.recoveryBtnBack);
        btnGoRecover = v.findViewById(R.id.btnRecovery);
        btnGoRecover.setOnClickListener(this);
        recoveryBtnBack.setOnClickListener(this);
        btnRecoveryPage.setOnClickListener(this);
        recoveryEmail = v.findViewById(R.id.recoveryEmail);
        btnRegister = v.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        registerEmail = v.findViewById(R.id.registerEmail);
        registerFirstName = v.findViewById(R.id.registerFirstName);
        registerLastName = v.findViewById(R.id.registerLastName);
        registerPassword = v.findViewById(R.id.registerPassword);
        register2Password = v.findViewById(R.id.registerPassword2);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.setView(this);

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
        if (v == v.findViewById(R.id.btnChange)) {
            loginPresenter.authenticate();
        } else if (v == token) {
            loginLayout.setVisibility(View.GONE);
            registerLayout.setVisibility(View.VISIBLE);
        } else if (v == registerBack) {
            loginLayout.setVisibility(View.VISIBLE);
            registerLayout.setVisibility(View.GONE);
        } else if (v == btnRegister) {
            if (register2Password.getText().toString().equals(registerPassword.getText().toString())) {
                loginPresenter.register(new Member(registerEmail.getText().toString()
                        , registerFirstName.getText().toString()
                        , registerLastName.getText().toString()
                        , registerPassword.getText().toString()));
            } else {
                Toast.makeText(getActivity(), "Passwords are not equal!", Toast.LENGTH_SHORT).show();
            }
        } else if (v == btnRecoveryPage) {
            loginLayout.setVisibility(View.GONE);
            recoveryLayout.setVisibility(View.VISIBLE);
        } else if (v == recoveryBtnBack) {
            loginLayout.setVisibility(View.VISIBLE);
            recoveryLayout.setVisibility(View.GONE);
        } else if (v == btnGoRecover) {
            loginPresenter.goRecovery(recoveryEmail.getText().toString());
        }
    }

    @Override
    public String getEmail() {
        return email.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void updateToken(String token) {
        this.token.setText(token);
    }
}
