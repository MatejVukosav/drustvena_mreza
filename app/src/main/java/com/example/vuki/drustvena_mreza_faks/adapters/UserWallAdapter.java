package com.example.vuki.drustvena_mreza_faks.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.helpers.AdapterHelpers;
import com.example.vuki.drustvena_mreza_faks.listeners.ItemClickListener;
import com.example.vuki.drustvena_mreza_faks.models.Post;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Vuki on 7.11.2015..
 */
public class UserWallAdapter extends RecyclerView.Adapter<UserWallAdapter.ViewHolder> {


    List<Post> data;
    private ItemClickListener<Post> itemClickListener;
    private static int i = 0;
    private static int j = 0;
    Context context;


    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_STATUS_ONLY = 1;
    private static final int TYPE_HEADER = 2;


    public UserWallAdapter(List<Post> data, ItemClickListener<Post> itemClickListener, Context context) {
        this.data = data;
        this.itemClickListener = itemClickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;//= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_home_item, parent, false);
        switch (viewType) {
            case TYPE_IMAGE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_home_item, parent, false);
                break;
            case TYPE_STATUS_ONLY:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_home_item_status, parent, false);
                break;

            case TYPE_HEADER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_user_wall_header, parent, false);
                break;
            default:
                v = new View(context);
        }

        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 2;
        } else if (data.get(position).getPostImage() == null) {
            return 1;
        } else {
            return 0;
        }

    }


    @Override
    public void onBindViewHolder(UserWallAdapter.ViewHolder holder, int position) {


       /* //if click on picture
        holder.itemPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(post);
            }
        });*/

/*

        holder.message.setText(post.getMessage());
        holder.numOfComments.setText(post.getCommentNum());
        holder.numOfLikes.setText(post.getLikes_num());
        setImage(post.getCreator().getProfileImage(), holder.personal_picture);
        holder.username.setText(post.getCreator().getFirstName()+" "+ post.getCreator().getLastName());
               //sa slikom
        if (i % 2 == 0) {
            holder.itemPicture.setVisibility(View.VISIBLE);
            setImage(post.getPostImage(), holder.itemPicture);
        }else{ //bez slike
            holder.itemPicture.setVisibility(View.GONE);
        }
        i++;

        */
        if (holder.getItemViewType() == TYPE_HEADER) {
            AdapterHelpers.setImage(context,R.drawable.lisica,holder.userWallPicture);
            holder.userWallUsername.setText("Mišo Kovač");

        } else {

            Post post = data.get(position);
            Random rand = new Random();
            holder.message.setText("Ovo je status i bas je kul gfgkfggfgjjggfbvghbbnvghgfkbvtbvzitbztbzbkkgbgbkgbhkgbhkgbhkgbhkgbhkgbhgbhghkknh");
            holder.numOfComments.setText("Comments: " + String.valueOf(rand.nextInt(100) + 1));
            holder.numOfLikes.setText("Likes: " + String.valueOf(rand.nextInt(100) + 1));


            long now = getTimeNow();

            Calendar calYesterday = Calendar.getInstance();
            calYesterday.add(Calendar.DATE, -3);
            long time = calYesterday.getTime().getTime();

            CharSequence relativeTimeSpan = DateUtils.getRelativeTimeSpanString(time, now, 0);
            holder.postTime.setText(relativeTimeSpan);
            AdapterHelpers.setImage(context, R.drawable.lisica, holder.personal_picture);
            holder.username.setText("Mišo Kovač");

            //sa slikom
            if (holder.getItemViewType() == TYPE_IMAGE) {
                AdapterHelpers.setImage(context, R.drawable.dvorac1, holder.itemPicture);
            }
        }

    }

    private long getTimeNow() {
        Calendar calNow = Calendar.getInstance();
        calNow.add(Calendar.DATE, 0);
        return calNow.getTime().getTime();

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.core_home_item_picture)
        ImageView itemPicture;
        @Nullable @Bind(R.id.core_home_username)
        TextView username;
        @Nullable @Bind(R.id.core_home_user_personal_picture)
        CircleImageView personal_picture;
        @Nullable @Bind(R.id.core_home_num_of_likes)
        TextView numOfLikes;
        @Nullable @Bind(R.id.core_home_num_of_comments)
        TextView numOfComments;
        @Nullable @Bind(R.id.core_home_message)
        TextView message;
        @Nullable @Bind(R.id.core_home_post_time)
        TextView postTime;

        @Nullable @Bind(R.id.user_wall_picture)
        ImageView userWallPicture;
        @Nullable @Bind(R.id.user_wall_username)
        TextView userWallUsername;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
