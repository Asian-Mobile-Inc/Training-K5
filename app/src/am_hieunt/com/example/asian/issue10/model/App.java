package com.example.asian.issue10.model;

public class App {
    private int mId;
    private int mIcon;
    private String mName;
    private String mHolder;
    private Boolean mFavorite;

    public App(int mId, int mIcon, String mName, String mHolder, Boolean mFavorite) {
        this.mId = mId;
        this.mIcon = mIcon;
        this.mName = mName;
        this.mHolder = mHolder;
        this.mFavorite = mFavorite;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getHolder() {
        return mHolder;
    }

    public void setHolder(String mHolder) {
        this.mHolder = mHolder;
    }

    public Boolean getFavorite() {
        return mFavorite;
    }

    public void setFavorite(Boolean mFavorite) {
        this.mFavorite = mFavorite;
    }
}
