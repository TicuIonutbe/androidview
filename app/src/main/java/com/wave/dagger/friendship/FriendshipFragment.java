package com.wave.dagger.friendship;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wave.dagger.R;
import com.wave.dagger.document.DocumentAdapter;
import com.wave.dagger.model.Friendship;
import com.wave.dagger.model.Member;
import com.wave.dagger.root.LoginActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class FriendshipFragment extends Fragment implements FriendshipMVP.View, View.OnClickListener {


    private View v;
    @Inject
    public FriendshipMVP.Presenter friendshipPresenter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<Member> friends;
    private Button btnAddFriend;
    private EditText etxtFriendEmail;
    private int friendId;

    public FriendshipFragment() {
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
        v = inflater.inflate(R.layout.fragment_friendship, container, false);
        recyclerView = v.findViewById(R.id.recyclerFriends);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        friendshipPresenter.getListOfFriends();
        btnAddFriend = v.findViewById(R.id.btnAddFriend);
        etxtFriendEmail = v.findViewById(R.id.addFriendEmail);

        btnAddFriend.setOnClickListener(this);

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
        friendshipPresenter.setFriendshipFragment(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void updateView() {
        FriendshipAdapter dAdapter = new FriendshipAdapter(friends, this, this);

        recyclerView.setAdapter(dAdapter);
    }

    @Override
    public void deleteFriendship(int id) {
        friendshipPresenter.deleteFriendship(id);
    }

    public List<Member> getFriends() {
        return friends;
    }

    public void setFriends(List<Member> friends) {
        this.friends = friends;
    }

    @Override
    public void onClick(View v) {
        if (v == btnAddFriend) {
            if (etxtFriendEmail.getText().toString().length() > 3) {
                friendshipPresenter.addFriendShip(etxtFriendEmail.getText().toString());
            } else {
                Toast.makeText(getContext(), "Email must be filled in.", Toast.LENGTH_SHORT).show();
            }
        } else if (((Button) v).getText().toString().equals("ACCEPT")) {
            friendshipPresenter.AcceptOrDenyFriendship(friendId, 2);
        } else if (((Button) v).getText().toString().equals("IGNORE")) {
            friendshipPresenter.AcceptOrDenyFriendship(friendId, 1);
        } else {
            Log.e("onclick", ((Button) v).getText().toString());
        }
    }


    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
}
