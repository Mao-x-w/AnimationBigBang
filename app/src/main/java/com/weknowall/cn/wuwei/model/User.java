package com.weknowall.cn.wuwei.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: laomao
 * Date: 2020/5/28
 * Time: 10:15 AM
 */
public class User implements Parcelable {

    private String userName;
    private String sex;
    private Student student;

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.sex);
        dest.writeParcelable(this.student, flags);
    }

    protected User(Parcel in) {
        this.userName = in.readString();
        this.sex = in.readString();
        this.student = in.readParcelable(Student.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
