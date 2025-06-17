package com.example.asian.issue4.model;

public class Language {
    private int mFlagId;
    private String mCode;
    private String mName;
    private Boolean mSelected;

    public Language(int mFlagId, String mCode, String mName, Boolean mSelected) {
        this.mFlagId = mFlagId;
        this.mCode = mCode;
        this.mName = mName;
        this.mSelected = mSelected;
    }

    public int getFlagId() {
        return mFlagId;
    }

    public void setFlagId(int mFlagId) {
        this.mFlagId = mFlagId;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String mCode) {
        this.mCode = mCode;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public Boolean getSelected() {
        return mSelected;
    }

    public void setSelected(Boolean mSlected) {
        this.mSelected = mSlected;
    }
}
