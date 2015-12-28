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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Vuki on 28.12.2015..
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    static Context context;
    static List<Comment> commentList;

    private final int ADD_NEW_COMMENT = 1;
    private final int SHOW_COMMENT = 0;

    public CommentsAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
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
        if (position != commentList.size()) {
            return SHOW_COMMENT;
        } else {
            return ADD_NEW_COMMENT;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (commentList.size() != position) {
            Comment comment = commentList.get(position);
            holder.commentUsernamee.setText(comment.getUsername());
            holder.commentMessage.setText(comment.getMessage());
        } else {
            //add comment holder
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size() + 1;
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
                    postComment(addNewStatusText.getText().toString());
                    break;
            }
        }

        private void postComment(String commentMessage) {
            /*

            SEND COMMENT

             */
            NotesHelpers.toastMessage(context, "NOVI_KOMENTAR " + commentMessage);

        }
    }
}
