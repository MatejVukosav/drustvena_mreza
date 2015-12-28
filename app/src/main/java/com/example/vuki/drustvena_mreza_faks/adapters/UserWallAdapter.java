package com.example.vuki.drustvena_mreza_faks.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.activities.UserWallAbout;
import com.example.vuki.drustvena_mreza_faks.activities.UserWallFriends;
import com.example.vuki.drustvena_mreza_faks.activities.UserWallPhotos;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.listeners.ItemClickListener;
import com.example.vuki.drustvena_mreza_faks.models.Post;
import com.example.vuki.drustvena_mreza_faks.models.User;

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


    static List<Post> data;
    private ItemClickListener<Post> itemClickListener;
    private static int i = 0;
    private static int j = 0;
    static Context context;
    static User mUser;


    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_STATUS_ONLY = 1;
    private static final int TYPE_HEADER = 2;


    public UserWallAdapter(User user,List<Post> data, ItemClickListener<Post> itemClickListener, Context context) {
        this.data = data;
        this.itemClickListener = itemClickListener;
        this.context = context;
        this.mUser=user;
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
        }else return TYPE_STATUS_ONLY;


         /*else if (data.get(position).getContetnTypeId() == 0) {
            return 0;
        } else {
            return 1;
        }*/

    }


    @Override
    public void onBindViewHolder(UserWallAdapter.ViewHolder holder, int position) {

        //header item
        if (holder.getItemViewType() == TYPE_HEADER) {
            //AdapterHelpers.setImage(context,R.drawable.lisica,holder.userWallPicture);
            holder.userWallUsername.setText(mUser.getUsername());

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

            // AdapterHelpers.setImage(context, R.drawable.lisica, holder.personal_picture);
            holder.username.setText("Mišo Kovač");

            //sa slikom
           /* if (holder.getItemViewType() == TYPE_IMAGE) {
                AdapterHelpers.setImage(context, R.drawable.dvorac1, holder.itemPicture);
            }*/
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

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Nullable
        @Bind(R.id.core_home_item_picture)
        ImageView itemPicture;
        @Nullable
        @Bind(R.id.core_home_username)
        TextView username;
        @Nullable
        @Bind(R.id.core_home_user_personal_picture)
        CircleImageView personal_picture;

        @Nullable
        @Bind(R.id.core_home_num_of_likes)
        TextView numOfLikes;
        @Nullable
        @Bind(R.id.like_btn)
        Button likeBtn;

        @Nullable
        @Bind(R.id.core_home_comments)
        TextView numOfComments;
        @Nullable
        @Bind(R.id.comments_btn)
        Button commentsBtn;

        @Nullable
        @Bind(R.id.core_home_message)
        TextView message;
        @Nullable
        @Bind(R.id.core_home_post_time)
        TextView postTime;

        @Nullable
        @Bind(R.id.user_wall_picture)
        ImageView userWallPicture;
        @Nullable
        @Bind(R.id.user_wall_username)
        TextView userWallUsername;
        @Nullable
        @Bind(R.id.user_wall_about)
        TextView userWallAbout;
        @Nullable
        @Bind(R.id.user_wall_photos)
        TextView userWallPhotos;
        @Nullable
        @Bind(R.id.user_wall_friends)
        TextView userWallFriends;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if (likeBtn != null) {
                likeBtn.setOnClickListener(this);
            }
            if (commentsBtn != null) {
                commentsBtn.setOnClickListener(this);
            }

            if (userWallAbout != null) {
                userWallAbout.setOnClickListener(this);
            }
            if (userWallPhotos != null) {
                userWallPhotos.setOnClickListener(this);
            }
            if (userWallFriends != null) {
                userWallFriends.setOnClickListener(this);
            }

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.comments_btn:
                    getComments();
                    break;
                case R.id.like_btn:
                    getLike();
                    break;
                case R.id.user_wall_about:
                    getAbout();
                    break;
                case R.id.user_wall_photos:
                    getPhotos();
                    break;
                case R.id.user_wall_friends:
                    getFriends();
                    break;
            }
        }


        private static void getComments() {
            NotesHelpers.toastMessage(context, "komentari");
        }

        private static void getLike() {
            NotesHelpers.toastMessage(context, "lajkoovi");
        }

        private static void getAbout() {
            NotesHelpers.toastMessage(context, "about");
            Intent intent=new Intent(context, UserWallAbout.class);
            context.startActivity(intent);
        }

        private static void getPhotos() {
            NotesHelpers.toastMessage(context, "photos");
            Intent intent=new Intent(context, UserWallPhotos.class);
            context.startActivity(intent);
        }

        private static void getFriends() {
            NotesHelpers.toastMessage(context, "friends");
            Intent intent=new Intent(context, UserWallFriends.class);
            context.startActivity(intent);
        }

    }
}
