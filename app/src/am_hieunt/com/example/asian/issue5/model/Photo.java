package com.example.asian.issue5.model;

public class Photo {
    private int mPhotoId;
    private String mName;
    private int mPen;
    private Boolean mFavorite;

    public Photo(int mPhotoId, String mName, int mPen, Boolean mFavorite) {
        this.mPhotoId = mPhotoId;
        this.mName = mName;
        this.mPen = mPen;
        this.mFavorite = mFavorite;
    }

    public int getPhotoId() {
        return mPhotoId;
    }

    public void setPhotoId(int mPhotoId) {
        this.mPhotoId = mPhotoId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getPen() {
        return mPen;
    }

    public void setPen(int mPen) {
        this.mPen = mPen;
    }

    public Boolean getFavorite() {
        return mFavorite;
    }

    public void setFavorite(Boolean mFavorite) {
        this.mFavorite = mFavorite;
    }
}
