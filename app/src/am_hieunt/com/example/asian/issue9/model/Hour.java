package com.example.asian.issue9.model;

public class Hour {
    private Float mRain, mWintryMix, mSnow, mTemperatureStart, mTemperatureEnd;

    public Hour(Float mRain, Float mWintryMix, Float mSnow, Float mTemperatureStart, Float mTemperatureEnd) {
        this.mRain = mRain;
        this.mWintryMix = mWintryMix;
        this.mSnow = mSnow;
        this.mTemperatureStart = mTemperatureStart;
        this.mTemperatureEnd = mTemperatureEnd;
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

    public Float getTemperatureStart() {
        return mTemperatureStart;
    }

    public void setTemperatureStart(Float mTemperatureStart) {
        this.mTemperatureStart = mTemperatureStart;
    }

    public Float getTemperatureEnd() {
        return mTemperatureEnd;
    }

    public void setTemperatureEnd(Float mTemperatureEnd) {
        this.mTemperatureEnd = mTemperatureEnd;
    }
}
