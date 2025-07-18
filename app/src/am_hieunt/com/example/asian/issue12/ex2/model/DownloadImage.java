package com.example.asian.issue12.ex2.model;

import android.graphics.Bitmap;

public class DownloadImage {
    private int mProgress;
    private double mSpeed;
    private Bitmap mImage;

    public DownloadImage(int mProgress, double mSpeed, Bitmap mImage) {
        this.mProgress = mProgress;
        this.mSpeed = mSpeed;
        this.mImage = mImage;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int mProgress) {
        this.mProgress = mProgress;
    }

    public double getSpeed() {
        return mSpeed;
    }

    public void setSpeed(double mSpeed) {
        this.mSpeed = mSpeed;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap mImage) {
        this.mImage = mImage;
    }
}
