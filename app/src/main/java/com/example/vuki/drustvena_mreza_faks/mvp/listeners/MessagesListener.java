package com.example.vuki.drustvena_mreza_faks.mvp.listeners;

import com.example.vuki.drustvena_mreza_faks.models.Message;

import java.util.List;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface MessagesListener {
    void onMessagesReceived(List<Message> messages);
    void onError(String error);
    void onTokenExpired();
}
