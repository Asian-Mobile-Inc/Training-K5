package com.example.asian.issue4.model;

public class Language {
    private int mFlagId;
    private String mCode;
    private String mName;

    public Language(int mFlagId, String mCode, String mName) {
        this.mFlagId = mFlagId;
        this.mCode = mCode;
        this.mName = mName;
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
}
