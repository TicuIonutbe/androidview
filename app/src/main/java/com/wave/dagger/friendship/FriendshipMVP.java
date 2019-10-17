package com.wave.dagger.friendship;

import com.wave.dagger.model.Friendship;

import java.util.List;

public interface FriendshipMVP {

    interface View {

    }

    interface Presenter {

    }

    interface Model {

        interface FriendshipListener {
            void onAddFriendship(String answer);

            void onGetListOfFriends(List<Friendship> friendshipList);

            void onAcceptOrDenyFriendship(String answer);

            void onDeleteFriendship(String answer);
        }

        void addFriendship(String friendEmail,FriendshipListener friendshipListener);

        void getListOfFriends(FriendshipListener friendshipListener);

        void acceptOrDenyFriendship(int friendId, int answer,FriendshipListener friendshipListener);

        void deleteFriendship(int friendId, FriendshipListener friendshipListener);

    }
}
