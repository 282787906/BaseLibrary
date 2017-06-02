package com.lee.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lee.base.R;
import com.lee.base.refreshrecyclerview.BaseHeaderView;
import com.lee.base.refreshrecyclerview.BaseRecyclerViewAdapter;

/**
 * Created by liqg
 * 2016/10/31 13:04
 * Note :
 */
public class MyRecyclerViewAdapter extends BaseRecyclerViewAdapter<Object> {

    OnRecyclerViewListener onRecyclerViewListener;

    protected MyRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayout() {

        return R.layout.recyclerview_item;
    }

    @Override
    public RecyclerView.ViewHolder setItemViewHolder(View itemView) {
        return new RecyclerViewHolder(itemView);
    }

    @Override
    protected View instanceFooterView(Context context) {
        return null;
    }

    @Override
    protected BaseHeaderView instanceHeaderView(Context context) {
        return null;
    }

    @Override
    public void initItemView(RecyclerView.ViewHolder itemHolder, int posion, Object entity) {

         RecyclerViewHolder holder = (RecyclerViewHolder) itemHolder;
        holder.tv1.setText(getDataList().get(posion - 1).toString());

        holder.position = posion;
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int position;
        ImageView iv1;
        TextView tv1;
        private Button right_view;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            iv1 = (ImageView) itemView.findViewById(R.id.iv1);
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            right_view = (Button) itemView.findViewById(R.id.right_view);
            right_view.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            onRecyclerViewListener.OnMenuClick(position);
        }
    }

    public static interface OnRecyclerViewListener {
        void OnItemClick(int position);

        void OnMenuClick(int position);

        void OnItemLongClick(int position);

    }
}