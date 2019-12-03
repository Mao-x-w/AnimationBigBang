package com.weknowall.app_domain.entity.general;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: laomao
 * Date: 2019-11-18
 * Time: 17-09
 */
public class Temp implements Parcelable {
    private String name;

    public Temp() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    protected Temp(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Temp> CREATOR = new Parcelable.Creator<Temp>() {
        @Override
        public Temp createFromParcel(Parcel source) {
            return new Temp(source);
        }

        @Override
        public Temp[] newArray(int size) {
            return new Temp[size];
        }
    };
}
