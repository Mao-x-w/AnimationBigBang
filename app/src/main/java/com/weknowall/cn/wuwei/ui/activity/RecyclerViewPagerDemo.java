package com.weknowall.cn.wuwei.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.widget.recyclerview.adapter.AdapterPlus;
import com.weknowall.cn.wuwei.widget.recyclerview.adapter.ViewHolderPlus;
import com.weknowall.cn.wuwei.widget.snapHelper.GravitySnapHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 注意：这里为了实现中间item正好居中，不设置item之接的间距，而是通过给item左右相同的margin，
 */
public class RecyclerViewPagerDemo extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private ViewPagerAdapter mAdapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_pager);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(manager=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        mRecyclerView.addItemDecoration(new LinearItemDecoration(getContext().getResources().getDimensionPixelOffset(R.dimen.size_20),LinearItemDecoration.Orientation.Horizontal));
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), manager.getOrientation());
//        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.draw_shape_rectangle_white_solid));
//        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mAdapter=new ViewPagerAdapter(getContext()));

        // 这个SnapHelper是扩展自LinearSnapHelper，可以实现不同的对其位置，但是不能实现pager功能
//        GravitySnapHelper snapHelper=new GravitySnapHelper(Gravity.START);
//        snapHelper.setMaxFlingDistance(getContext().getResources().getDimensionPixelOffset(R.dimen.size_420));
//        // 如果recyclerview有padding可以保证滑到padding的地方
//        snapHelper.setSnapToPadding(true);
//        // 设置滑动每英寸所用的毫秒数
//        snapHelper.setScrollMsPerInch(10f);
//        snapHelper.attachToRecyclerView(mRecyclerView);

        new PagerSnapHelper().attachToRecyclerView(mRecyclerView);

        ArrayList list=new ArrayList();
        list.add("a");
        list.add("a");
        list.add("a");
        mAdapter.insertRange(list,false);
    }

    class ViewPagerAdapter extends AdapterPlus<String> {

        public ViewPagerAdapter(Context context) {
            super(context);
        }

        @Override
        public ViewHolderPlus<String> onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater) {
            return new ItemViewHolder(createView(R.layout.item_view_pager, parent, inflater));
        }

        @Override
        public void onBindViewHolder(ViewHolderPlus<String> holder, int position) {

            ItemViewHolder holder1 = (ItemViewHolder) holder;

            switch (position%3){
                case 0:
                    holder1.mGeneralView.setBackgroundColor(Color.parseColor("#222222"));
                    break;
                case 1:
                    holder1.mGeneralView.setBackgroundColor(Color.parseColor("#ff4c39"));
                    break;
                case 2:
                    holder1.mGeneralView.setBackgroundColor(Color.parseColor("#000000"));
                    break;
            }
            holder1.mNum.setText(position+"");

        }

        @Override
        public int getItemCount() {
            return getList().size()*10;
        }

        class ItemViewHolder extends ViewHolderPlus<String> {
            @BindView(R.id.general_view)
            View mGeneralView;
            @BindView(R.id.general_number)
            TextView mNum;

            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @Override
            public void onBinding(int position, String s) {
            }
        }

    }

}
