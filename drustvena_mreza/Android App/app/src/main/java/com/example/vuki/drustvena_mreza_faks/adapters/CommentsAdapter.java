package com.example.vuki.drustvena_mreza_faks.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.helpers.AdapterHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.RetrofitHelper;
import com.example.vuki.drustvena_mreza_faks.models.Comment;
import com.example.vuki.drustvena_mreza_faks.models.CommentResponse;
import com.example.vuki.drustvena_mreza_faks.models.PostCommentRequest;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Vuki on 28.12.2015..
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    static Context context;
    static List<Comment> mCommentList;

    private final int ADD_NEW_COMMENT = 1;
    private final int SHOW_COMMENT = 0;

    static int mContentId;

    public CommentsAdapter(Context context, List<Comment> mCommentList, int mContentId) {
        this.context = context;
        this.mCommentList = mCommentList;
        this.mContentId = mContentId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case SHOW_COMMENT:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_comment_child, parent, false);
                return new ViewHolder(v);
            default:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_add_new_comment, parent, false);
                return new ViewHolder(v2);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position != mCommentList.size()) {
            return SHOW_COMMENT;
        } else {
            return ADD_NEW_COMMENT;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (mCommentList.size() != position) {
            Comment comment = mCommentList.get(position);
            holder.commentUsernamee.setText(comment.getUsername());
            holder.commentMessage.setText(comment.getMessage());
            holder.commentPostTime.setText(AdapterHelpers.setTime(comment.getCreatedAt()));
            //set user profile picture
            AdapterHelpers.setCircleImage(context, comment.getAvatar(), holder.commentUserProfilePicture);

        } else {
            //add comment holder (post comment is on bottom)
            if (holder.addNewStatusButton != null) {
                holder.addNewStatusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postComment(holder);
                    }
                });
            }
        }
    }

    /*
         SEND COMMENT
          */
    private void postComment(final ViewHolder holder) {

        String commentMessage = "";
        if (holder.addNewStatusText.getText() != null) {
            commentMessage = holder.addNewStatusText.getText().toString();
        }


        //TODO komentar se ne posta dobro jer po dokumentaciji request je samo id?? a od kud poruka dode
        //
        PostCommentRequest postCommentRequest = new PostCommentRequest(commentMessage);

        Call<CommentResponse> sendCommentCall = ApiManager.getInstance().getService().postComment(mContentId, postCommentRequest);
        sendCommentCall.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Response<CommentResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getComment() != null) {
                        Comment comment=response.body().getComment();
                        comment.setUsername(ApiManager.getInstance().getUser().getUsername());
                        mCommentList.add(comment);
                        //TODO ulijepsat prikaz komentara
                        notifyDataSetChanged();
                        holder.addNewStatusText.setText("");
                        NotesHelpers.toastMessage(context, "You have posted comment ");
                    } else {
                        NotesHelpers.toastMessage(context, "Error: response body is empty ");
                    }
                } else {
                    RetrofitHelper.checkCode(response.code(),context );
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, "Failure " + t.getMessage());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mCommentList.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Nullable
        @Bind(R.id.comment_child_user_personal_picture)
        CircleImageView commentUserProfilePicture;

        @Nullable
        @Bind(R.id.comment_child_post_time)
        TextView commentPostTime;


        @Nullable
        @Bind(R.id.comment_child_message)
        TextView commentMessage;
        @Nullable
        @Bind(R.id.comment_child_username)
        TextView commentUsernamee;

        @Nullable
        @Bind(R.id.add_new_status)
        EditText addNewStatusText;

        @Nullable
        @Bind(R.id.add_new_status_btn)
        Button addNewStatusButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if (addNewStatusButton != null) {
                addNewStatusButton.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_new_status_btn:
                    break;
            }
        }


    }
}
