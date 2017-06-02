package com.lee.base.view.bott_nav;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lee.base.R;

import java.util.List;

/**
 * 底部Tab适配
 * Created by liqg on 2015/10/23.
 */
public class BottomNavigationAdapter extends BaseAdapter {

    private String mTag = getClass().getSimpleName();
    private List<MainTab> data;
    private MainTab mainTab;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private BottomTabClickListener mClickListener;
    private int textColor_selected=-1;
    private int textColor_normal=-1;
    private final  int TEXT_COLOR_SELECTED_DEFAULT= Color.WHITE;
    private final int TEXT_COLOR_NORMAL_DEFAULT=Color.BLACK;

    public BottomNavigationAdapter(Context context, List<MainTab> mainTabs, BottomTabClickListener clickListener) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        data = mainTabs;
        mClickListener = clickListener;
    }

    public void setTextColor(int textColor_selected,int textColor_normal) {
        this.textColor_selected = textColor_selected;
        this.textColor_normal = textColor_normal;
    }

    public void setData( List<MainTab> mainTabs) {
        data = mainTabs;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.main_tab_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        mainTab = data.get(position);

        holder.textview.setText(mainTab.getName());


        if (mainTab.isSelected()) {
            holder.imageview.setImageResource(mainTab.getImage_selected());
            holder.textview.setTextColor(textColor_selected==-1?TEXT_COLOR_SELECTED_DEFAULT:textColor_selected);
        } else {
            holder.imageview.setImageResource(mainTab.getImage_normal());
            holder.textview.setTextColor(textColor_normal==-1?TEXT_COLOR_NORMAL_DEFAULT:textColor_normal);
        }

        holder.maintabitemrlbg.setId(position);

        holder.maintabitemrlbg.setOnClickListener(onClickListener);

        return view;
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            setSelected(view.getId());
            mClickListener.onClick(view.getId());
        }
    };

    /**
     * 设置底部tab选中
     *
     * @param index
     */
    public void setSelected(int index) {
        for (int i = 0; i < data.size(); i++) {
            if (index == i) {
                data.get(i).setSelected(true);
            } else {
                data.get(i).setSelected(false);
            }
        }
        notifyDataSetChanged();
    }


    public class ViewHolder {
        public final ImageView imageview;
        public final TextView textview;
        public final RelativeLayout maintabitemrlbg;

        public final View root;

        public ViewHolder(View root) {
            imageview = (ImageView) root.findViewById(R.id.imageview);
            textview = (TextView) root.findViewById(R.id.textview);
            maintabitemrlbg = (RelativeLayout) root.findViewById(R.id.main_tab_item_rl_bg);

            this.root = root;
        }
    }


}
