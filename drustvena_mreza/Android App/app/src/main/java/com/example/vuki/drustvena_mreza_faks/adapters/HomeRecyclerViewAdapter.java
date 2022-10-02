package com.example.vuki.drustvena_mreza_faks.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.activities.ShowComments;
import com.example.vuki.drustvena_mreza_faks.activities.UserWallFromFriend;
import com.example.vuki.drustvena_mreza_faks.helpers.AdapterHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.BundleKeys;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.RetrofitHelper;
import com.example.vuki.drustvena_mreza_faks.listeners.OnImageClickListener;
import com.example.vuki.drustvena_mreza_faks.models.Bubble;
import com.example.vuki.drustvena_mreza_faks.models.BubblesResponse;
import com.example.vuki.drustvena_mreza_faks.models.HomeFeedOneModel;
import com.example.vuki.drustvena_mreza_faks.models.Post;
import com.example.vuki.drustvena_mreza_faks.models.PostResponse;
import com.example.vuki.drustvena_mreza_faks.models.PostStatusRequest;
import com.example.vuki.drustvena_mreza_faks.models.User;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import java.util.ArrayList;
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

    public static final int TYPE_STATUS_ONLY = 1;
    public static final int TYPE_IMAGE = 2;

    private static final int ADD_NEW_STATUS = 0;

    private static final int GALERY = 2;
    private static final int TIMELINE = 1;
    private static final int CUSTOM_BUBLE = 3;


    private static final int BUBLE_ERROR = 0;
    static OnImageClickListener onImageClickListener;


    public HomeRecyclerViewAdapter(List<HomeFeedOneModel> mData, Context context, OnImageClickListener onImageClickListener) {
        this.mData = mData;
        this.context = context;
        this.onImageClickListener = onImageClickListener;
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
        int postItemPosition = position - 1;
        if (position == 0) {
            return ADD_NEW_STATUS;
        } else if (mData.get(postItemPosition).getContentTypeId() == TYPE_STATUS_ONLY) {
            return TYPE_STATUS_ONLY;
        } else {
            return TYPE_IMAGE;
        }

    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    private static int mBubblesItemId;
    private static List<Bubble> bubbleList = new ArrayList<>();

    private static void getSpinnerItemsApiCall(final Spinner spinner) {

        Call<BubblesResponse> userBubblesCall = ApiManager.getInstance().getService().getUserBubbles();
        userBubblesCall.enqueue(new Callback<BubblesResponse>() {
            @Override
            public void onResponse(Response<BubblesResponse> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    if (response.body().getBubble() != null) {
                        bubbleList = response.body().getBubble();
                        List<String> itemsList = new ArrayList<String>();
                        for (Bubble bubble : bubbleList) {
                            itemsList.add(bubble.getTitle());
                        }
                        String[] items = itemsList.toArray(new String[itemsList.size()]);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                    } else {
                        NotesHelpers.toastMessage(context, "There is no bubbles available");
                    }
                } else {
                    RetrofitHelper.checkCode(response.code(), context);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, "Failure: " + t.getMessage());
                t.printStackTrace();
            }
        });


    }


    @Override
    public void onBindViewHolder(final HomeRecyclerViewAdapter.ViewHolder holder, final int position) {

        //if zero position it is add new post
        if (position == ADD_NEW_STATUS) {
            if (holder.showBubbles != null && holder.addNewStatusButton != null && holder.addNewStatusText != null) {
                // addNewStatusUserImage
                if (ApiManager.getInstance().getUser() != null) {
                    AdapterHelpers.setCircleImage(context, ApiManager.getInstance().getUser().getProfileImage(), holder.addNewStatusUserImage);
                }

                holder.showBubbles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mBubblesItemId = bubbleList.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                holder.addNewStatusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int bubbleId = mBubblesItemId;
                        if (bubbleId != BUBLE_ERROR) {
                            postStatus(bubbleId, holder.addNewStatusText, holder.showBubbles);

                        } else {
                            NotesHelpers.toastMessage(context, "You must choose bubble");
                        }
                    }
                });
            }


        } else {
            final int itemPosition = position - 1;
            final HomeFeedOneModel homeFeedOneModel = mData.get(itemPosition);

            String message = "";
            int numOfLikes;
            int numOfDislikes;
            String createdAt = "";
            String author = "";
            String url = "";

            if (homeFeedOneModel.getAuthor() != null) {
                author = homeFeedOneModel.getAuthor();
            }
            holder.username.setText(author);


            if (homeFeedOneModel.getCreatedAt() != null) {
                createdAt = homeFeedOneModel.getCreatedAt();
            }

            numOfLikes = homeFeedOneModel.getNumOfLikes();
            numOfDislikes = homeFeedOneModel.getNumOfDislikes();


            holder.numOfLikes.setText(String.valueOf(numOfLikes));
            holder.postTime.setText(AdapterHelpers.setTime(createdAt));

            // AdapterHelpers.setCircleImage(context, homeFeedOneModel..getProfileImage(), holder.personal_picture);

            url = homeFeedOneModel.getUserProfilePicture();
            //show user picture
            AdapterHelpers.setCircleImage(context, url, holder.personal_picture);
            //show content_type2

            if (homeFeedOneModel.getContentTypeId() == TYPE_IMAGE) {
                if (holder.itemPicture != null) {
                    url = homeFeedOneModel.getContent();
                    AdapterHelpers.setImageWithGlide(context, url, holder.itemPicture);

                }
                holder.message.setText(homeFeedOneModel.getDescription());
            } else {
                holder.message.setText(homeFeedOneModel.getContent());
            }

            //I didn't like post
            if (homeFeedOneModel.getiLike() == 0) {
                holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.thumb_up_outline_black_24dp), null, null, null);
                holder.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.not_liked));
            } else {
                holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.thumb_up_24dp), null, null, null);
                holder.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.liked));
            }

            holder.username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UserWallFromFriend.class);
                    Bundle b = new Bundle();
                    b.putInt(BundleKeys.FRIEND_USER_ID, mData.get(itemPosition).getAuthorId());
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });

            holder.likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLike(mData.get(itemPosition).getId(), holder, homeFeedOneModel);
                    notifyItemChanged(itemPosition);
                }
            });



     /*   long now = getTimeNow();
        Calendar calYesterday = Calendar.getInstance();
        calYesterday.add(Calendar.DATE, -3);
        long time = calYesterday.getTime().getTime();
        CharSequence relativeTimeSpan = DateUtils.getRelativeTimeSpanString(time, now, 0);
        holder.postTime.setText(relativeTimeSpan);*/


            //AdapterHelpers.setImageWithGlide(context, R.drawable.lisica, holder.personal_picture);


            //sa slikom
      /*  if (post.getContetnTypeId() == TYPE_IMAGE) {
            AdapterHelpers.setImageWithGlide(context, R.drawable.dvorac1, holder.itemPicture);
        }*/
        }

    }

    private static void turnOnLike(HomeFeedOneModel homeFeedOneModel, ViewHolder holder) {
        if (homeFeedOneModel.getiLike() == 1) {
            holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.thumb_up_outline_black_24dp), null, null, null);
            holder.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.not_liked));
            homeFeedOneModel.setiLike(0);
        } else {
            holder.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.thumb_up_24dp), null, null, null);
            holder.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.liked));
            homeFeedOneModel.setiLike(1);
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
                    RetrofitHelper.checkCode(response.code(), context);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, "Failure: " + t.getMessage());
            }
        });


        //   NotesHelpers.toastMessage(context, "lajkoovi");
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
        @Nullable
        @Bind(R.id.spinner_show_bubbles)
        Spinner showBubbles;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if (itemPicture != null) {
                itemPicture.setOnClickListener(this);
            }


            if (commentsBtn != null) {
                commentsBtn.setOnClickListener(this);
            }


            if (showBubbles != null) {

                showBubbles.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            getSpinnerItemsApiCall(showBubbles);
                        }
                        return false;
                    }
                });
            }


        }

        @Override
        public void onClick(View v) {
            int getPostItemPosition = getAdapterPosition() - 1;
            int newPostBubbleid = TIMELINE;
            switch (v.getId()) {
                case R.id.comments_btn:
                    getComments(getPostItemPosition);
                    break;
                case R.id.core_home_item_picture:
                    onImageClickListener.OnImageClick(itemPicture);
                    break;
            }
        }

    }

    private static void getComments(int position) {
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


    /*
       //TODO post status
       POST STATUS
        */
    private static void postStatus(final int bubbleId, final EditText editTextPost, final Spinner spinner) {
        String postMessage = editTextPost.getText().toString();

        PostStatusRequest postStatusRequest = new PostStatusRequest(postMessage);
        Call<PostResponse> postResponseCall = ApiManager.getInstance().getService().postStatus(bubbleId, postStatusRequest);
        postResponseCall.enqueue(new Callback<PostResponse>() {
                                     @Override
                                     public void onResponse(Response<PostResponse> response, Retrofit retrofit) {
                                         if (response.isSuccess()) {
                                             if (response.body().getPost() != null) {
                                                 Post post = response.body().getPost();
                                                 User author = ApiManager.getInstance().getUser();
                                                 HomeFeedOneModel homeFeedOneModel = new HomeFeedOneModel(0, 0, 0, 0, post.getDescription(), post.getContent(), post.getTitle(), post.getUpdatedAt(), post.getCreatedAt(),
                                                         post.getContentTypeId(), author.getUserId(), author.getUsername(), post.getBubbleId(), post.getId());
                                                 mData.add(0, homeFeedOneModel);
                                                 // swap(mData);
                                                 editTextPost.setText("");
                                                 spinner.setSelection(0);
                                                 NotesHelpers.toastMessage(context, "You have added a new post");

                                             } else if (response.body().getError() != null) {
                                                 NotesHelpers.toastMessage(context, response.body().getError());
                                             } else {
                                                 NotesHelpers.toastMessage(context, "Error: " + "response body is empty");
                                             }
                                         } else {
                                             RetrofitHelper.checkCode(response.code(), context);
                                             NotesHelpers.toastMessage(context, "Please choose bubble");
                                         }
                                     }

                                     @Override
                                     public void onFailure(Throwable t) {
                                         NotesHelpers.toastMessage(context, "Failure " + t.getMessage());
                                         t.printStackTrace();
                                     }
                                 }
        );
    }

    public void swap(List<HomeFeedOneModel> datas) {
        mData.clear();
        mData.addAll(datas);
        notifyDataSetChanged();
    }


}
