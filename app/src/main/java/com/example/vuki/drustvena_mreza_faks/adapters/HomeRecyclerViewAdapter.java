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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.activities.ShowComments;
import com.example.vuki.drustvena_mreza_faks.activities.UserWallFromFriend;
import com.example.vuki.drustvena_mreza_faks.helpers.AdapterHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.BundleKeys;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.models.HomeFeedOneModel;
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
 * Created by vuki on 18.10.15..
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {


    static List<HomeFeedOneModel> mData;
    private static int i = 0;
    private static int j = 0;
    static Context context;


    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_STATUS_ONLY = 0;

    private static final int ADD_NEW_STATUS = 2;


    public HomeRecyclerViewAdapter(List<HomeFeedOneModel> mData, Context context) {
        this.mData = mData;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;//= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_home_item_picture, parent, false);
        switch (viewType) {
            case TYPE_STATUS_ONLY:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_home_item_status, parent, false);
                break;
            case TYPE_IMAGE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_home_item_picture, parent, false);
                break;
            case ADD_NEW_STATUS:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_add_status, parent, false);
                break;
            default:
                v = new View(context);
        }

        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ADD_NEW_STATUS;
        } else {
            return TYPE_STATUS_ONLY;
        }

    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }


    @Override
    public void onBindViewHolder(final HomeRecyclerViewAdapter.ViewHolder holder, final int position) {

        //if zero position it is add new post
        if (position == ADD_NEW_STATUS - 2) {
            if (false) {
                AdapterHelpers.setImage(context, R.drawable.dvorac1, holder.addNewStatusUserImage);
            }

        } else {
            final int itemPosition = position - 1;
            final HomeFeedOneModel homeFeedOneModel = mData.get(itemPosition);

            String message = "";
            int numOfLikes;
            int numOfDislikes;
            String createdAt = "";
            String author = "";

            if (homeFeedOneModel.getAuthor() != null) {
                author = homeFeedOneModel.getAuthor();
            }
            holder.username.setText(author);

            if (homeFeedOneModel.getContent() != null) {
                message = homeFeedOneModel.getContent();
            }

            if (homeFeedOneModel.getCreatedAt() != null) {
                createdAt = homeFeedOneModel.getCreatedAt();
            }

            numOfLikes = homeFeedOneModel.getNumOfLikes();
            numOfDislikes = homeFeedOneModel.getNumOfDislikes();

            holder.message.setText(message);
            holder.numOfLikes.setText(String.valueOf(numOfLikes));
            holder.postTime.setText(createdAt);

            //I didn't like post
            if (homeFeedOneModel.getiLike() == 0) {
                holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.thumb_up_outline_black_24dp), null, null, null);
                holder.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.not_liked));
            } else {
                holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.thumb_up_24dp), null, null, null);
                holder.likeBtn.setTextColor(ContextCompat.getColor(context,R.color.liked));
            }

                       holder.username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, UserWallFromFriend.class);
                    Bundle b=new Bundle();
                    b.putInt(BundleKeys.FRIEND_USER_ID,mData.get(itemPosition).getAuthorId());
                    context.startActivity(intent);
                }
            });

            holder.likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLike(mData.get(position).getId(), holder, homeFeedOneModel);
                    notifyItemChanged(position);
                }
            });


     /*   long now = getTimeNow();
        Calendar calYesterday = Calendar.getInstance();
        calYesterday.add(Calendar.DATE, -3);
        long time = calYesterday.getTime().getTime();
        CharSequence relativeTimeSpan = DateUtils.getRelativeTimeSpanString(time, now, 0);
        holder.postTime.setText(relativeTimeSpan);*/


            //AdapterHelpers.setImage(context, R.drawable.lisica, holder.personal_picture);


            //sa slikom
      /*  if (post.getContetnTypeId() == TYPE_IMAGE) {
            AdapterHelpers.setImage(context, R.drawable.dvorac1, holder.itemPicture);
        }*/
        }

    }

    private static void turnOnLike(HomeFeedOneModel homeFeedOneModel, ViewHolder holder) {
        if (homeFeedOneModel.getiLike() == 0) {
            holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.thumb_up_outline_black_24dp), null, null, null);
            holder.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.not_liked));
            homeFeedOneModel.setiLike(1);
        } else {
            holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.thumb_up_24dp), null, null, null);
            holder.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.liked));
            homeFeedOneModel.setiLike(0);
        }

    }
        /*
        ADD LIKE
         */

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


        NotesHelpers.toastMessage(context, "lajkoovi");
    }

    private long getTimeNow() {
        Calendar calNow = Calendar.getInstance();
        calNow.add(Calendar.DATE, 0);
        return calNow.getTime().getTime();

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
        @Bind(R.id.add_new_status)
        EditText addNewStatusText;

        @Nullable
        @Bind(R.id.add_new_status_btn)
        Button addNewStatusButton;

        @Nullable
        @Bind(R.id.comment_list_recycler_view)
        RecyclerView commentRecyclerView;

        @Nullable
        @Bind(R.id.add_new_status_user_image)
        CircleImageView addNewStatusUserImage;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if (likeBtn != null) {
                likeBtn.setOnClickListener(this);
            }
            if (commentsBtn != null) {
                commentsBtn.setOnClickListener(this);
            }
            if (addNewStatusButton != null) {
                addNewStatusButton.setOnClickListener(this);
            }


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.comments_btn:
                    getComments(getAdapterPosition());
                    break;

                case R.id.add_new_status_btn:
                    postStatus(addNewStatusText.getText().toString());
                    break;
            }
        }
    }

    private static void getComments(int position) {
        //TODO koji id je dobar?
        int contentId = mData.get(position).getId();

        Bundle b = new Bundle();
        b.putInt(BundleKeys.COMMENT, contentId);
        Intent intent = new Intent(context, ShowComments.class);
        intent.putExtras(b);
        context.startActivity(intent);





     /*   List<ParentListItem> parentListItems = new ArrayList<>();
        ChildItem childItem=new ChildItem();
        childItem.setComments(MockUsers.getComments(5));
        parentListItems.add(childItem);

        //fill comment list
        CommentsExpendableAdapter commentsExpendableAdapter=new CommentsExpendableAdapter(context,parentListItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(commentsExpendableAdapter);

        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.model_comment_parent, null);
        ButterKnife.findById(linearLayout, R.layout.model_comment_parent);
        Button button = ButterKnife.findById(linearLayout, R.id.comments_btn);
*/
        // button.performClick();
    }


    private static void postStatus(String postMessage) {
        /*
        //TODO post status
        POST STATUS
         */
        NotesHelpers.toastMessage(context, "novi_statuuus " + postMessage);

    }

}
