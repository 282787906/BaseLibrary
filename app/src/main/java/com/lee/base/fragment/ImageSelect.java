package com.lee.base.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lee.base.R;
import com.lee.base.core.log.MyLog;

import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

public class ImageSelect extends BaseFragment implements View.OnClickListener {


    private ImageView ivImage;
    private String tag = ImageSelect.class.getSimpleName();

    public ImageSelect() {
        // Required empty public constructor
    }

    public static ImageSelect newInstance() {
        ImageSelect fragment = new ImageSelect();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_select, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.but_ImageSelect).setOnClickListener(this);
        view.findViewById(R.id.but_OpenCamera).setOnClickListener(this);
        ivImage = (ImageView) view.findViewById(R.id.iv_Image);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_ImageSelect:

                RxGalleryFinal
                        .with(mContext)
                                              .image()
                        .radio()
                        .crop()
                        .imageLoader(ImageLoaderType.FRESCO)
                        .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                            @Override
                            protected void onEvent(ImageRadioResultEvent baseResultEvent) throws Exception {
//                                ToastUtil.showSingleToast(mContext, "Path:" + baseResultEvent.getResult().getOriginalPath(), Toast.LENGTH_LONG);
                                MyLog.d(tag,  "Path:" + baseResultEvent.getResult().getOriginalPath());
                                ivImage.setImageURI(Uri.parse(baseResultEvent.getResult().getOriginalPath()));

                            }
                        }).openGallery();
                break;
            case R.id.but_OpenCamera:

                RxGalleryFinal
                        .with(mContext)
                                      .image()
                        .multiple()
                        .maxSize(3)
                        .imageLoader(ImageLoaderType.FRESCO)
                        .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                            @Override
                            protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                                List<MediaBean> mediaBeens = imageMultipleResultEvent.getResult();
                                for (MediaBean mediaBean : mediaBeens) {
                                    MyLog.d(tag, "Path:" + mediaBean.getOriginalPath());
                                }
                                //                                ToastUtil.showSingleToast(mContext,"Path:"+ baseResultEvent.getResult().getOriginalPath(), Toast.LENGTH_LONG);
                                //                                ivImage.setImageURI(Uri.parse(baseResultEvent.getResult().getOriginalPath()));
                                //                                cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent
                            }
                        }).openGallery();
                break;
        }
    }
}
