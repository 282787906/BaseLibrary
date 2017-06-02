package com.lee.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lee.base.R;
import com.lee.base.glide.GlideCircleTransform;
import com.lee.base.module.Person;

import java.util.List;

/**
 * Created by liqg
 * 2016/9/18 14:59
 * Note :
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter {
    protected List<Person> data;
    OnRecyclerViewListener onRecyclerViewListener;
Context mContext;


    public RecyclerViewAdapter(Context context,List<Person> data, OnRecyclerViewListener onRecyclerViewListener) {
        this.data = data;
        this.onRecyclerViewListener = onRecyclerViewListener;
        mContext=context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, null);

        return new TestViewHolder(convertView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TestViewHolder testHolder = (TestViewHolder) holder;
        testHolder.personName.setText(data.get(position).getName());
        testHolder.personSign.setText(data.get(position).getSign());
        Glide.with(mContext)
                .load(data.get(position).getFace())
                .error(R.drawable.loading)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop().transform(new GlideCircleTransform(mContext))
                .into(testHolder.personFace);
        testHolder.position = position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    protected class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private int position;
        private ImageView personFace;
        private TextView personName;
        private TextView personSign;
        public TestViewHolder(View view) {
            super(view);
            personFace = (ImageView) view.findViewById(R.id.person_face);
            personName = (TextView) view.findViewById(R.id.person_name);
            personSign = (TextView) view.findViewById(R.id.person_sign);
            view.findViewById(R.id.content_view).setOnClickListener(this);
            view.findViewById(R.id.add). setOnClickListener(this);
            view.findViewById(R.id.edit). setOnClickListener(this);
            view.findViewById(R.id.change). setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.content_view:
                    if (onRecyclerViewListener != null) {
                        onRecyclerViewListener.OnItemClick(position);
                    }
                    break;
                case R.id.add:
                    if (onRecyclerViewListener != null) {
                        onRecyclerViewListener.OnMenuAddClick(position);
                    }
                    break;
                case R.id.edit:
                    if (onRecyclerViewListener != null) {
                        onRecyclerViewListener.OnMenuEditClick(position);
                    }
                    break;
                case R.id.change:
                    if (onRecyclerViewListener != null) {
                        onRecyclerViewListener.OnMenuChangeClick(position);
                    }
                    break;
            }

        }

        @Override
        public boolean onLongClick(View view) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.OnItemLongClick(position);
                return true;
            }
            return false;
        }
    }

    public static interface OnRecyclerViewListener {
        void OnItemClick(int position);
        void OnMenuAddClick(int position);
        void OnMenuEditClick(int position);
        void OnMenuChangeClick(int position);
        void OnItemLongClick(int position);

    }
}
