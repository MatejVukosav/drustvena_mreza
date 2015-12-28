package com.example.vuki.drustvena_mreza_faks.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.adapters.SearchUserAdapter;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SparseItemRemoveAnimator;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Vuki on 4.11.2015..
 */
public abstract class SearchActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener
        , OnMoreListener
        , SwipeDismissRecyclerViewTouchListener.DismissCallbacks {

    private SearchUserAdapter mAdapter;
    private SparseItemRemoveAnimator mSparseAnimator;
    private RecyclerView.LayoutManager mLayoutManager;
    private Handler mHandler;
    ArrayList<String> list = new ArrayList<>();

    @Bind(R.id.discoverRecyclerView)
    SuperRecyclerView mRecycler;

    public static String followS;
    public static String unfollowS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);





        followS = getResources().getString(R.string.follow);
        unfollowS = getResources().getString(R.string.unfollow);

        mHandler = new Handler(Looper.getMainLooper());

    /*    for (int i = 0; i < 25; i++)
         list.add("stvar" + i);*/

       // mAdapter = new SearchUserAdapter(this, LoginActivity.users);
       // mRecycler.setAdapter(mAdapter);

        mLayoutManager = getLayoutManager();
        mRecycler.setLayoutManager(mLayoutManager);

        boolean dismissEnabled = isSwipeToDismissEnabled();
        if (dismissEnabled) {
            mRecycler.setupSwipeToDismiss(this);
            mSparseAnimator = new SparseItemRemoveAnimator();
            mRecycler.getRecyclerView().setItemAnimator(mSparseAnimator);
        }


        mRecycler.setRefreshListener(this);
        mRecycler.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        mRecycler.setupMoreListener(this, 1);



      /*  mRecycler.addOnItemTouchListener(new RecyclerUtils.RecyclerItemClickListener(this, new RecyclerUtils.RecyclerItemClickListener.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NotesHelper.toastMessage(getApplicationContext(),"pozicija"+position);
            }
        }));*/


    }


    protected abstract int getLayoutId();

    protected abstract boolean isSwipeToDismissEnabled();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
/*
        mHandler.postDelayed(new Runnable() {
            public void run() {
                // mAdapter.add("New stuff");
            }
        }, 2000);*/
    }

    static int i = 10;

    @Override
    public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
        Snackbar.make(findViewById(R.id.discoveriMainlayout), "Loading..", Snackbar.LENGTH_SHORT);
        // Toast.makeText(this, "Loading..", Toast.LENGTH_LONG).show();
        i++;
        mHandler.postDelayed(new Runnable() {
            public void run() {
                //  for (int i = 0; i < 5; i++)
                //  mAdapter.add(LoginActivity.user2);
            }
        }, 300);
    }

    @Override
    public boolean canDismiss(int position) {
        return true;
    }

    @Override
    public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            mSparseAnimator.setSkipNext(true);
            mAdapter.remove(position);
        }

        if (mAdapter.getItemCount() == 0) {

        }

    }



}