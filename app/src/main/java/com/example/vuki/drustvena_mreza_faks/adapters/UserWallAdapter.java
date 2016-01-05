package com.example.vuki.drustvena_mreza_faks.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.activities.ShowComments;
import com.example.vuki.drustvena_mreza_faks.activities.UserWallAbout;
import com.example.vuki.drustvena_mreza_faks.activities.UserWallFriends;
import com.example.vuki.drustvena_mreza_faks.activities.UserWallPhotos;
import com.example.vuki.drustvena_mreza_faks.helpers.AdapterHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.BundleKeys;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.models.CheckIfFriends;
import com.example.vuki.drustvena_mreza_faks.models.HomeFeedOneModel;
import com.example.vuki.drustvena_mreza_faks.models.Post;
import com.example.vuki.drustvena_mreza_faks.models.SearchUserRequest;
import com.example.vuki.drustvena_mreza_faks.models.User;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Vuki on 7.11.2015..
 */
public class UserWallAdapter extends RecyclerView.Adapter<UserWallAdapter.ViewHolder> {


    static List<Post> mData;
    private static int i = 0;
    private static int j = 0;
    static Context context;
    static User mUser;
    boolean myWall;


    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_STATUS_ONLY = 1;
    private static final int TYPE_HEADER = 2;

    static boolean isFriends;


    public UserWallAdapter(User user, List<Post> mData, Context context, boolean myWall) {
        this.mData = mData;
        this.context = context;
        this.mUser = user;
        this.myWall = myWall;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;//= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_home_item_picture, parent, false);
        switch (viewType) {
            case TYPE_IMAGE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_home_item_picture, parent, false);
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
        int postItemPosition = position - 1;
        if (position == 0) {
            return 2;
        } else if (mData.get(postItemPosition).getContetnTypeId() == 1) {
            return TYPE_STATUS_ONLY;
        } else {
            return TYPE_IMAGE;
        }
    }


