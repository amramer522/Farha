package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable
{
    String fri_name;
    int fri_img;

    public Friend(String fri_name, int fri_img) {
        this.fri_name = fri_name;
        this.fri_img = fri_img;
    }

    public Friend(String fri_name) {
        this.fri_name = fri_name;
    }

    public Friend() {
    }

    protected Friend(Parcel in) {
        fri_name = in.readString();
        fri_img = in.readInt();
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    public String getFri_name()
    {
        return fri_name;
    }

    public void setFri_name(String fri_name) {
        this.fri_name = fri_name;
    }

    public int getFri_img() {
        return fri_img;
    }

    public void setFri_img(int fri_img) {
        this.fri_img = fri_img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fri_name);
        parcel.writeInt(fri_img);
    }
}
