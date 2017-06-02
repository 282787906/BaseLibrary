package com.lee.base.view.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.lee.base.R;


/**
 * Banner每页显示的view 该类和Model类属于业务类
 * Created by liqg
 * on 2015/12/29.
 */
public class MyBannerView {
    LayoutInflater layoutInflater;
    Context context;

    public MyBannerView(Context context, LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        this.context = context;

    }

    public View newView(final MyBannerModel myBannerModel) {
        View view = layoutInflater.inflate(R.layout.view_banner_item, null, false);

        DraweeView draweeView = (DraweeView) view.findViewById(R.id.view_banner_item_dv_Img);
//        ImageLoader.getInstance().displayImage(myBannerModel.getPictureUrl(), imageView, options, null);
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(context.getResources())
                .setFadeDuration(300)
                .setProgressBarImage(new ProgressBarDrawable())
                .setPlaceholderImage(context.getResources().getDrawable(R.mipmap.qwe), ScalingUtils.ScaleType.CENTER_CROP)
                .setFailureImage(context.getResources().getDrawable(R.mipmap.ic_launcher))
                .build();

        DraweeController draweeController=Fresco.newDraweeControllerBuilder()
                .setUri(myBannerModel.getPictureUrl())
                .build();
        draweeView.setHierarchy(hierarchy);
        draweeView.setController(draweeController);
        //        view.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                Toast.makeText(context, "onClick:" + myBannerModel.getPictureUrl(), Toast.LENGTH_SHORT).show();
        //            }
        //        });
        return view;
    }
}
