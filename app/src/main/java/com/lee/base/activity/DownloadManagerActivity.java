package com.lee.base.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lee.base.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.lee.base.R.id.download;


public class DownloadManagerActivity extends BaseActivity implements View.OnClickListener {
    DownloadManager manager;
    DownloadCompleteReceiver receiver;
    private Button mDownload;
    private TextView mState;
    private Button mDownload2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_manager);
        //获取下载服务
        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();
        initView();
    }

    private void initView() {
        mDownload = (Button) findViewById(download);
        mDownload.setOnClickListener(this);
        mState = (TextView) findViewById(R.id.state);

        mDownload2 = (Button) findViewById(R.id.download2);
        mDownload2.setOnClickListener(this);
    }

    DownloadManager.Request request;
                final String f1 = "http://12366app.tax.sh.gov.cn/downloads/swj.apk";
                final String f2 = "http://shlgservice.com/cmsDownLoad/downLoadFile?id=5";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case download:
                //                //创建下载请求
                //                request = new DownloadManager.Request(Uri.parse(f2));
                //                //设置允许使用的网络类型，这里是移动网络和wifi都可以
                //                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                //                //禁止发出通知，既后台下载
                //                request.setShowRunningNotification(false);
                //                //不显示下载界面
                //                request.setVisibleInDownloadsUi(true);
                //                String path1 = Environment.getDownloadCacheDirectory().getAbsolutePath();
                //                request.setDestinationUri(Uri.fromFile(new File(path1)));//设置文件存放目录，filePath是保存文件的路径
                //                //设置下载后文件存放的位置
                //                request.setDestinationInExternalFilesDir(mContext, null, "qqinput.apk");
                //                //将下载请求放入队列
                //                lastDownloadId = manager.enqueue(request);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //另起线程执行下载，安卓最新sdk规范，网络操作不能再主线程。
                        Download l = new Download(f1);
                        //                        load.setMax(l.getLength());

                        Log.d("log", Integer.toString(l.getLength()));
//                        /**
//                         * 下载文件到sd卡，虚拟设备必须要开始设置sd卡容量
//                         * downhandler是Download的内部类，作为回调接口实时显示下载数据
//                         */
//                        int status = l.down2sd("downtemp/", "1.ddd", l.new downhandler() {
//                            @Override
//                            public void setSize(int size) {
//                                Message msg = handler.obtainMessage();
//                                msg.arg1 = size;
//                                msg.sendToTarget();
//
//                            }
//                        });
//                        //log输出
//                        Log.d("log", Integer.toString(status));

                    }
                }).start();

                break;
            case R.id.download2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //另起线程执行下载，安卓最新sdk规范，网络操作不能再主线程。
                        Download l = new Download(f2);
                        //                        load.setMax(l.getLength());

                        Log.d("log", Integer.toString(l.getLength()));
