package com.example.vuki.drustvena_mreza_faks.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.models.HomeFeedOneModel;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vuki on 18.10.15..
 */
public class CoreHomeRecyclerViewAdapter extends RecyclerView.Adapter<CoreHomeRecyclerViewAdapter.ViewHolder> {


    static List<HomeFeedOneModel> data;
    private static int i = 0;
    private static int j = 0;
    static Context context;


    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_STATUS_ONLY = 0;


    public CoreHomeRecyclerViewAdapter(List<HomeFeedOneModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;//= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_home_item, parent, false);
        switch (viewType) {
            case TYPE_STATUS_ONLY:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_home_item_status, parent, false);
                break;
            case TYPE_IMAGE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_home_item, parent, false);
                break;
            default:
                v = new View(context);
        }

        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        //if content type = 0 it is post
     /*   if (data.get(position).getContentTypeId() == 0) {
            return 0;
        } else {
            return 1;
        }*/

        return 0;

    }


    @Override
    public void onBindViewHolder(CoreHomeRecyclerViewAdapter.ViewHolder holder, int position) {
        HomeFeedOneModel homeFeedOneModel = data.get(position);

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
        @Bind(R.id.core_home_username)
        TextView username;
        @Bind(R.id.core_home_user_personal_picture)
        CircleImageView personal_picture;

        @Bind(R.id.core_home_num_of_likes)
        TextView numOfLikes;
        @Bind(R.id.like_btn)
        Button likeBtn;

        @Bind(R.id.core_home_comments)
        TextView numOfComments;
        @Bind(R.id.comments_btn)
        Button commentsBtn;

        @Bind(R.id.core_home_message)
        TextView message;

        @Bind(R.id.core_home_post_time)
        TextView postTime;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if (likeBtn != null) {
                likeBtn.setOnClickListener(this);
            }
            if (commentsBtn != null) {
                commentsBtn.setOnClickListener(this);
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
            }
        }
    }

    private static void getComments() {
        NotesHelpers.toastMessage(context, "komentari");
    }

    private static void getLike() {
        NotesHelpers.toastMessage(context, "lajkoovi");
    }

}
