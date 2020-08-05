package com.weknowall.cn.wuwei.ui.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.StatusBarUtils;
import com.weknowall.cn.wuwei.widget.FullScreenVideoView;
import com.weknowall.cn.wuwei.widget.layoutManager.ViewPagerLayoutManager;
import com.weknowall.cn.wuwei.widget.recyclerview.adapter.AdapterPlus;
import com.weknowall.cn.wuwei.widget.recyclerview.adapter.ViewHolderPlus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DouYinActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private DouYinAdapter mAdapter;
    private ViewPagerLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douyin);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(layoutManager=new ViewPagerLayoutManager(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter = new DouYinAdapter(getContext()));

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("");
        }
        mAdapter.insertRange(datas);
        layoutManager.setOnViewPagerListener(new ViewPagerLayoutManager.ViewPagerListener() {
            @Override
            public void onInit() {
                playVideo();
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                playVideo();
            }

            @Override
            public void onPageRelease(int position, boolean isNext) {
                int index=0;
                if (isNext){
                    index=0;
                }else {
                    index=1;
                }
                releaseVideo(index);
            }
        });
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtils.setTranslucentForImageView(this,0,null);
        StatusBarUtils.StatusBarLightMode(this,true);
    }

    private void releaseVideo(int index) {
        View view = mRecyclerView.getChildAt(index);
        VideoView videoView = view.findViewById(R.id.video_view);
        ImageView imageView = view.findViewById(R.id.video_cover_img);
        videoView.stopPlayback();
        imageView.animate().alpha(1).start();
    }

    private void playVideo() {
        View view = mRecyclerView.getChildAt(0);
        VideoView videoView = view.findViewById(R.id.video_view);
        ImageView imageView = view.findViewById(R.id.video_cover_img);
        videoView.start();
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                mp.setLooping(true);
                imageView.animate().alpha(0).setDuration(200).start();
                return false;
            }
        });
    }

    class DouYinAdapter extends AdapterPlus<String> {
        private int[] imgs = {R.drawable.img_video_1, R.drawable.img_video_2};
        private int[] videos = {R.raw.video_1, R.raw.video_2};

        public DouYinAdapter(Context context) {
            super(context);
        }

        @Override
        public ViewHolderPlus<String> onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater) {
            return new ItemViewHolder(inflater.inflate(R.layout.item_douyin, parent, false));
        }

        class ItemViewHolder extends ViewHolderPlus<String> {
            @BindView(R.id.video_view)
            FullScreenVideoView mVideoView;
            @BindView(R.id.video_cover_img)
            ImageView mCoverImage;

            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @Override
            public void onBinding(int position, String s) {
                mVideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+ videos[position%2]));
                mCoverImage.setImageResource(imgs[position%2]);
            }

        }
    }
}
