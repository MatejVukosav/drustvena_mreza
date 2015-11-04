package com.example.vuki.drustvena_mreza_faks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Vuki on 4.11.2015..
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    public List<User> users;
    public ArrayList<String> pictures = new ArrayList<>();
    Context context;


    public SearchAdapter(Context context, List<User> users) {
        this.users = users;
        this.context = context;
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SearchAdapter.ViewHolder holder, final int position) {
        User user = users.get(position);

    /*    holder.discoverUsername.setText(user.getEmail());
        holder.discoverStoreName.setText(user.getFirstName());



        String url = user.getPicture();
        Glide.with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.profileImage);



        //user products

        Glide.with(context)
                .load(urlPicture)
                .centerCrop()
                .crossFade()
                .into(holder.discoveritemPicture1);


        Glide.with(context)
                .load(urlPicture)
                .centerCrop()
                .crossFade()
                .into(holder.discoveritemPicture2);


        Glide.with(context)
                .load(urlPicture)
                .centerCrop()
                .crossFade()
                .into(holder.discoveritemPicture3);


        Glide.with(context)
                .load(urlPicture)
                .centerCrop()
                .crossFade()
                .into(holder.discoveritemPicture4);

        //if user liked alr
        try {
            if (!LoginActivity.GYM_BAJA.getFollowedUsers().contains(user)) {
                holder.linearLayoutPictures.setVisibility(View.VISIBLE);
                holder.follow.setText(DiscoverActivity.followS);
            }
        }catch(Exception e){
            e.printStackTrace();
        }*/


        // holder.discoverItemRecyclerView.setAdapter(new DiscoverItemUserPicturesAdapter(context, pictures));

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
        @Bind(R.id.discoverUsername)
        TextView discoverUsername;
        @Bind(R.id.discoverStoreName)
        TextView discoverStoreName;
        @Bind(R.id.profile_image)
        CircleImageView profileImage;

        @Bind(R.id.discoveritemPicture1)
        ImageView discoveritemPicture1;
        @Bind(R.id.discoveritemPicture2)
        ImageView discoveritemPicture2;
        @Bind(R.id.discoveritemPicture3)
        ImageView discoveritemPicture3;
        @Bind(R.id.discoveritemPicture4)
        ImageView discoveritemPicture4;


        @Bind(R.id.discoverFollow)
        Button follow;

        @Bind(R.id.discoverLinearLayoutUserPictures)
        LinearLayout linearLayoutPictures;

       /* @Bind(R.id.discoverItemRecyclerView)
        SuperRecyclerView discoverItemRecyclerView;*/

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
