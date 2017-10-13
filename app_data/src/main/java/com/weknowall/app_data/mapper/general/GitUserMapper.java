package com.weknowall.app_data.mapper.general;

import com.weknowall.app_common.utils.MapperImpl;
import com.weknowall.app_data.entity.general.GitUserEntity;
import com.weknowall.app_domain.entity.general.GitUser;

import javax.inject.Inject;

/**
 * User: laomao
 * Date: 2017-03-01
 * Time: 18-02
 */

public class GitUserMapper extends MapperImpl<GitUserEntity,GitUser> {

    @Inject
    public GitUserMapper() {
    }

    @Override
    public GitUserEntity transform(GitUser g) {
        GitUserEntity ge=new GitUserEntity();
        ge.setAvatar(g.getAvatar());
        ge.setFollowersUrl(g.getFollowersUrl());
        ge.setFollowingUrl(g.getFollowingUrl());
        ge.setId(g.getId());
        ge.setName(g.getName());
        ge.setUserHtmlUrl(g.getUserHtmlUrl());
        ge.setUserUrl(g.getUserUrl());
        return ge;
    }

    @Override
    public GitUser transformTo(GitUserEntity g) {
        GitUser ge=new GitUser();
        ge.setAvatar(g.getAvatar());
        ge.setFollowersUrl(g.getFollowersUrl());
        ge.setFollowingUrl(g.getFollowingUrl());
        ge.setId(g.getId());
        ge.setName(g.getName());
        ge.setUserHtmlUrl(g.getUserHtmlUrl());
        ge.setUserUrl(g.getUserUrl());
        return ge;
    }
}
