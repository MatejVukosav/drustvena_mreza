package com.example.vuki.drustvena_mreza_faks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.helpers.AdapterHelpers;
import com.example.vuki.drustvena_mreza_faks.models.User;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Vuki on 4.11.2015..
 */
public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {

    public List<User> users;
    Context context;



    public SearchUserAdapter(Context context, List<User> users) {
        this.users = users;
        this.context = context;
    }

    @Override
    public SearchUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_search_user_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SearchUserAdapter.ViewHolder holder, final int position) {

/*
        User user=users.get(position);
        holder.username.setText(user.getFirstName()+" "+user.getLastName());
        holder.location.setText("lokacija usera");*/

        holder.username.setText("Mitar Miric");
        holder.location.setText("lokacija usera");
        AdapterHelpers.setImage(context, R.drawable.lisica,holder.profileImage);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.search_user_username)
        TextView username;
        @Bind(R.id.search_user_location)
        TextView location;
        @Bind(R.id.search_user_profile_image)
        CircleImageView profileImage;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        /*    follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("tt", "tt");
                    if (follow.getText().equals(DiscoverActivity.followS)) {
                        linearLayoutPictures.setVisibility(View.GONE);
                        follow.setText(DiscoverActivity.unfollowS);
                    } else {
                        linearLayoutPictures.setVisibility(View.VISIBLE);
                        follow.setText(DiscoverActivity.followS);
                    }


                }
            });*/
        }

    }


}
