package com.weknowall.cn.wuwei.ui.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weknowall.app_domain.entity.general.GitUser;
import com.weknowall.app_presenter.presenter.general.GitUsersPresenter;
import com.weknowall.app_presenter.view.IGitUsersView;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.dagger.components.DaggerGeneralComponent;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.ui.helper.ItemClickListener;
import com.weknowall.cn.wuwei.widget.BezierCurveAnimation;
import com.weknowall.cn.wuwei.widget.image.CircleImageView;
import com.weknowall.cn.wuwei.widget.recyclerview.adapter.AdapterPlus;
import com.weknowall.cn.wuwei.widget.recyclerview.adapter.ViewHolderPlus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2017-03-28
 * Time: 15-43
 */

public class BezierCurveActivity extends BaseActivity implements IGitUsersView {

    @BindView(R.id.add_your_fav_git_user)
    LinearLayout mAddGitUser;
    @BindView(R.id.git_users_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.root)
    RelativeLayout mRoot;

    @Inject
    GitUsersPresenter mPresenter;

    private BezierCurveAdapter mAdapter;

    public ArrayList<GitUser> checkLists = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_curve);
        ButterKnife.bind(this);

        initView();

        // 初始化网络请求
        DaggerGeneralComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).build().inject(this);
        mPresenter.setView(this);
        // 发起网络请求
        mPresenter.initialize();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter = new BezierCurveAdapter(getContext()));
        mAdapter.setOnItemClickListener(user -> {
            if (user.isChecked()) {
                // 执行添加操作
                executeAdd(user);
            } else {
                // 执行删除操作
                executeRemove(user);
            }
        });
    }

    /**
     * 从集合中删除选中用户
     *
     * @param user
     */
    private void executeRemove(GitUser user) {
        int index = checkLists.indexOf(user);
        // 执行删除食材动画
        View view = mAddGitUser.getChildAt(index);
        ViewCompat.animate(view).rotation(180).translationX(-50).translationY(-50).scaleX(0).scaleY(0).setDuration(500).setListener(new ViewPropertyAnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(View view) {
                super.onAnimationStart(view);
            }

            @Override
            public void onAnimationEnd(View view) {
                super.onAnimationEnd(view);
                checkLists.remove(user);
                mAddGitUser.removeViewAt(index);
                mAdapter.getList().get(user.getClickPosition()).setChecked(false);
                mAdapter.notifyItemChanged(user.getClickPosition());
            }
        }).start();
    }

    /**
     * 将用户添加到集合
     *
     * @param user
     */
    private void executeAdd(GitUser user) {
        checkLists.add(user);

        // 创建结束位置的View,并添加的布局中
        View view = View.inflate(getContext(), R.layout.selected_git_users, null);
        CircleImageView imageView = (CircleImageView) view.findViewById(R.id.combine_recipe_selected_food_material_image);
        imageView.setImageUrl(user.getAvatar());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.rightMargin = (int) getContext().getResources().getDimension(R.dimen.size_13);
        view.setVisibility(View.INVISIBLE);
        mAddGitUser.addView(view, layoutParams);

        view.setOnClickListener(v -> {
            // 点击执行删除
            executeRemove(user);
        });

        // 执行增加食材动画
        BezierCurveAnimation animation = new BezierCurveAnimation(user.getAnimationView(), imageView, mRoot);
        animation.startAnimation(getContext());
        animation.setAnimationListener(new BezierCurveAnimation.AnimationListener() {
            @Override
            public void onAnimationStart() {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd() {
                view.setVisibility(View.VISIBLE);
                mAdapter.notifyItemChanged(user.getClickPosition());
            }
        });

    }

    /**
     * 请求网络返回数据
     * @param users
     */
    @Override
    public void onGetGitUsers(List<GitUser> users) {
        mAdapter.clear();
        mAdapter.insertRange(users, false);
    }

    /**
     * RecyclerView对应的Adapter，虽然写到了内部类，但是充分解耦的，可以随便移动出去。
     */
    class BezierCurveAdapter extends AdapterPlus<GitUser> {


        public BezierCurveAdapter(Context context) {
            super(context);
        }

        @Override
        public ViewHolderPlus<GitUser> onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater) {
            return new ItemViewHolder(inflater.inflate(R.layout.item_git_users_selectable, parent, false));
        }

        class ItemViewHolder extends ViewHolderPlus<GitUser> {

            @BindView(R.id.selectable_git_users_avator)
            CircleImageView mAvator;
            @BindView(R.id.selectable_git_users_name)
            TextView mName;
            @BindView(R.id.selectable_git_users_check)
            ImageView mCheck;

            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(v -> {
                    boolean checked = getItemObject().isChecked();

                    // 设置条目对应的状态
                    getItemObject().setChecked(!checked);
                    getItemObject().setClickPosition(getPosition());
                    getItemObject().setAnimationView(mAvator);

                    // 将点击事件传递出去
                    if (mListener != null) {
                        mListener.onClick(getItemObject());
                    }
                });
            }

            @Override
            public void onBinding(int position, GitUser gitUser) {
                mAvator.setImageUrl(gitUser.getAvatar());
                mName.setText(gitUser.getName());
                mCheck.setVisibility(gitUser.isChecked() ? View.VISIBLE : View.INVISIBLE);
            }
        }

        private ItemClickListener mListener;

        public void setOnItemClickListener(ItemClickListener listener) {
            mListener = listener;
        }

    }


}
