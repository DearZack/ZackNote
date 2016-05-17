package com.zack.zacknote.bean;

/**
 * Created by Zack Zhou on 2016/5/15.
 */
public class Note {
    private long id;
    private String title;
    private String content;
    private String lastModifyTime;
    private String createTime;
    private boolean isDeleted;

    public Note() {
    }

    public Note(long id, String title, String content, String lastModifyTime, String createTime, boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.lastModifyTime = lastModifyTime;
        this.createTime = createTime;
        this.isDeleted = isDeleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
