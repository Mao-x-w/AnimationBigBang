package com.weknowall.cn.wuwei.ui.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weknowall.cn.wuwei.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: mao
 * Date: 2017/4/3
 * Time: 11:41
 */
public class SwipeDeleteAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {

    private List<String> mDatas;

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swipe_delete, parent, false);
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new ItemViewHolder(realContentView);
    }

    public void setDatas(List<String> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        if (mDatas != null) {
            viewHolder.setData(mDatas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.swipe_delete_title)
        TextView mTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(String title) {
            mTitle.setText(title);
        }
    }

}
