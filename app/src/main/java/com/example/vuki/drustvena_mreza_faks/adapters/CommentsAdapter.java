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
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.models.Comment;
import com.example.vuki.drustvena_mreza_faks.models.User;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_add_status, parent, false);
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
            holder.addNewStatusText.getText().toString();
        }
        User user = ApiManager.getInstance().getUser();
        int userId = user.getUserId();
        String firstName = user.getFirstName();
        String middleName = user.getMiddleName();
        String lastName = user.getLastName();
        String username = user.getUsername();

        //TODO komentar se ne posta dobro jer po dokumentaciji request je samo id?? a od kud poruka dode
        //

        final Comment comment = new Comment(userId, commentMessage, firstName, lastName, middleName, username);

        Call<Void> sendCommentCall = ApiManager.getInstance().getService().postComment(mContentId);
        sendCommentCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    mCommentList.add(comment);
                    holder.addNewStatusText.setText("");
                    NotesHelpers.toastMessage(context, "You have posted comment ");

                } else {
                    NotesHelpers.toastMessage(context, "Response is not success ");
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
