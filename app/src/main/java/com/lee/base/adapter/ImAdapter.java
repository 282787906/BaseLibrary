package com.lee.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lee.base.R;
import com.lee.base.module.IM_Msg;

import java.util.List;

/**
 * Created by liqg
 * 2016/9/18 14:59
 * Note :
 */
public class ImAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<IM_Msg> data;
    OnRecyclerViewListener onRecyclerViewListener;
    Context mContext;


    public ImAdapter(Context context, List<IM_Msg> data, OnRecyclerViewListener onRecyclerViewListener) {
        this.data = data;
        this.onRecyclerViewListener = onRecyclerViewListener;
        mContext = context;
    }

    public void setData(List<IM_Msg> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == IM_Msg.MsgType.TEXT.ordinal()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_im_text, null);
            return new ViewHolderText(view);
        }else if(viewType == IM_Msg.MsgType.RED_PACKET.ordinal()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_im_redpacket, null);
            return new ViewHolderRedBacket(view);

        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getMsgType().ordinal();

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderText) {
            ((ViewHolderText) holder).bindData(data.get(position), position);
        } else if (holder instanceof ViewHolderRedBacket) {
            ((ViewHolderRedBacket) holder).bindData(data.get(position), position);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected class ViewHolderText extends ImViewHolder implements View.OnClickListener, View.OnLongClickListener {
        protected RelativeLayout itemImLeftRl;
        protected TextView itemImLeftTvMsg;
        protected RelativeLayout itemImRightRl;
        protected TextView itemImRightTvMsg;

        public ViewHolderText(View view) {
            super(view);

            itemImLeftRl = (RelativeLayout) view.findViewById(R.id.item_im_left_rl);
            itemImLeftTvMsg = (TextView) view.findViewById(R.id.item_im_left_tv_msg);
            itemImRightRl = (RelativeLayout) view.findViewById(R.id.item_im_right_rl);
            itemImRightTvMsg = (TextView) view.findViewById(R.id.item_im_right_tv_msg);

            itemImLeftTvMsg.setOnClickListener(this);
            itemImLeftTvMsg.setOnLongClickListener(this);
            itemImRightTvMsg.setOnClickListener(this);
            itemImRightTvMsg.setOnLongClickListener(this);
        }

        public void bindData(IM_Msg im_msg, int position) {
            itemImTvTime.setText(im_msg.getDate());
            if (im_msg.getFromType() == IM_Msg.FromType.SEND) {
                itemImRightTvMsg.setText(im_msg.getMsg());
                itemImLeftRl.setVisibility(View.GONE);
                itemImRightRl.setVisibility(View.VISIBLE);
            } else {
                itemImLeftTvMsg.setText(im_msg.getMsg());
                itemImLeftRl.setVisibility(View.VISIBLE);
                itemImRightRl.setVisibility(View.GONE);
            }
            //        Glide.with(mContext)
            //                .load(data.get(position).getFace())
            //                .error(R.drawable.loading)
            //                .placeholder(R.mipmap.ic_launcher)
            //                .centerCrop().transform(new GlideCircleTransform(mContext))
            //                .into(holder1.personFace);
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            switch (view.getId()) {

                case R.id.item_im_left_tv_msg:
                    if (onRecyclerViewListener != null) {
                        onRecyclerViewListener.OnItemClick(position);
                    }
                    break;
                case R.id.item_im_right_tv_msg:
                    if (onRecyclerViewListener != null) {
                        onRecyclerViewListener.OnItemClick(position);
                    }
                    break;

            }

        }

        @Override
        public boolean onLongClick(View view) {
            switch (view.getId()) {
                case R.id.item_im_left_tv_msg:
                    if (onRecyclerViewListener != null) {
                        onRecyclerViewListener.OnItemLongClick(position);
                    }
                    return true;

                case R.id.item_im_right_tv_msg:
                    if (onRecyclerViewListener != null) {
                        onRecyclerViewListener.OnItemLongClick(position);
                    }
                    return true;

            }

            return false;
        }
    }

    protected class ViewHolderRedBacket extends ImViewHolder implements View.OnClickListener, View.OnLongClickListener {
        protected RelativeLayout itemImLeftRl;
        protected TextView itemImLeftTvMsg;
        protected RelativeLayout itemImRightRl;
        protected TextView itemImRightTvMsg;

        public ViewHolderRedBacket(View view) {
            super(view);

            itemImLeftRl = (RelativeLayout) view.findViewById(R.id.item_im_left_rl);
            itemImRightRl = (RelativeLayout) view.findViewById(R.id.item_im_right_rl);

            //            itemImLeftTvMsg.setOnClickListener(this);
            //            itemImLeftTvMsg.setOnLongClickListener(this);
            //            itemImRightTvMsg.setOnClickListener(this);
            //            itemImRightTvMsg.setOnLongClickListener(this);
        }

        public void bindData(IM_Msg im_msg, int position) {
            itemImTvTime.setText(im_msg.getDate());
            if (im_msg.getFromType() == IM_Msg.FromType.SEND) {

                itemImLeftRl.setVisibility(View.GONE);
                itemImRightRl.setVisibility(View.VISIBLE);
            } else {
                itemImLeftRl.setVisibility(View.VISIBLE);
                itemImRightRl.setVisibility(View.GONE);
            }

            this.position = position;
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            //            switch (view.getId()) {
            //
            //                case R.id.item_im_left_tv_msg:
            //                    if (onRecyclerViewListener != null) {
            //                        onRecyclerViewListener.OnItemClick(position);
            //                    }
            //                    break;
            //                case R.id.item_im_right_tv_msg:
            //                    if (onRecyclerViewListener != null) {
            //                        onRecyclerViewListener.OnItemClick(position);
            //                    }
            //                    break;
            //
            //            }

        }

        @Override
        public boolean onLongClick(View view) {
            //            switch (view.getId()) {
            //                case R.id.item_im_left_tv_msg:
            //                    if (onRecyclerViewListener != null) {
            //                        onRecyclerViewListener.OnItemLongClick(position);
            //                    }
            //                    return true;
            //
            //                case R.id.item_im_right_tv_msg:
            //                    if (onRecyclerViewListener != null) {
            //                        onRecyclerViewListener.OnItemLongClick(position);
            //                    }
            //                    return true;
            //
            //            }

            return false;
        }
    }

    protected class ImViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected int position;
        protected ImageView itemImLeftIvFace;
        protected ImageView itemImRightIvFace;
        protected TextView itemImTvTime;


        public ImViewHolder(View view) {
            super(view);

            itemImLeftIvFace = (ImageView) view.findViewById(R.id.item_im_left_iv_face);
            itemImRightIvFace = (ImageView) view.findViewById(R.id.item_im_right_iv_face);

            itemImTvTime = (TextView) view.findViewById(R.id.item_im_tv_time);
            itemImLeftIvFace.setOnClickListener(this);
            itemImRightIvFace.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.item_im_left_iv_face:
                    if (onRecyclerViewListener != null) {
                        onRecyclerViewListener.OnHeadClick(position);
                    }
                    break;
                case R.id.item_im_right_iv_face:
                    if (onRecyclerViewListener != null) {
                        onRecyclerViewListener.OnHeadClick(position);
                    }
                    break;

            }

        }

    }

    public static interface OnRecyclerViewListener {
        void OnItemClick(int position);

        void OnItemLongClick(int position);

        void OnHeadClick(int position);

    }
}
