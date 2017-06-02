package com.lee.base.refreshlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqg
 * 2015/11/18 10:52
 * Note :
 */
public class MyBaseAdapter extends BaseAdapter {
    protected LayoutInflater layoutInflater;
    protected List<Object> data;
    protected Context context;

    public MyBaseAdapter(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        data = new ArrayList<>();
    }

    public void setData(List<Object> data) {
        this.data = data;
        notifyDataSetChanged();
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
