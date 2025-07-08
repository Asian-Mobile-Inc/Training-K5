package com.example.asian.issue9.model;

public class Hour {
    private Float mRain, mWintryMix, mSnow, mTemperature;

    public Hour(Float mRain, Float mWintryMix, Float mSnow, Float mTemperature) {
        this.mRain = mRain;
        this.mWintryMix = mWintryMix;
        this.mSnow = mSnow;
        this.mTemperature = mTemperature;
    }

    public Float getRain() {
        return mRain;
    }

    public void setRain(Float mRain) {
        this.mRain = mRain;
    }

    public Float getWintryMix() {
        return mWintryMix;
    }

    public void setWintryMix(Float mWintryMix) {
        this.mWintryMix = mWintryMix;
    }

    public Float getSnow() {
        return mSnow;
    }

    public void setSnow(Float mSnow) {
        this.mSnow = mSnow;
    }

    public Float getTemperature() {
        return mTemperature;
    }

    public void setTemperature(Float mTemperature) {
        this.mTemperature = mTemperature;
    }
}