    @Override
    public void onBindViewHolder(final UserWallAdapter.ViewHolder holder, int position) {

        //header item
        if (holder.getItemViewType() == TYPE_HEADER) {
            //AdapterHelpers.setImageWithGlide(context,R.drawable.lisica,holder.userWallPicture);
            holder.userWallUsername.setText(mUser.getUsername());
            AdapterHelpers.setImageWithGlide(context, mUser.getProfileImage(), holder.userWallPicture);

            if (!myWall) {
                if (holder.addAsFriend != null) {
                    holder.addAsFriend.setVisibility(View.VISIBLE);

                    getCheckIfFriendsApiCall(mUser.getUserId(), holder);
                    setAddFriendSettings(isFriends, holder);
                    holder.addAsFriend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isFriends) {
                                removeUserAsFriendApiCall(mUser.getUsername(), mUser.getUserId());
                            } else {
                                addUserAsFriendApiCall(mUser.getUsername(), mUser.getUserId());
                            }
                        }
                    });
                }

            } else {
                if (holder.addAsFriend != null) {
                    holder.addAsFriend.setVisibility(View.GONE);
                }
            }

        } else {

            final int itemPosition = position - 1;
            final Post post = mData.get(itemPosition);
            try {

                holder.message.setText(post.getContent().toString());
                holder.numOfComments.setText("");
                holder.numOfLikes.setText(String.valueOf(post.getNumOfLikes()));
                holder.username.setText(mUser.getUsername());
                holder.postTime.setText(AdapterHelpers.setTime(post.getCreatedAt()));
            }catch (NullPointerException e){
                e.printStackTrace();
            }


            //I didn't like post
            if (post.getiLike() == 0) {
                holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.thumb_up_outline_black_24dp), null, null, null);
                holder.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.not_liked));
            } else {
                holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.thumb_up_24dp), null, null, null);
                holder.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.liked));
            }

            AdapterHelpers.setCircleImage(context, mUser.getProfileImage(), holder.personal_picture);


            holder.likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLike(mData.get(itemPosition).getId(), holder, post);
                    notifyItemChanged(itemPosition);
                }
            });

        }
    }

    private void addUserAsFriendApiCall(String friendUsername, int userId) {
        SearchUserRequest searchUserRequest = new SearchUserRequest(userId);
        Call<Void> addUserAsFriendCall = ApiManager.getInstance().getService().addFriend(searchUserRequest);
        addUserAsFriendCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    NotesHelpers.toastMessage(context, "Congratulations,you have a new friend!");
                    notifyDataSetChanged();
                } else {
                    NotesHelpers.toastMessage(context, context.getResources().getString(R.string.error_something_went_wrong));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, context.getResources().getString(R.string.error_something_is_wrong));

            }
        });
    }

    private void removeUserAsFriendApiCall(final String friendUsername, int userId) {
        SearchUserRequest searchUserRequest = new SearchUserRequest(userId);
        Call<Void> addUserAsFriendCall = ApiManager.getInstance().getService().removeFriend(searchUserRequest);
        addUserAsFriendCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    NotesHelpers.toastMessage(context, "You have removed " + friendUsername + " as friend");
                    notifyDataSetChanged();
                } else {
                    NotesHelpers.toastMessage(context, context.getResources().getString(R.string.error_something_went_wrong));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, context.getResources().getString(R.string.error_something_is_wrong));

            }
        });
    }



    private void getCheckIfFriendsApiCall(int userId, final ViewHolder holder) {

        Call<CheckIfFriends> checkIfFriendsCall = ApiManager.getInstance().getService().checkIfFriends(userId);
        checkIfFriendsCall.enqueue(new Callback<CheckIfFriends>() {
            @Override
            public void onResponse(Response<CheckIfFriends> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    isFriends = response.body().isFriends();
                    setAddFriendSettings(isFriends, holder);
                } else {
                    NotesHelpers.toastMessage(context, "Error: " + "response body is empty");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, "Failure: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }


    private void setAddFriendSettings(boolean friends, ViewHolder holder) {

        if (friends) {
            holder.addAsFriend.setText(context.getResources().getString(R.string.remove_as_friend));
            holder.addAsFriend.setTextColor(ContextCompat.getColor(context, R.color.remove_as_friend_color));
        } else {
            holder.addAsFriend.setText(context.getResources().getString(R.string.add_as_friend));
            holder.addAsFriend.setTextColor(ContextCompat.getColor(context, R.color.add_as_friend_color));
        }
    }


    private static void turnOnLike(Post post, ViewHolder holder) {
        if (post.getiLike() == 1) {
            holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.thumb_up_outline_black_24dp), null, null, null);
            holder.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.not_liked));
            post.setiLike(0);
        } else {
            holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.thumb_up_24dp), null, null, null);
            holder.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.liked));
            post.setiLike(1);
        }
    }
        /*
        ADD LIKE
         */

    private static void setLike(int postId, final ViewHolder holder, final Post post) {

        //TODO send like
        Call<Void> setLikeCall = ApiManager.getInstance().getService().postLike(postId);
        setLikeCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    turnOnLike(post, holder);
                } else {
                    NotesHelpers.toastMessage(context, "Response is not success");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, "Failure: " + t.getMessage());
            }
        });


        //   NotesHelpers.toastMessage(context, "lajkoovi");
    }


    private static void turnOnLike(HomeFeedOneModel homeFeedOneModel, ViewHolder holder) {
        //if liked
        if (homeFeedOneModel.getiLike() == 0) {
            holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.thumb_up_outline_black_24dp), null, null, null);
            homeFeedOneModel.setiLike(1);
            holder.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.not_liked));
            //if not liked
        } else {
            holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.thumb_up_24dp), null, null, null);
            homeFeedOneModel.setiLike(0);
            holder.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.liked));
        }
    }

    private static void setLike(int postId, final ViewHolder holder, final HomeFeedOneModel homeFeedOneModel) {
        //TODO send like
        Call<Void> setLikeCall = ApiManager.getInstance().getService().postLike(postId);
        setLikeCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    turnOnLike(homeFeedOneModel, holder);
                } else {
                    NotesHelpers.toastMessage(context, "Response is not success");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, "Failure: " + t.getMessage());
            }
        });


        // NotesHelpers.toastMessage(context, "lajkoovi");
    }

    private long getTimeNow() {
        Calendar calNow = Calendar.getInstance();
        calNow.add(Calendar.DATE, 0);
        return calNow.getTime().getTime();

    }


    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Nullable
        @Bind(R.id.user_wall_addAsFriend)
        TextView addAsFriend;

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
            int getPostItemPosition = getAdapterPosition() - 1;
            int getCoverPosition = 0;
            switch (v.getId()) {
                case R.id.comments_btn:
                    getComments(getPostItemPosition);
                    break;
                case R.id.user_wall_about:
                    getAbout(mUser.getUserId());
                    break;
                case R.id.user_wall_photos:
                    getPhotos(mUser.getUserId());
                    break;
                case R.id.user_wall_friends:
                    getFriends(mUser.getUserId());
                    break;
            }
        }


        private static void getComments(int position) {
            int contentId = mData.get(position).getId();
            Bundle b = new Bundle();
            b.putInt(BundleKeys.COMMENT, contentId);
            Intent intent = new Intent(context, ShowComments.class);
            intent.putExtras(b);
            context.startActivity(intent);
        }


        private static void getAbout(int userId) {
            //NotesHelpers.toastMessage(context, "about");
            Intent intent = new Intent(context, UserWallAbout.class);

            Bundle b = new Bundle();
            b.putInt(BundleKeys.USER_ABOUT, userId);
            intent.putExtras(b);
            context.startActivity(intent);
        }

        private static void getPhotos(int userId) {
            // NotesHelpers.toastMessage(context, "Photos");
            Bundle b = new Bundle();
            Intent intent = new Intent(context, UserWallPhotos.class);
            b.putInt(BundleKeys.FRIEND_USER_ID, userId);
            intent.putExtras(b);
            context.startActivity(intent);
        }

        private static void getFriends(int userId) {
            // NotesHelpers.toastMessage(context, "friends");
            Intent intent = new Intent(context, UserWallFriends.class);
            Bundle b = new Bundle();
            b.putInt(BundleKeys.FRIEND_USER_ID, userId);
            intent.putExtras(b);
            context.startActivity(intent);
        }

    }
}