//                        /**
//                         * 下载文件到sd卡，虚拟设备必须要开始设置sd卡容量
//                         * downhandler是Download的内部类，作为回调接口实时显示下载数据
//                         */
//                        int status = l.down2sd("downtemp/", "1.ddd", l.new downhandler() {
//                            @Override
//                            public void setSize(int size) {
//                                Message msg = handler.obtainMessage();
//                                msg.arg1 = size;
//                                msg.sendToTarget();
//
//                            }
//                        });
//                        //log输出
//                        Log.d("log", Integer.toString(status));

                    }
                }).start();
                break;
        }
    }
    //接受下载完成后的intent

    class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Log.v("DownloadCompleteReceiver", " download complete! id : " + downId);
                Toast.makeText(context, intent.getAction() + "id : " + downId, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");

    private DownloadChangeObserver downloadObserver;

    @Override
    public void onResume() {
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        downloadObserver = new DownloadChangeObserver(null);
        getContentResolver().registerContentObserver(CONTENT_URI, true, downloadObserver);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (receiver != null)
            unregisterReceiver(receiver);


        getContentResolver().unregisterContentObserver(downloadObserver);
        super.onDestroy();
    }

    private long lastDownloadId = 0;

    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(lastDownloadId);
        Cursor cursor = manager.query(query);

        if (cursor != null && cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            int reasonIdx = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
            int titleIdx = cursor.getColumnIndex(DownloadManager.COLUMN_TITLE);
            int fileSizeIdx = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            int bytesDLIdx = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
            int uri = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
            String title = cursor.getString(titleIdx);
            String local_uri = cursor.getString(uri);
            int fileSize = cursor.getInt(fileSizeIdx);
            int bytesDL = cursor.getInt(bytesDLIdx);

            // Translate the pause reason to friendly text.
            int reason = cursor.getInt(reasonIdx);

            final StringBuilder sb = new StringBuilder();
            //            sb.append(title).append("  ").append(reason).append("  ").append(local_uri).append("\n");
            //            sb.append(cursor.getString(12)). append("\n");
            //            sb.append(cursor.getString(10)). append("\n");
            //            sb.append("Downloaded ").append(bytesDL).append(" / ").append(fileSize).append("    " + bytesDL * 100 / fileSize + "%");

            for (int i = 0; i < cursor.getColumnCount(); i++) {
                if (cursor.getString(i) != null) {

                    sb.append(cursor.getColumnName(i) + "------").append(cursor.getString(i)).append("\n");
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mState.setText(sb.toString());

                }
            });

            // Display the status
            Log.d("tag", sb.toString());
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.v("tag", "STATUS_PAUSED");
                case DownloadManager.STATUS_PENDING:
                    Log.v("tag", "STATUS_PENDING");
                case DownloadManager.STATUS_RUNNING:
                    //正在下载，不做任何事情
                    Log.v("tag", "STATUS_RUNNING");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    //完成
                    Log.v("tag", "下载完成");
                    //                  dowanloadmanager.remove(lastDownloadId);
                    break;
                case DownloadManager.STATUS_FAILED:
                    //清除已下载的内容，重新下载
                    Log.v("tag", "STATUS_FAILED");
                    manager.remove(lastDownloadId);
                    break;
            }
        }
    }

    class DownloadChangeObserver extends ContentObserver {

        public DownloadChangeObserver(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }


        @Override
        public void onChange(boolean selfChange) {
            queryDownloadStatus();
        }


    }

    class Download {
        /**
         * 连接url
         */
        private String urlstr;
        /**
         * sd卡目录路径
         */
        private String sdcard;
        /**
         * http连接管理类
         */
        private HttpURLConnection urlcon;

        public Download(String url) {
            this.urlstr = url;
            //获取设备sd卡目录
            this.sdcard = Environment.getExternalStorageDirectory() + "/";
            urlcon = getConnection();
        }

        /*
         * 读取网络文本
         */
        public String downloadAsString() {
            StringBuilder sb = new StringBuilder();
            String temp = null;
            try {
                InputStream is = urlcon.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        /*
         * 获取http连接处理类HttpURLConnection
         */
        private HttpURLConnection getConnection() {
            URL url;
            HttpURLConnection urlcon = null;
            try {
                url = new URL(urlstr);
                urlcon = (HttpURLConnection) url.openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return urlcon;
        }

        /*
         * 获取连接文件长度。
         */
        public int getLength() {
            return urlcon.getContentLength();
        }

        /*
         * 写文件到sd卡 demo
         * 前提需要设置模拟器sd卡容量，否则会引发EACCES异常
         * 先创建文件夹，在创建文件
         */
        public int down2sd(String dir, String filename, downhandler handler) {
            StringBuilder sb = new StringBuilder(sdcard)
                    .append(dir);
            File file = new File(sb.toString());
            if (!file.exists()) {
                file.mkdirs();
                //创建文件夹
                Log.d("log", sb.toString());
            }
            //获取文件全名
            sb.append(filename);
            file = new File(sb.toString());

            FileOutputStream fos = null;
            try {
                InputStream is = urlcon.getInputStream();
                //创建文件
                file.createNewFile();
                fos = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                while ((is.read(buf)) != -1) {
                    fos.write(buf);
                    //同步更新数据
                    handler.setSize(buf.length);
                }
                is.close();
            } catch (Exception e) {
                return 0;
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return 1;
        }

        /*
         * 内部回调接口类
         */
        public abstract class downhandler {
            public abstract void setSize(int size);
        }
    }
}
