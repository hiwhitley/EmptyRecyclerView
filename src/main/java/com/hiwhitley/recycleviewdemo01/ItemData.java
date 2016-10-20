package com.hiwhitley.recycleviewdemo01;

/**
 * Created by hiwhitley on 2016/4/25.
 */
public class ItemData {
    private int imgResId;
    private String title;

    public ItemData(int imgResId, String title) {
        this.imgResId = imgResId;
        this.title = title;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
