package com.lee.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lee.base.R;
import com.lee.base.refreshlistview.MyBaseAdapter;

public class FrescoAdapter extends MyBaseAdapter {


    public FrescoAdapter(Context context, LayoutInflater layoutInflater) {
        super(context, layoutInflater);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.fresco_listview_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((String )getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(String uriString, ViewHolder holder) {
        //TODO implement
        DraweeController draweeController= Fresco.newDraweeControllerBuilder()
                .setUri(uriString)
                .build();
        holder.frescoListItemSdvIMG.setController(draweeController);
        holder.frescoListItemTvName.setText(uriString);
        Glide.with(context)
                .load(uriString)
                .into(holder.frescoListItemIvIMG);
    }

    protected class ViewHolder {
        private SimpleDraweeView frescoListItemSdvIMG;
        private ImageView frescoListItemIvIMG;
        private TextView frescoListItemTvName;

        public ViewHolder(View view) {
            frescoListItemSdvIMG = (SimpleDraweeView) view.findViewById(R.id.fresco_list_item_sdv_IMG);
            frescoListItemIvIMG = (ImageView) view.findViewById(R.id.fresco_list_item_iv_IMG);
            frescoListItemTvName = (TextView) view.findViewById(R.id.fresco_list_item_tv_Name);
        }
    }
}
