package com.weknowall.cn.wuwei.ui.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.image.ImageLoader2;
import com.weknowall.cn.wuwei.widget.recyclerview.adapter.AdapterPlus;
import com.weknowall.cn.wuwei.widget.recyclerview.adapter.ViewHolderPlus;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2018-10-21
 * Time: 18-08
 */

public class ThreadDemoActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private ImageAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private Set<String> mDirSets = new HashSet<>();
    private int mMaxDirSize = 0;
    private File mMaxDirFile;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mProgressDialog.dismiss();

            String[] images = mMaxDirFile.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    if (filename.endsWith(".jpg"))
                        return true;
                    return false;
                }
            });

            mAdapter.insertRange(Arrays.asList(images), false);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_demo);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter = new ImageAdapter(getContext()));
    }

    private void initData() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            showToast("无外部存储");
            return;
        }

        mProgressDialog = ProgressDialog.show(getContext(), null, "加载中....");

        // 获取到图片最多的文件夹
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver contentResolver = ThreadDemoActivity.this.getContentResolver();
                Cursor cursor = contentResolver.query(uri, null, MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?"
                        , new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    File parentFile = new File(path).getParentFile();
                    String dirPath = parentFile.getAbsolutePath();

                    if (mDirSets.contains(dirPath)) {
                        continue;
                    } else {
                        mDirSets.add(dirPath);
                    }

                    int dirSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg"))
                                return true;
                            return false;
                        }
                    }).length;

                    if (dirSize > mMaxDirSize) {
                        mMaxDirSize = dirSize;
                        mMaxDirFile = parentFile;
                    }

                }

                cursor.close();
                mDirSets = null;

                mHandler.sendEmptyMessage(0);

            }
        }).start();
    }

    class ImageAdapter extends AdapterPlus<String> {

        public ImageAdapter(Context context) {
            super(context);
        }

        @Override
        public ViewHolderPlus<String> onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater) {
            return new ItemViewHolder(inflater.inflate(R.layout.item_images, parent, false));
        }

        class ItemViewHolder extends ViewHolderPlus<String> {

            @BindView(R.id.image_view)
            ImageView mImageView;
            @BindView(R.id.title)
            TextView mTitle;

            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @Override
            public void onBinding(int position, String s) {
//                Glide.with(getContext()).load(new File(mMaxDirFile,s)).into(mImageView);
                mTitle.setText(s);
                ImageLoader2.getInstance().loadImage(new File(mMaxDirFile,s).getAbsolutePath(),mImageView);
            }
        }

    }
}
