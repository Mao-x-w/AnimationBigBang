package com.weknowall.app_presenter.entity.general;

import android.widget.ImageView;

/**
 * User: laomao
 * Date: 2017-03-01
 * Time: 18-01
 */

public class GitUser {
    private String name;
    private int id;
    private String avatar;
    private String userUrl;
    private String userHtmlUrl;
    private String followersUrl;
    private String followingUrl;

    private boolean checked;
    private ImageView animationView;

    public GitUser() {
    }

    public GitUser(String name, int id, String avatar, String userUrl, String userHtmlUrl, String followersUrl, String followingUrl) {
        this.name = name;
        this.id = id;
        this.avatar = avatar;
        this.userUrl = userUrl;
        this.userHtmlUrl = userHtmlUrl;
        this.followersUrl = followersUrl;
        this.followingUrl = followingUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getUserHtmlUrl() {
        return userHtmlUrl;
    }

    public void setUserHtmlUrl(String userHtmlUrl) {
        this.userHtmlUrl = userHtmlUrl;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitUser gitUser = (GitUser) o;

        return id == gitUser.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    public ImageView getAnimationView() {
        return animationView;
    }

    public void setAnimationView(ImageView animationView) {
        this.animationView = animationView;
    }
}
