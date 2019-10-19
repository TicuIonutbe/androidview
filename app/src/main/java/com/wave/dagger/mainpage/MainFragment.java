package com.wave.dagger.mainpage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wave.dagger.R;
import com.wave.dagger.root.LoginActivity;
import com.wave.dagger.login.LoginFragment;
import com.wave.dagger.model.Member;
import com.wave.dagger.service.AuthorizationService;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class MainFragment extends Fragment implements View.OnClickListener, MainMVPContract.View {


    private Button logout;
    private FragmentManager fm;
    private LoginActivity loginActivity;
    private SharedPreferences prefs;
    private TextView email,firstName,lastName,memberId;

    @Inject
    MainMVPContract.Presenter presenter;

    private Member member;

    public MainFragment() {
        // Required empty public constructor
    }

    public MainFragment(Member member) {
        this.member = member;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        logout = view.findViewById(R.id.btnLogout);
        logout.setOnClickListener(this);
        email = view.findViewById(R.id.mainEmail);
        firstName = view.findViewById(R.id.mainName);
        lastName = view.findViewById(R.id.mainLastName);
        memberId = view.findViewById(R.id.mainId);
        view.findViewById(R.id.btnDeleteProfile).setOnClickListener(this);


        email.setText(member.getEmail());
        firstName.setText(member.getFirstName());
        lastName.setText(member.getLastName());
        memberId.setText(String.valueOf(member.getId()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginActivity.setMember(member);
        fm = loginActivity.getSupportFragmentManager();
        presenter.setView(this);
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
        if (v == logout) {
            fm.beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commitAllowingStateLoss();
            prefs.edit().remove(AuthorizationService.TOKEN).apply();
            Log.e("LOGOUT INFO: ", "THE LOGOUT BUTTON WAS PRESSED!");
        } else if (v == getView().findViewById(R.id.btnDeleteProfile)) {




            presenter.deleteMember(member);
        }
    }

    @Override
    public void setLoginActivity(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    public void setPrefs(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public void updateView(Member member) {

        email.setText(member.getEmail());
        firstName.setText(member.getFirstName());
        lastName.setText(member.getLastName());
        memberId.setText(String.valueOf(member.getId()));
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
