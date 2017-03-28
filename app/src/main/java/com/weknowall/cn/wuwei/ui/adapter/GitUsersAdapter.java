package com.weknowall.cn.wuwei.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weknowall.app_presenter.entity.general.GitUser;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.widget.image.CircleImageView;
import com.weknowall.cn.wuwei.widget.recyclerview.adapter.AdapterPlus;
import com.weknowall.cn.wuwei.widget.recyclerview.adapter.ViewHolderPlus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecylcerView对应Adapter
 */
public class GitUsersAdapter extends AdapterPlus<GitUser> {

    public GitUsersAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolderPlus<GitUser> onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater) {
        return new GitUsersViewHolder(inflater.inflate(getLayout(), parent, false));
    }

    public int getLayout() {
        return R.layout.item_git_users;
    }

    public class GitUsersViewHolder extends ViewHolderPlus<GitUser> {

        @BindView(R.id.git_users_avator)
        CircleImageView mAvator;
        @BindView(R.id.git_users_name)
        TextView mName;

        public GitUsersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBinding(int position, GitUser gitUser) {
            mAvator.setImageUrl(gitUser.getAvatar());
            mName.setText(gitUser.getName());
        }
    }

}
