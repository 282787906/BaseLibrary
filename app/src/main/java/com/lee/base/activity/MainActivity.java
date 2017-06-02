package com.lee.base.activity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lee.base.R;
import com.lee.base.app_framework.BottomNavigationActivity;
import com.lee.base.business.AppUpdateManager;
import com.lee.base.business.Constant;
import com.lee.base.core.business.Permission.CheckPermissionCallback;
import com.lee.base.core.business.Permission.Permission;
import com.lee.base.core.log.MyLog;
import com.lee.base.core.utils.FileUtil;
import com.lee.base.core.utils.Phone;
import com.lee.base.core.utils.ToastUtil;
import com.lee.base.fragment.Banner;
import com.lee.base.fragment.CardView;
import com.lee.base.fragment.FragmentFresco;
import com.lee.base.fragment.FragmentGlide;
import com.lee.base.fragment.ImageSelect;
import com.lee.base.fragment.JChat;
import com.lee.base.fragment.JPush;
import com.lee.base.fragment.NoHttp;
import com.lee.base.fragment.RecyclerViewFragment;
import com.lee.base.fragment.RefreshListFragment;
import com.lee.base.fragment.RefreshListFrescoFragment;
import com.lee.base.fragment.TaskRecvclerViewFragment;
import com.lee.base.fragment.WebViewTemplate;
import com.lee.base.view.jpushNotification.MyNotification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String tag = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //        fab.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                        .setAction("Action", null).show();
        //            }
        //        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//                int a=Integer.parseInt("we");

        JPushInterface.setPushNotificationBuilder(2, MyNotification.newInstance(activity));


        //        //        JPushInterface.setPushNotificationBuilder(4, builder);
        //
        //
        //        CustomPushNotificationBuilder builder = new
        //                CustomPushNotificationBuilder(MainActivity.this,
        //                R.layout.customer_notitfication_layout,
        //                R.id.customer_notitfication_iv_icon,
        //                R.id.customer_notitfication_tv_title,
        //                R.id.customer_notitfication_tv_text);
        //        builder.layoutIconDrawable = R.mipmap.qwe;
        //        builder.developerArg0 = "developerArg2";
        //        JPushInterface.setPushNotificationBuilder(2, builder);


        MyLog.d(tag, "onCreate");
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivity(intent);
        }else if (id == R.id.nav_Dialog) {
            startActivity(new Intent(this,DialogActivity.class));
        } else if (id == R.id.nav_CardView) {

            showFragment(CardView.newInstance());
        }   else if (id == R.id.nav_CallPhone) {
            Phone.readyCall(activity, "15555555555");
            List<Permission> permissions = new ArrayList<>();
//            permissions.add(new Permission(Manifest.permission.CALL_PHONE, "拨打电话和管理通话"));//调用拨号界面不需要申请权限
//
//            checkPermission(permissions, false, new CheckPermissionCallback() {
//                @Override
//                public void checkOverPass() {
//                    call(activity, "15555555555");
//                }
//
//                @Override
//                public void checkOverCancel() {
//                    ToastUtil.longToast(activity, "拨打电话未授权");
//                }
//            });

        } else if (id == R.id.nav_Fresco) {

            showFragment(FragmentFresco.newInstance());
        }  else if (id == R.id.nav_Glide) {

            showFragment(FragmentGlide.newInstance());
        } else if (id == R.id.nav_RefreshListview) {
            showFragment(RefreshListFragment.newInstance());
        } else if (id == R.id.nav_Scan) {
            List<Permission> permissions = new ArrayList<>();
            permissions.add(new Permission(Manifest.permission.CAMERA, "拨打电话和管理通话"));//调用相机界面不需要申请权限

            checkPermission(permissions, false, new CheckPermissionCallback() {
                @Override
                public void checkOverPass() {
                    startActivity(new Intent(activity, ScanActivity.class));
                    //                    showFragment(Scan.newInstance( ));
                }

                @Override
                public void checkOverCancel() {
                    ToastUtil.longToast(activity, "摄像头未授权");
                }
            });

        } else if (id == R.id.nav_NoHttp) {
            showFragment(NoHttp.newInstance());
        } else if (id == R.id.nav_FrescoListview) {
            showFragment(RefreshListFrescoFragment.newInstance());
        } else if (id == R.id.nav_Update) {
            File file = new File(FileUtil.getRootFilePath(activity) + Constant.NEW_VERSION_APK_PATH + "/" + Constant.NEW_VERSION_APK_NAME);
            file.delete();

            AppUpdateManager appUpdateManager = new AppUpdateManager(activity, new AppUpdateManager.AppUpdateCallBack() {
                @Override
                public void toMain() {
                    startActivity(new Intent(activity, MainActivity.class));//跳转到MainActivity
                    //                    AppManager.getAppManager().finishActivity((MainActivity)activity );
                    //                    finish();
                }
            });
            appUpdateManager.submitUpdate();
        } else if (id == R.id.nav_Banner) {
            showFragment(Banner.newInstance());
        } else if (id == R.id.nav_JPush) {
            showFragment(JPush.newInstance());
        } else if (id == R.id.nav_JChat) {
            showFragment(JChat.newInstance());
        } else if (id == R.id.nav_ImageSelect) {
            showFragment(ImageSelect.newInstance());
        }else if (id == R.id.nav_WebViewTemplate) {
            showFragment(WebViewTemplate.newInstance());
        }else if (id == R.id.nav_DownloadManager) {
            startActivity(new Intent(this,DownloadManagerActivity.class));
        }else if (id == R.id.nav_TabActivity) {
            startActivity(new Intent(this,TabActivity.class));
        }else if (id == R.id.nav_CoordinatorLayout) {
            startActivity(new Intent(this,CoordinatorLayoutText.class));
        } else if (id == R.id.nav_RecyclerView) {
            showFragment(RecyclerViewFragment.newInstance());
        }else if (id == R.id.nav_RefreshRecyclerView) {
//            showFragment(RefreshRecvclerViewFragment.newInstance());
            showFragment(TaskRecvclerViewFragment.newInstance());
        }else if (id == R.id.nav_ApiStore) {
            startActivity(new Intent(this,ApiStoreActivity.class));
        }else if (id == R.id.nav_IM) {
            startActivity(new Intent(this,WeiIMActivity.class));
        }else if (id == R.id.nav_BottomNavigation) {
            startActivity(new Intent(this,BottomNavigationActivity.class));
        }else  if (id == R.id.nav_SlidingClose) {
            // Handle the camera action
            Intent intent = new Intent(this,SlidingCloseActivity.class);

            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
