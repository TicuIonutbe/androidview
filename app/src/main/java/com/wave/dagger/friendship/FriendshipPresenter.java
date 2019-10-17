package com.wave.dagger.friendship;

import com.wave.dagger.model.Friendship;

import java.util.List;

public class FriendshipPresenter implements FriendshipMVP.Model.FriendshipListener, FriendshipMVP.Presenter {

    @Override
    public void onAddFriendship(String answer) {

    }

    @Override
    public void onGetListOfFriends(List<Friendship> friendshipList) {

    }

    @Override
    public void onAcceptOrDenyFriendship(String answer) {

    }

    @Override
    public void onDeleteFriendship(String answer) {

    }
}
