package com.weknowall.cn.wuwei.model;

import java.io.Serializable;

/**
 * User: laomao
 * Date: 2020/5/28
 * Time: 11:21 AM
 */
public class User2 implements Serializable {

    private static final long serialVersionUID = 6484097016830694188L;

    private String userName;
    private byte[] bytes = new byte[1024 * 1024];

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
