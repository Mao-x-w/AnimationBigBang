package com.weknowall.cn.wuwei.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * User: laomao
 * Date: 2020/3/23
 * Time: 10:20 AM
 */
public class Test implements Parcelable {

    private String name;

    public Test() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }


    protected Test(Parcel in) {
        this.name = in.readString();
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel source) {
            return new Test(source);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };
}
