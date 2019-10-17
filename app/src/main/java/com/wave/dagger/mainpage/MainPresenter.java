package com.wave.dagger.mainpage;

import android.widget.Toast;

import com.wave.dagger.R;
import com.wave.dagger.model.Member;

import javax.inject.Inject;

public class MainPresenter implements MainMVPContract.Presenter, MainMVPContract.Model.OnFinishListener {

    MainFragment mainFragment;
    MainModel mainModel;

    @Inject
    public MainPresenter(MainModel model) {
        this.mainModel = model;
    }

    @Override
    public void setView(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    @Override
    public void deleteMember(Member member) {
        mainModel.deleteMember(member, this);
    }

    @Override
    public void updateMember(Member member) {
        mainModel.updateMember(member, this);
    }

    @Override
    public void onSuccess(Object String) {
        Toast.makeText(mainFragment.getContext(), String.toString(), Toast.LENGTH_SHORT).show();
        mainFragment.onClick(mainFragment.getActivity().findViewById(R.id.btnLogout));
    }

    @Override
    public void onFailure(String failText) {
        Toast.makeText(mainFragment.getContext(), failText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateMember(Member member) {
        Member loggedMember = mainFragment.getMember();
        loggedMember.setFirstName(member.getFirstName());
        loggedMember.setLastName(member.getLastName());
        mainFragment.updateView(loggedMember);
        Toast.makeText(mainFragment.getContext(), "Updated with success!", Toast.LENGTH_SHORT).show();
    }
}
