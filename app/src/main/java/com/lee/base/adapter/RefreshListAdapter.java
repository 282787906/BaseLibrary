package com.lee.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lee.base.R;
import com.lee.base.refreshlistview.MyBaseAdapter;

public class RefreshListAdapter extends MyBaseAdapter {


    public RefreshListAdapter(Context context, LayoutInflater layoutInflater) {
        super(context, layoutInflater);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.refresh_listview_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((String)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(String object, ViewHolder holder) {
       holder.listItemTvName.setText(object);
    }

    protected class ViewHolder {
        private TextView listItemTvName;

        public ViewHolder(View view) {
            listItemTvName = (TextView) view.findViewById(R.id.list_item_tv_Name);
        }
    }
}
