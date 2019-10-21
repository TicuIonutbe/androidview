package com.wave.dagger.friendship;


import com.wave.dagger.CardsAndFilesAPI.CardsAndFilesInterface;
import com.wave.dagger.model.Member;
import com.wave.dagger.service.AuthorizationService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FriendshipModel implements FriendshipMVP.Model {

    private Retrofit retrofit;
    private AuthorizationService authorizationService;
    private String token;

    public FriendshipModel(Retrofit retrofit, AuthorizationService authorizationService) {
        this.retrofit = retrofit;
        this.authorizationService = authorizationService;
        this.token = "Bearer " + authorizationService.getTokenFromPrefs();
    }

    @Override
    public void addFriendship(String friendEmail, final FriendshipListener friendshipListener) {

        CardsAndFilesInterface.FriendshipAPI service = retrofit.create(CardsAndFilesInterface.FriendshipAPI.class);
        Call<String> call = service.addFriendship("Bearer " + authorizationService.getTokenFromPrefs(), friendEmail);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    friendshipListener.onAddFriendship("Friendship added!");
                } else {
                    friendshipListener.onFail("There was something wrong, please try again later!");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                friendshipListener.onFail("Server not found, please try again later!");
            }
        });

    }

    @Override
    public void getListOfFriends(final FriendshipListener friendshipListener) {
        CardsAndFilesInterface.FriendshipAPI service = retrofit.create(CardsAndFilesInterface.FriendshipAPI.class);
        Call<List<Member>> call = service.getListofFriends("Bearer " + authorizationService.getTokenFromPrefs());

        call.enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                if (response.code() == 200) {
                    friendshipListener.onGetListOfFriends(response.body());
                } else {
                    friendshipListener.onFail("Something went wrong,please try again!");
                }
            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
                friendshipListener.onFail("Server is not available, please try again later!");
            }
        });

    }

    @Override
    public void acceptOrDenyFriendship(int friendId, int answer, final FriendshipListener friendshipListener) {
        CardsAndFilesInterface.FriendshipAPI service = retrofit.create(CardsAndFilesInterface.FriendshipAPI.class);
        Call<String> call = service.acceptOrDenyFriendship("Bearer " + authorizationService.getTokenFromPrefs(), friendId, answer);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    friendshipListener.onAcceptOrDenyFriendship("Friend updated!");
                } else {
                    friendshipListener.onFail("Something went wrong! Please try again!");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                friendshipListener.onFail("Server not found! Please try again later!");
            }
        });

    }

    @Override
    public void deleteFriendship(int friendId, final FriendshipListener friendshipListener) {
        CardsAndFilesInterface.FriendshipAPI service = retrofit.create(CardsAndFilesInterface.FriendshipAPI.class);
        Call<String> call = service.deleteFriendship("Bearer " + authorizationService.getTokenFromPrefs(), friendId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    friendshipListener.onDeleteFriendship("Friendship deleted!");
                } else {
                    friendshipListener.onFail("Something went wrong please try again!");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                friendshipListener.onFail("Server not found, please try again later!");
            }
        });
    }
}
