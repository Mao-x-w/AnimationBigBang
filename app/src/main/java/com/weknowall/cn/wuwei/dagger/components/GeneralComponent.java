package com.weknowall.cn.wuwei.dagger.components;

import com.weknowall.app_presenter.dagger.modules.ActivityModule;
import com.weknowall.app_presenter.dagger.scope.PerActivity;
import com.weknowall.cn.wuwei.ui.activity.BezierCurveActivity;
import com.weknowall.cn.wuwei.ui.activity.GitUsersActivity;

import dagger.Component;

/**
 * User: mao
 * Date: 2017/1/29
 * Time: 9:51
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = {ActivityModule.class})
public interface GeneralComponent extends ActivityComponent{
    void inject(GitUsersActivity activity);
    void inject(BezierCurveActivity activity);
}
