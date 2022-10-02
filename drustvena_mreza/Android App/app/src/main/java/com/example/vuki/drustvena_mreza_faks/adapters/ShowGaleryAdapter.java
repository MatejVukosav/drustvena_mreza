package com.example.vuki.drustvena_mreza_faks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.helpers.AdapterHelpers;
import com.example.vuki.drustvena_mreza_faks.models.Post;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Vuki on 28.12.2015..
 */
public class ShowGaleryAdapter extends RecyclerView.Adapter<ShowGaleryAdapter.ViewHolder> {

    static List<Post> photos;
    static Context context;



    public ShowGaleryAdapter(Context context, List<Post> photos) {
        this.photos = photos;
        this.context = context;
    }

    @Override
    public ShowGaleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_galery_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ShowGaleryAdapter.ViewHolder holder, final int position) {
        Post post = photos.get(position);
        AdapterHelpers.setImageWithGlide(context, post.getContent(), holder.galeryitem);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    public void remove(int position) {
        photos.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = photos.size();
        photos.clear();
        notifyItemRangeRemoved(0, size);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.galery_item_image_view)
        ImageView galeryitem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}