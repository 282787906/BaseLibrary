package com.lee.base.fragment;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lee.base.R;
import com.lee.base.glide.GlideCircleTransform;
import com.lee.base.glide.GlideRoundTransform;

public class FragmentGlide extends BaseFragment implements View.OnClickListener{

//    private DraweeView frescoDvDraweeView;
//    private GenericDraweeView frescoDvGenericDraweeView;
//    private SimpleDraweeView frescoDvSimpleDraweeView;    private SimpleDraweeView frescoDvSimpleGif;
    private static FragmentGlide fragment;

    private ImageView glideIvImg1;
    private ImageView glideIvImg2;
    private ImageView glideIvImg3;
    private ImageView glideIvImg4;

    String imgPath="http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fpic2.bbzhi.com%2Fjianzhubizhi%2Fjianzhuyuanlinxiaoguo%2Fjianzhuyuanlinxiaoguo_98848_10.jpg";
    public synchronized static Fragment newInstance() {
        if (fragment == null) {
            fragment = new FragmentGlide();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_glide, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.fresco_btn_LoadImage).setOnClickListener(this);
        view.findViewById(R.id.fresco_btn_Clear).setOnClickListener(this);

        glideIvImg1 = (ImageView) view.findViewById(R.id.glide_iv_Img1);
        glideIvImg2 = (ImageView) view.findViewById(R.id.glide_iv_Img2);
        glideIvImg3 = (ImageView) view.findViewById(R.id.glide_iv_Img3);
        glideIvImg4 = (ImageView) view.findViewById(R.id.glide_iv_Img4);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fresco_btn_LoadImage:
                Glide.with(this)
                        .load( imgPath).transform(new GlideRoundTransform(mContext,45))
                        .into(glideIvImg1);
                Glide.with(this)
                    .load( imgPath)
                        .centerCrop().transform(new GlideCircleTransform(mContext))
                    .into(glideIvImg2);
                Glide.with(this)
                        .load( R.mipmap.gif)
                        .centerCrop()
                        .placeholder(R.mipmap.list_empty)
                        .into(glideIvImg3);

                //Uri.parse("asset:///gif.gif")
                Glide.with(this)
                        .load( Uri.parse("http://p5.qhimg.com/t01d0e0384b952ed7e8.gif"))
                        .centerCrop()
                        .placeholder(R.mipmap.list_empty)
                        .into(glideIvImg4);
//                Uri uri4 = Uri.parse("http://p5.qhimg.com/t01d0e0384b952ed7e8.gif");
//                ////                Uri uri4 = Uri.parse("res://"+ LiPackageUtil.getPackageName(getActivity())+"/"+R.raw.gif);
                break;
            case R.id.fresco_btn_Clear:
                //TODO implement

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                Glide.get(getActivity()).clearDiskCache();

                    }
                }).start();
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                Glide.get(getActivity()).clearMemory();
                break;
        }
    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        frescoDvDraweeView = (DraweeView) view.findViewById(R.id.fresco_dv_DraweeView);
//        frescoDvGenericDraweeView = (GenericDraweeView) view.findViewById(R.id.fresco_dv_GenericDraweeView);
//        frescoDvSimpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.fresco_dv_SimpleDraweeView);
//        frescoDvSimpleGif= (SimpleDraweeView) view.findViewById(R.id.fresco_dv_SimpleGif);
//
//        view.findViewById(R.id.fresco_btn_LoadImage).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Uri uri1 = Uri.parse("http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01b1985545815d0000019ae9b7c787.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D1074053662%2C86985875%26fm%3D206%26gp%3D0.jpg");
//
//                Uri uri1 = Uri.parse("http://img.zcool.cn/community/01b1985545815d0000019ae9b7c787.jpg");
//                ImageRequest request1 = ImageRequestBuilder.newBuilderWithSource(uri1)
//                        .setProgressiveRenderingEnabled(true)
//                        .build();
//                DraweeController controller1 = Fresco.newDraweeControllerBuilder()
//                        .setImageRequest(request1)
//                        .build();
//                GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
//                        .setFadeDuration(300)
//                        .setProgressBarImage(new ProgressBarDrawable())
//                        .setPlaceholderImage(getActivity().getDrawable(R.mipmap.qwe), ScalingUtils.ScaleType.CENTER_CROP)
//                        .setFailureImage(getActivity().getDrawable(R.mipmap.ic_launcher))
//                        .build();
//                frescoDvDraweeView.setHierarchy(hierarchy);
//                frescoDvDraweeView.setController(controller1);
//
//                Uri uri2 = Uri.parse("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fa.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F72f082025aafa40fa38bfc55a964034f79f019ec.jpg&thumburl=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fh%253D200%2Fsign%3De648595ff01986185e47e8847aed2e69%2F0b46f21fbe096b63a377826e04338744ebf8aca6.jpg");
//
//                  uri2 =Uri.parse("res://"+getActivity().getPackageName()+"/" + R.drawable.lead1);
////                Resources r =getActivity().getResources();
////                uri2 =  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
////                        + r.getResourcePackageName(R.drawable.lead1) + "/"
////                        + r.getResourceTypeName(R.drawable.lead1) + "/"
////                        + r.getResourceEntryName(R.drawable.lead1));
//                 ImageRequest request2 = ImageRequestBuilder.newBuilderWithSource(uri2)
//                        .setProgressiveRenderingEnabled(true)
//                        .build();
//                DraweeController controller2 = Fresco.newDraweeControllerBuilder()
//                        .setImageRequest(request2)
//                        .build();
//                frescoDvGenericDraweeView.setController(controller2);
//
//                Uri uri3 = Uri.parse("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fpic2.bbzhi.com%2Fjianzhubizhi%2Fjianzhuyuanlinxiaoguo%2Fjianzhuyuanlinxiaoguo_98848_10.jpg");
//                frescoDvSimpleDraweeView.setImageURI(uri3);
//
////                Uri uri4 = Uri.parse("http://p5.qhimg.com/t01d0e0384b952ed7e8.gif");
////                Uri uri4 = Uri.parse("res://"+ LiPackageUtil.getPackageName(getActivity())+"/"+R.raw.gif);
//                Uri uri4 = Uri.parse("asset:///gif.gif");
//                ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
//                    @Override
//                    public void onFinalImageSet(
//                            String id,
//                            @Nullable ImageInfo imageInfo,
//                            @Nullable Animatable anim) {
//                        if (anim != null) {
//                            // 其他控制逻辑
//                            anim.start();
//                        }
//                    }
//                };
//
//                DraweeController controller = Fresco.newDraweeControllerBuilder()
//                        .setUri(uri4)
//                        .setAutoPlayAnimations(true)
////                        .setControllerListener(controllerListener)
//                        // 其他设置（如果有的话）
//                        .build();
//
//
//                frescoDvSimpleGif.setController(controller);
//
//            }
//        });
//        view.findViewById(R.id.fresco_btn_Clear).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ImagePipeline imagePipeline = Fresco.getImagePipeline();
//                imagePipeline.clearMemoryCaches();
//                imagePipeline.clearDiskCaches();
//                imagePipeline.clearCaches();
//            }
//        });
//
//        Log.i(getClass().getSimpleName(), "onViewCreated");
//    }

}
