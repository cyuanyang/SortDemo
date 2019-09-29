package com.sort;

import android.os.Parcel;
import android.os.Parcelable;

public class BinaryTree implements Parcelable {


    protected BinaryTree(Parcel in) {
    }

    public static final Creator<BinaryTree> CREATOR = new Creator<BinaryTree>() {
        @Override
        public BinaryTree createFromParcel(Parcel in) {
            return new BinaryTree(in);
        }

        @Override
        public BinaryTree[] newArray(int size) {
            return new BinaryTree[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
