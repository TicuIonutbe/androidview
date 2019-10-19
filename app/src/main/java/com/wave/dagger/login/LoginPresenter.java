package com.wave.dagger.login;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.wave.dagger.R;
import com.wave.dagger.mainpage.MainFragment;
import com.wave.dagger.model.JwtRequest;
import com.wave.dagger.model.JwtResponse;
import com.wave.dagger.model.Member;
import com.wave.dagger.root.LoginActivity;
import com.wave.dagger.service.AuthorizationService;

import javax.inject.Inject;

public class LoginPresenter implements LoginMVP.Presenter, LoginMVP.Model.OnFinishedListener {

    private LoginFragment loginFragment;

    private LoginModel loginModel;

    private AuthorizationService authorizationService;

    @Inject
    public LoginPresenter(LoginModel loginModel, AuthorizationService authorizationService) {
        this.loginModel = loginModel;
        this.authorizationService = authorizationService;
    }


    @Override
    public void authenticate() {
        String email = loginFragment.getEmail();
        String password = loginFragment.getPassword();
        if (email.equals("") || password.equals("")) {
            Toast.makeText(loginFragment.getActivity().getApplicationContext(), "Fill up the fields!", Toast.LENGTH_SHORT).show();
        } else {
            loginModel.getToken(new JwtRequest(email, password), this);
        }
    }

    @Override
    public void setView(LoginFragment view) {
        loginFragment = view;
    }

    @Override
    public void register(Member member) {
        loginModel.register(member, this);
    }

    @Override
    public void goRecovery(String email) {
        loginModel.recovery(email, this);
    }

    //listens to request for Token and then makes request for Member from token.
    @Override
    public void onFinished(JwtResponse jwtResponse) {
        //after we have token we ask for the member and save the token on disk
        loginModel.getMember(jwtResponse.getJwttoken(), this);
        authorizationService.saveToken(jwtResponse.getJwttoken());

    }

    //Listens when Member is loaded from the GetRequest
    //and changes Login fragment to MainPage Fragment
    @Override
    public void onMemberResponse(Member member) {
        FragmentManager fm = loginFragment.getActivity().getSupportFragmentManager();
        MainFragment mainFragment = new MainFragment(member);

        mainFragment.setLoginActivity((LoginActivity) loginFragment.getActivity());

        mainFragment.setPrefs(authorizationService.getPrefs());

        fm.beginTransaction().replace(R.id.fragment_container, mainFragment).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public void onRegisterResponse(String answer) {
        Toast.makeText(loginFragment.getActivity(), "Registration was completed, now u can use the back button to login!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmailRecovery(int httpCode) {
        if (httpCode == 200) {
            Toast.makeText(loginFragment.getActivity(), "An email has been sent with the new password!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(loginFragment.getActivity(), "Email not found!", Toast.LENGTH_SHORT).show();
        }
    }

    //listens to get request failure!
    @Override
    public void onFailure(String answer) {
        //do something with failure
        Toast.makeText(loginFragment.getContext(), answer, Toast.LENGTH_SHORT).show();
        Log.e("error", answer);
    }
}
