package com.example.vuki.drustvena_mreza_faks.models;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vuki on 28.12.2015..
 */
public class ChildItem implements ParentListItem {
    List<Comment> comments=new ArrayList<>();

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public List<Comment> getChildItemList() {
        return comments;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
