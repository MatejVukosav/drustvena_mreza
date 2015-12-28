package com.example.vuki.drustvena_mreza_faks.mvp.views;

import com.example.vuki.drustvena_mreza_faks.models.Comment;

import java.util.List;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface CommentView {
    void onCommentsReceived(List<Comment> commentList);
    void onCommentListEmpty();
    void onTokenExpired();
}
