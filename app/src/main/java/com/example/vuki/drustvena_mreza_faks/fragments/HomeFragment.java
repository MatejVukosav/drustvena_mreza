package com.example.vuki.drustvena_mreza_faks.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vuki.drustvena_mreza_faks.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Vuki on 4.11.2015..
 */
public class HomeFragment extends Fragment {

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Bind(R.id.core_home_recyclerView)
    RecyclerView recyclerView;

/*    private  ItemClickListener<Product> itemClickListener=new ItemClickListener<Product>() {
        @Override
        public void onItemClick(Product item) {
            Intent intent=new Intent(getActivity(), ItemDetailActivity.class);
            intent.putExtra(ItemDetailActivity.TAG, item);
            startActivity(intent);
        }
    };*/


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_core_home,container,false);
        ButterKnife.bind(this, v);

        populateRecycler();

        return v;
    }

    private void populateRecycler(){
      /*  List<Product> prod=new ArrayList<>();
        //TODO get products to show in home
        CoreHomeRecyclerViewAdapter adapter=new CoreHomeRecyclerViewAdapter(LoginActivity.productList,itemClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));*/

    }


}
