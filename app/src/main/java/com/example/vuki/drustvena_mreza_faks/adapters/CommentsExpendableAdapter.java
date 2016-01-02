package com.example.vuki.drustvena_mreza_faks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.models.Comment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Vuki on 28.12.2015..
 */
public class CommentsExpendableAdapter extends ExpandableRecyclerAdapter<CommentsExpendableAdapter.ParentVH, CommentsExpendableAdapter.ChildVH> {
    /*

    NOT IN USE
     */
    Context context;
    private static final int TYPE_PARENT = 0;
    private static final int TYPE_CHILD = 1;


    /**
     * Primary constructor. Sets up {@link #mParentItemList} and {@link #mItemList}.
     * <p/>
     * Changes to {@link #mParentItemList} should be made through add/remove methods in
     * {@link ExpandableRecyclerAdapter}
     *
     * @param parentItemList List of all {@link ParentListItem} objects to be
     *                       displayed in the RecyclerView that this
     *                       adapter is linked to
     */
    public CommentsExpendableAdapter(Context context, List parentItemList) {
        super(parentItemList);
        this.context = context;
    }

    @Override
    public ParentVH onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View v = LayoutInflater.from(parentViewGroup.getContext()).inflate(R.layout.model_comment_parent, parentViewGroup, false);
        return new ParentVH(v);
    }


    @Override
    public ChildVH onCreateChildViewHolder(ViewGroup childViewGroup) {
        View v = LayoutInflater.from(childViewGroup.getContext()).inflate(R.layout.model_comment_child, childViewGroup, false);
        return new ChildVH(v);
    }

    @Override
    public void onBindParentViewHolder(ParentVH parentViewHolder, int position, ParentListItem parentListItem) {

    }

    @Override
    public void onBindChildViewHolder(ChildVH childViewHolder, int position, Object childListItem) {
        Comment crimeChild = (Comment) childListItem;
        childViewHolder.commentUsernamee.setText(crimeChild.getUsername());
        childViewHolder.commentMessage.setText(crimeChild.getMessage());
    }


    protected class ParentVH extends ParentViewHolder implements View.OnClickListener {
        @Bind(R.id.comments_btn)
        Button parentCommentTrigerBtn;

        /**
         * Default constructor.
         *
         * @param itemView The {@link View} being hosted in this ViewHolder
         */
        public ParentVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            parentCommentTrigerBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);


        }

        @Override
        public boolean shouldItemViewClickToggleExpansion() {
            return false;
        }

        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);

           /* if (expanded) {
                mParentDropDownArrow.setRotation(ROTATED_POSITION);
            } else {
                mParentDropDownArrow.setRotation(INITIAL_POSITION);
            }*/
        }
    }

    protected class ChildVH extends ChildViewHolder {
        @Bind(R.id.comment_child_message)
        TextView commentMessage;

        @Bind(R.id.comment_child_username)
        TextView commentUsernamee;

        /**
         * Default constructor.
         *
         * @param itemView The {@link View} being hosted in this ViewHolder
         */
        public ChildVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
