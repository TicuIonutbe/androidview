package com.wave.dagger.friendship;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wave.dagger.R;
import com.wave.dagger.model.Friendship;
import com.wave.dagger.model.Member;

import java.util.List;

class FriendshipAdapter extends RecyclerView.Adapter<FriendshipAdapter.MyViewHolder> {

    private List<Member> friends;
    private FriendshipFragment friendshipFragment;
    private View.OnClickListener onClickListener;


    public FriendshipAdapter(List<Member> friends, FriendshipFragment friendshipFragment, View.OnClickListener onClickListener) {
        this.friends = friends;
        this.friendshipFragment = friendshipFragment;
        this.onClickListener = onClickListener;
    }

    @Override
    public FriendshipAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friendship_card, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //holder.
        holder.friendEmail.setText(friends.get(position).getEmail());
        String status = "NEW REQUEST";
        holder.friendStatus.setTextColor(Color.BLUE);
        if (friends.get(position).getStatus() == 1) {
            status = "IGNORED";
            holder.friendStatus.setTextColor(Color.RED);
        } else if (friends.get(position).getStatus() == 2) {
            status = "ACCEPTED";
            holder.friendStatus.setTextColor(Color.GREEN);
        }
        holder.friendStatus.setText(status);

        if (!friends.get(position).isRequester()) {
            holder.btnFriendshipUpdate.setTextColor(Color.GRAY);
            holder.btnFriendshipUpdate.setEnabled(false);
        }
        holder.btnFriendshipUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendshipFragment.setFriendId(friends.get(position).getId());
                onClickListener.onClick(holder.btnFriendshipUpdate);
            }
        });
        holder.btnIgnoreFriendship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendshipFragment.setFriendId(friends.get(position).getId());
                onClickListener.onClick(holder.btnIgnoreFriendship);
            }
        });
        holder.btnDeleteFriendship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendshipFragment.deleteFriendship(friends.get(position).getId());
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return friends.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView friendEmail, friendStatus;
        private Button btnFriendshipUpdate, btnIgnoreFriendship, btnDeleteFriendship;

        public MyViewHolder(RelativeLayout v) {
            super(v);
            friendStatus = v.findViewById(R.id.friendStatus);
            friendEmail = v.findViewById(R.id.friendEmail);
            btnFriendshipUpdate = v.findViewById(R.id.btnFriendshipUpdate);
            btnIgnoreFriendship = v.findViewById(R.id.btnIgnoreFriendShip);
            btnDeleteFriendship = v.findViewById(R.id.btnDeleteFriendship);

        }
    }
}
