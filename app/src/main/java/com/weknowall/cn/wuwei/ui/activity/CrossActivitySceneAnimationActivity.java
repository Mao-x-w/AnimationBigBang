package com.weknowall.cn.wuwei.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weknowall.app_common.utils.DeviceHelper;
import com.weknowall.cn.wuwei.Constants;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.image.GlideApp;
import com.weknowall.cn.wuwei.widget.ToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CrossActivitySceneAnimationActivity extends BaseActivity {

    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.web_image_view)
    ImageView mWebImageView;
    @BindView(R.id.round_image_view)
    ImageView mRoundImageView;
    @BindView(R.id.toolbar)
    ToolBar mToolbar;

    private String url1="https://s1.st.meishij.net/r/21/119/2654771/s2654771_141984835065242.jpg";
    private String url2="https://cdn.dribbble.com/users/688456/screenshots/6759955/chair-scene-final-clay-dribbble_1x.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_activity_scene_animation);
        ButterKnife.bind(this);

        setImageUrl(url1,mWebImageView);
        setImageUrl(url2,mRoundImageView);
    }

    @OnClick({R.id.search, R.id.web_image_view,R.id.round_image_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                Bundle options = ActivityOptions.makeSceneTransitionAnimation(this, mSearch, getString(R.string.transition_search_back)).toBundle();
                startActivityForResult(new Intent(this, CrossActivitySearchActivity.class), 0, options);
                break;
            case R.id.web_image_view:
                Bundle bundle=ActivityOptions.makeSceneTransitionAnimation(this,mWebImageView,getString(R.string.transition_detail)).toBundle();
                startActivity(new Intent(this,CrossActivityDetailActivity.class).putExtra(Constants.IntentExtra.URL,url1),bundle);
                break;
            case R.id.round_image_view:
                Bundle bundle1=ActivityOptions.makeSceneTransitionAnimation(this,mRoundImageView,getString(R.string.transition_detail)).toBundle();
                startActivity(new Intent(this,CrossActivityDetailActivity.class).putExtra(Constants.IntentExtra.URL,url2),bundle1);
                break;
        }
    }

    /**
     * 实现图片转场动画无感知，关键是要两边加载的图片的尺寸要相同，这样到详情页的时候才不会重新裁剪图片
     * 此外，里面和外面的图片缩放方式要相同（这里用的都是centerCrop）
     * @param url
     * @param imageView
     */
    public void setImageUrl(String url,ImageView imageView){
        GlideApp.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .transition(withCrossFade())
                .override(DeviceHelper.getScreenWidth(), getResources().getDimensionPixelSize(R.dimen.size_640))
                .into(imageView);
    }
}
