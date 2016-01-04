package com.example.vuki.drustvena_mreza_faks.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.models.User;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Vuki on 6.11.2015..
 */
public class SearchAdapter extends BaseAdapter implements Filterable {

    private ArrayList<String> data;

    private String[] typeAheadData;
    List<User> mUsersList;

    LayoutInflater inflater;
    Context context;

    public SearchAdapter(Context context,List<User> usersList) {
        inflater = LayoutInflater.from(context);
        data = new ArrayList<String>();
        this.mUsersList=usersList;

       // typeAheadData=usernames.toArray(new String[usernames.size()]);//context.getResources().getStringArray(R.array.state_array_full);*/
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (!TextUtils.isEmpty(constraint)) {
                    // Retrieve the autocomplete results.
                    List<String> searchData = new ArrayList<>();

                    for (User user : mUsersList) {
                        String str=user.getUsername();
                        if (str.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            searchData.add(str);
                        }
                    }

                    // Assign the data to the FilterResults
                    filterResults.values = searchData;
                    filterResults.count = searchData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null) {
                    data = (ArrayList<String>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
        return filter;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        String currentListData = (String) getItem(position);

        mViewHolder.textView.setText(currentListData);

        return convertView;
    }


    private class MyViewHolder {
        TextView textView;

        public MyViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(android.R.id.text1);
        }
    }
}

