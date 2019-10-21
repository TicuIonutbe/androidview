package com.wave.dagger.friendship;

import android.content.Intent;
import android.widget.Toast;

import com.wave.dagger.model.Member;

import java.util.List;

import javax.inject.Inject;

public class FriendshipPresenter implements FriendshipMVP.Model.FriendshipListener, FriendshipMVP.Presenter {

    private FriendshipFragment friendshipFragment;
    private FriendshipModel friendshipModel;

    @Inject
    public FriendshipPresenter(FriendshipModel friendshipModel) {
        this.friendshipModel = friendshipModel;
    }

    @Override
    public void onAddFriendship(String answer) {
        Toast.makeText(friendshipFragment.getContext(), answer, Toast.LENGTH_SHORT).show();
        friendshipFragment.startActivity(Intent.makeRestartActivityTask(friendshipFragment.getActivity().getIntent().getComponent()));
    }

    @Override
    public void onGetListOfFriends(List<Member> friendsList) {
        friendshipFragment.setFriends(friendsList);
        friendshipFragment.updateView();
    }

    @Override
    public void onAcceptOrDenyFriendship(String answer) {
        Toast.makeText(friendshipFragment.getContext(), answer, Toast.LENGTH_SHORT).show();
        friendshipFragment.getFragmentManager().beginTransaction().detach(friendshipFragment).attach(friendshipFragment).commit();
    }

    @Override
    public void onDeleteFriendship(String answer) {
        Toast.makeText(friendshipFragment.getContext(), answer, Toast.LENGTH_SHORT).show();
        friendshipFragment.getFragmentManager().beginTransaction().detach(friendshipFragment).attach(friendshipFragment).commit();
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(friendshipFragment.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addFriendShip(String email) {
        friendshipModel.addFriendship(email, this);
    }

    @Override
    public void getListOfFriends() {
        friendshipModel.getListOfFriends(this);

    }

    @Override
    public void AcceptOrDenyFriendship(int friendId, int answer) {
        friendshipModel.acceptOrDenyFriendship(friendId, answer, this);
    }

    @Override
    public void deleteFriendship(int id) {
        friendshipModel.deleteFriendship(id, this);
    }


    @Override
    public void setFriendshipFragment(FriendshipFragment friendshipFragment) {
        this.friendshipFragment = friendshipFragment;
    }

}
