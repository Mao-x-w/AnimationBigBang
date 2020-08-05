package com.weknowall.app_presenter.presenter.general;

import com.weknowall.app_domain.entity.general.GitUser;
import com.weknowall.app_domain.executor.PostExecutionThread;
import com.weknowall.app_domain.executor.ThreadExecutor;
import com.weknowall.app_domain.repository.IGeneralRepository;
import com.weknowall.app_presenter.presenter.LoadingPresenter;
import com.weknowall.app_presenter.view.IGitUsersView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


/**
 * User: laomao
 * Date: 2017-03-01
 * Time: 18-19
 */

public class GitUsersPresenter extends LoadingPresenter<IGeneralRepository,Object,Object,List<GitUser>,List<GitUser>,IGitUsersView> {

    @Inject
    public GitUsersPresenter(IGeneralRepository repository, ThreadExecutor workThread, PostExecutionThread resultThread) {
        super(repository, workThread, resultThread);
    }

//    @Inject
//    public GitUsersPresenter(IGeneralRepository repository,
//                             @Named(Constants.NAMED_GIT_USERS)
//                             @NonNull UseCase<Object, List<GitUserModel>> useCase, GitUserMapper gMapper) {
//        super(repository, useCase);
//        mGMapper = gMapper;
//    }

    @Override
    public void initialize(Object... objects) {
        execute(objects);
    }

    @Override
    protected Observable<List<GitUser>> buildUseCaseObservable(Object... objects) {
        return getRepository().getGitUsers();
    }

    @Override
    public void onNext(List<GitUser> gitUserModels) {
        super.onNext(gitUserModels);
        getView().onGetGitUsers(gitUserModels);
    }
}
