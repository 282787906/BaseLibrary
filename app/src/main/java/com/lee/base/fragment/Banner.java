package com.lee.base.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lee.base.R;
import com.lee.base.view.banner.MyBanner;
import com.lee.base.view.banner.MyBannerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Banner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Banner extends BaseFragment {
    MyBanner myBanner;

    public Banner() {
        // Required empty public constructor
    }

    public static Banner newInstance() {
        Banner fragment = new Banner();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, null);
        myBanner = (MyBanner) view.findViewById(R.id.fragment_banner_MyBanner);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //                String[] ad = {"http://a3.qpic.cn/psb?/V11ThYra1iVeHg/gQwMlXiO1uo3WtvpnnWxeR8D2QiGCHAwvpGjAF9Y2D8!/b/dPsAAAAAAAAA&bo=7wKzAQAAAAABB38!&rf=viewer_4",
        //                        "http://a3.qpic.cn/psb?/V11ThYra1iVeHg/Opn.jd0jWfizOVnCz1YxDbGRiKNQSWEiM7Ll2TXK8C0!/b/dKcAAAAAAAAA&bo=7wKzAQAAAAABAHg!&rf=viewer_4",
        //                        "http://img.zcool.cn/community/01b1985545815d0000019ae9b7c787.jpg"};
//        uri2 = Uri.parse("res://" + getActivity().getPackageName() + "/" + R.drawable.lead1);
        String[] ad = {"res://" + getActivity().getPackageName() + "/" + R.drawable.lead1,
                "res://" + getActivity().getPackageName() + "/" + R.drawable.lead2,
                "res://" + getActivity().getPackageName() + "/" + R.drawable.lead3};
        //        String[] ad =initAdImgs();
        List<MyBannerModel> myBannerModels = new ArrayList<>();

        for (String url : ad) {
            MyBannerModel myBannerModel = new MyBannerModel();
            myBannerModel.setPictureUrl(url);
            myBannerModels.add(myBannerModel);
        }
        myBanner.initPageIndex(myBannerModels);


    }


}
