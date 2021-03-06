package com.zack.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Entity mapped to table "NOTE".
 */
public class Note implements Parcelable {

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
    private Long id;
    private String title;
    private String content;
    private Long createTime;
    private Long lastModifyTime;
    private Boolean isDeleted;

    public Note() {
    }

    public Note(Long id) {
        this.id = id;
    }

    public Note(Long id, String title, String content, Long createTime, Long lastModifyTime, Boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.lastModifyTime = lastModifyTime;
        this.isDeleted = isDeleted;
    }

    protected Note(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.content = in.readString();
        this.createTime = (Long) in.readValue(Long.class.getClassLoader());
        this.lastModifyTime = (Long) in.readValue(Long.class.getClassLoader());
        this.isDeleted = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeValue(this.createTime);
        dest.writeValue(this.lastModifyTime);
        dest.writeValue(this.isDeleted);
    }
}
