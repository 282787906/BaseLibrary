package com.lee.base.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lee.base.R;
import com.lee.base.core.utils.ToastUtil;
import com.lee.base.view.FixedSpeedScroller;
import com.lee.base.view.SwipeBackLayout;

import java.lang.reflect.Field;


public class TabActivity extends BaseActivity implements View.OnClickListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    SwipeBackLayout swipeBackLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ToastUtil.showSingleToast(activity,"index:"+position, Toast.LENGTH_LONG);
                mViewPager.setCurrentItem(position);
                if (position==0)
                {

                    swipeBackLayout.setEnableMoveClose(true);
                }else {
                    swipeBackLayout.setEnableMoveClose(false);}
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.but_Tab1).setOnClickListener(this);
        findViewById(R.id.but_Tab2).setOnClickListener(this);
        findViewById(R.id.but_Tab3).setOnClickListener(this);
        setViewPagerScrollSpeed();
          swipeBackLayout = (SwipeBackLayout) findViewById(R.id.swipe_layout_two);
        swipeBackLayout.setCallBack(new SwipeBackLayout.CallBack() {
            @Override
            public void onFinish() {
                Log.e(getClass().getSimpleName(),"SwipeBack onFinish");
                finish();
            }
        });
        swipeBackLayout.setEnableMoveClose(true);
    }


    /**
     * 设置ViewPager的滑动速度
     *
     * */
    private void setViewPagerScrollSpeed( ){
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller( mViewPager.getContext( ) );
            mScroller.set( mViewPager, scroller);
        }catch(NoSuchFieldException e){

        }catch (IllegalArgumentException e){

        }catch (IllegalAccessException e){

        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_Tab1:
                //TODO implement
                mViewPager.setCurrentItem(0);
                break;
            case R.id.but_Tab2:
                //TODO implement
                mViewPager.setCurrentItem(1);
                break;
            case R.id.but_Tab3:
                //TODO implement

                mViewPager.setCurrentItem(2);
                break;
        }
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
