package com.netease.demo.abtest.second;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.netease.demo.abtest.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zyl06 on 2018/9/16.
 */

public class ShoppingCartFragment extends Fragment {
    private static final int DATASET_COUNT = 60;
    protected List<String> mDataset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shoppingcart, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listview);

        initDataset();
        listView.setAdapter(new InnerListAdapter(mDataset));

        return rootView;
    }

    private void initDataset() {
        mDataset = new ArrayList<String>(DATASET_COUNT);
        for (int i = 0; i < DATASET_COUNT; i++) {
            mDataset.add("This is element #" + i);
        }
    }

    private static class InnerListAdapter extends BaseAdapter {

        private List<String> mData = new LinkedList<>();

        public InnerListAdapter(List<String> data) {
            if (data != null) {
                mData = data;
            }
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = (getItemViewType(position) == 0) ?
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_1, parent, false) :
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_2, parent, false);
            }

            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder();
                viewHolder.textView = convertView.findViewById(R.id.text_view);
                convertView.setTag(viewHolder);
            }

            String data = (String) getItem(position);
            viewHolder.textView.setText(data);

            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            return position % 2;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public boolean isEmpty() {
            return mData.isEmpty();
        }
    }

    private static class ViewHolder {
        public TextView textView;
    }
}
