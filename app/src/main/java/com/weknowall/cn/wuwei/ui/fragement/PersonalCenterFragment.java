package com.weknowall.cn.wuwei.ui.fragement;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseFragment;
import com.weknowall.cn.wuwei.ui.activity.CoordinatorLayoutActivity;
import com.weknowall.cn.wuwei.ui.adapter.SwipeDeleteAdapter;
import com.weknowall.cn.wuwei.widget.recyclerview.RecyclerViewHelper;

import java.util.ArrayList;

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
        // 当滚动到第一个项目时，自动滚动到顶部
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastPosition = -1;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int visiblePosition = RecyclerViewHelper.findFirstCompleteVisibleItemPosition(mRecyclerView);
                    if (visiblePosition == 0 && lastPosition != visiblePosition) {
                        RxBus.get().post(CoordinatorLayoutActivity.RX_BUS_SCROLL_TOP,"1");
                    }
                    lastPosition = visiblePosition;
                }
            }
        });

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
