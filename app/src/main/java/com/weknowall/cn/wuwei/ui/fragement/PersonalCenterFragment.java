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
import com.weknowall.cn.wuwei.ui.adapter.SwipeDeleteAdapter;
import com.weknowall.cn.wuwei.widget.recyclerview.adapter.AdapterPlus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2017-04-01
 * Time: 16-27
 */

public class PersonalCenterFragment extends BaseFragment {

    @BindView(R.id.personal_center_recycler_view)
    RecyclerView mRecyclerView;

    private SwipeDeleteAdapter mAdapter;

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
        mRecyclerView.setAdapter(mAdapter=new SwipeDeleteAdapter());
        initData();
    }

    private void initData() {
        ArrayList mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("测试" + i);
        }
        mAdapter.setDatas(mList);
    }

}
