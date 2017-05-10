package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.ui.fragement.PersonalCenterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2017-04-24
 * Time: 10-29
 */

public class SmartTablayoutActivity extends BaseActivity {

    @BindView(R.id.smart_tab_layout)
    SmartTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_tab_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        String[] titles = getContext().getResources().getStringArray(R.array.personal_center_tab);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(titles[0], PersonalCenterFragment.class)
                .add(titles[1], PersonalCenterFragment.class)
                .add(titles[2], PersonalCenterFragment.class)
                .add(titles[3], PersonalCenterFragment.class)
                .add(titles[4], PersonalCenterFragment.class)
                .create());
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);
    }
}
