package com.example.vuki.drustvena_mreza_faks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.models.ContactRawInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Vuki on 2.1.2016..
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    static List<ContactRawInfo> contactRawInfos;
    static Context context;
    static boolean friendList;

    public FriendsAdapter(Context context, List<ContactRawInfo> contactRawInfos, boolean friendList) {
        this.contactRawInfos = contactRawInfos;
        this.context = context;
        this.friendList = friendList;
    }


    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_search_user_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FriendsAdapter.ViewHolder holder, final int position) {
        ContactRawInfo contactRawInfo = contactRawInfos.get(position);
        holder.username.setText(contactRawInfo.getUsername());

    }

    @Override
    public int getItemCount() {
        return contactRawInfos.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
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

            if(friendList){
                addUserAsFriend.setVisibility(View.GONE);
            }


        }


    }


}
