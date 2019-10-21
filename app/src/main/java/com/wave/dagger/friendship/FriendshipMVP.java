package com.wave.dagger.friendship;

import com.wave.dagger.model.Friendship;
import com.wave.dagger.model.Member;

import java.util.List;

public interface FriendshipMVP {

    interface View {

        void updateView();

        void deleteFriendship(int id);

    }

    interface Presenter {

        void setFriendshipFragment(FriendshipFragment friendshipFragment);

        void addFriendShip(String email);

        void getListOfFriends();

        void AcceptOrDenyFriendship(int friendId, int answer);

        void deleteFriendship(int id);

    }

    interface Model {

        interface FriendshipListener {
            void onAddFriendship(String answer);

            void onGetListOfFriends(List<Member> friendsList);

            void onAcceptOrDenyFriendship(String answer);

            void onDeleteFriendship(String answer);

            void onFail(String message);
        }

        void addFriendship(String friendEmail,FriendshipListener friendshipListener);

        void getListOfFriends(FriendshipListener friendshipListener);

        void acceptOrDenyFriendship(int friendId, int answer,FriendshipListener friendshipListener);

        void deleteFriendship(int friendId, FriendshipListener friendshipListener);

    }
}
