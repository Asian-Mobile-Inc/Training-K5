package issues9;

public class Hour {
    private float mRain;
    private float mWintry;
    private float mSnow;
    private float mTemperature;

    public Hour(float mRain, float mWintry, float mSnow, float mTemperature) {
        this.mRain = mRain;
        this.mWintry = mWintry;
        this.mSnow = mSnow;
        this.mTemperature = mTemperature;
    }

    public float getmRain() {
        return mRain;
    }

    public void setmRain(float mRain) {
        this.mRain = mRain;
    }

    public float getmWintry() {
        return mWintry;
    }

    public void setmWintry(float mWintry) {
        this.mWintry = mWintry;
    }

    public float getmSnow() {
        return mSnow;
    }

    public void setmSnow(float mSnow) {
        this.mSnow = mSnow;
    }

    public float getmTemperature() {
        return mTemperature;
    }

    public void setmTemperature(float mTemperature) {
        this.mTemperature = mTemperature;
    }
}
