package com.example.vuki.drustvena_mreza_faks.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.activities.UserWallFromFriend;
import com.example.vuki.drustvena_mreza_faks.helpers.AdapterHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.BundleKeys;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.RetrofitHelper;
import com.example.vuki.drustvena_mreza_faks.models.SearchUserRequest;
import com.example.vuki.drustvena_mreza_faks.models.User;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Vuki on 4.11.2015..
 */
public class AddFriendsAdapter extends RecyclerView.Adapter<AddFriendsAdapter.ViewHolder> {

    static List<User> users;
    static Context context;
    static boolean friendList;

    public AddFriendsAdapter(Context context, List<User> users, boolean friendList) {
        this.users = users;
        this.context = context;
        this.friendList=friendList;
    }


    @Override
    public AddFriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_search_user_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AddFriendsAdapter.ViewHolder holder, final int position) {


        final User user = users.get(position);
        holder.username.setText(user.getUsername());
        String country = "";
        if (user.getCountryId() != null) {
            country = user.getCountryId();
        }

        String city = "";
        if (user.getCity() != null) {
            city = user.getCity();
        }

        String location = country + " " + city;
        holder.location.setText(location);

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserWallFromFriend.class);
                Bundle b = new Bundle();
                b.putInt(BundleKeys.FRIEND_USER_ID, user.getUserId());
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });

        AdapterHelpers.setImageWithGlide(context, user.getProfileImage(), holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(User user) {
        insert(user, users.size());
    }

    public void insert(User user, int position) {
        users.add(position, user);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        users.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = users.size();
        users.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(User[] strings) {
        int startIndex = users.size();
        users.addAll(startIndex, Arrays.asList(strings));
        notifyItemRangeInserted(startIndex, strings.length);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.search_user_username)
        TextView username;
        @Bind(R.id.search_user_location)
        TextView location;
        @Bind(R.id.search_user_profile_image)
        CircleImageView profileImage;
        @Bind(R.id.search_users_add_user)
        ImageView addUserAsFriend;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if(friendList) {
                addUserAsFriend.setVisibility(View.GONE);
            }else {
                addUserAsFriend.setOnClickListener(this);
            }
        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            int userAsFriend = users.get(adapterPosition).getUserId();

            SearchUserRequest searchUserRequest = new SearchUserRequest(userAsFriend);

            Call<Void> addUserAsFriendCall = ApiManager.getInstance().getService().addFriend(searchUserRequest);
            addUserAsFriendCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        NotesHelpers.toastMessage(context, "Congratulations,you have a new friend!");
                        addUserAsFriend.setImageResource(android.R.drawable.star_big_on);
                        addUserAsFriend.setClickable(false);
                    } else {
                        RetrofitHelper.checkCode(response.code(),context );
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    NotesHelpers.toastMessage(context, context.getResources().getString(R.string.error_something_is_wrong));

                }
            });

        }
    }


}
