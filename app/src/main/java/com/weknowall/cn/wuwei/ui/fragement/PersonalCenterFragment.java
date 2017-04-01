package com.weknowall.cn.wuwei.ui.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weknowall.app_presenter.entity.general.GitUser;
import com.weknowall.app_presenter.presenter.general.GitUsersPresenter;
import com.weknowall.app_presenter.view.IGitUsersView;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.dagger.components.DaggerGeneralFragmentComponent;
import com.weknowall.cn.wuwei.ui.BaseFragment;
import com.weknowall.cn.wuwei.ui.adapter.GitUsersAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2017-04-01
 * Time: 16-27
 */

public class PersonalCenterFragment extends BaseFragment implements IGitUsersView {

    @BindView(R.id.personal_center_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    GitUsersPresenter mPresenter;

    private GitUsersAdapter mAdapter;

    public static PersonalCenterFragment newInstance() {
        Bundle args = new Bundle();
        PersonalCenterFragment fragment = new PersonalCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter=new GitUsersAdapter(getContext()));

        DaggerGeneralFragmentComponent.builder().applicationComponent(getApplicationComponent()).fragmentModule(getFragmentModule()).build().inject(this);
        mPresenter.setView(this);
        mPresenter.initialize();
    }

    @Override
    public void onGetGitUsers(List<GitUser> users) {
        // 通过返回数据更新adapter
        mAdapter.clear();
        mAdapter.insertRange(users, false);
    }
}
