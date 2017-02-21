package com.fridayrss.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by voltazor on 30/10/16.
 */
public class Friday extends RealmObject implements BaseModel, Comparable<Friday>, Parcelable {

    @PrimaryKey
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("thumb")
    private String thumb;

    public Friday() {

    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @Override
    public int compareTo(@NonNull Friday another) {
        return (int) (Long.parseLong(getId()) - Long.parseLong(another.getId()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.thumb);
    }

    protected Friday(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.thumb = in.readString();
    }

    public static final Parcelable.Creator<Friday> CREATOR = new Parcelable.Creator<Friday>() {
        @Override
        public Friday createFromParcel(Parcel source) {
            return new Friday(source);
        }

        @Override
        public Friday[] newArray(int size) {
            return new Friday[size];
        }
    };

}
