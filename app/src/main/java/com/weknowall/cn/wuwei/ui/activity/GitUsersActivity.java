package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.weknowall.app_domain.entity.general.GitUser;
import com.weknowall.app_presenter.presenter.general.GitUsersPresenter;
import com.weknowall.app_presenter.view.IGitUsersView;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.dagger.components.DaggerGeneralComponent;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.ui.adapter.GitUsersAdapter;
import com.weknowall.cn.wuwei.widget.ToolBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2017-03-01
 * Time: 18-45
 */

public class GitUsersActivity extends BaseActivity implements IGitUsersView {

    @BindView(R.id.toolbar)
    ToolBar mToolbar;
    @BindView(R.id.git_users_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    GitUsersPresenter mPresenter;

    GitUsersAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git_users);
        ButterKnife.bind(this);

        // 初始化网络请求
        DaggerGeneralComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).build().inject(this);
        mPresenter.setView(this);

        // 初始化recyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter = new GitUsersAdapter(getContext()));

        // 发起网络请求
        mPresenter.initialize();
    }

    /**
     * 网络请求返回数据
     * @param users
     */
    @Override
    public void onGetGitUsers(List<GitUser> users) {
        // 通过返回数据更新adapter
        mAdapter.clear();
        mAdapter.insertRange(users, false);
    }

}
