package com.example.vuki.drustvena_mreza_faks.mvp.views;

import com.example.vuki.drustvena_mreza_faks.models.Message;

import java.util.List;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface MessageView {

    void onMessagesReceived(List<Message> messagesList);
    void onMessageListEmpty();
    void onTokenExpired();
}
