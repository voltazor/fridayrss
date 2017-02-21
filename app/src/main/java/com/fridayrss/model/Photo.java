package com.fridayrss.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by voltazor on 23/04/16.
 */
public class Photo extends RealmObject implements BaseModel, Comparable<Photo>,Parcelable {

    public static final String DB_FRIDAY_ID = "fridayId";

    @PrimaryKey
    @SerializedName(ID)
    private String id;

    @SerializedName("thumb")
    private String thumb;

    @SerializedName("url")
    private String url;

    @SerializedName("friday_id")
    private String fridayId;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFridayId() {
        return fridayId;
    }

    public void setFridayId(String fridayId) {
        this.fridayId = fridayId;
    }

    @Override
    public int compareTo(@NonNull Photo another) {
        return (int) (Long.parseLong(another.getId()) - Long.parseLong(getId()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.thumb);
        dest.writeString(this.url);
        dest.writeString(this.fridayId);
    }

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.id = in.readString();
        this.thumb = in.readString();
        this.url = in.readString();
        this.fridayId = in.readString();
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

}
