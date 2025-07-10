package issues10.Model;

public class AppModel {
    private int mId;
    private int mIcon;
    private String mName;
    private int mIsFavorite;

    public AppModel(int mId, int mIcon, String mName, int mIsFavorite) {
        this.mId = mId;
        this.mIcon = mIcon;
        this.mName = mName;
        this.mIsFavorite = mIsFavorite;
    }

    public int getmIcon() {
        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmIsFavorite() {
        return mIsFavorite;
    }

    public void setmIsFavorite(int mIsFavorite) {
        this.mIsFavorite = mIsFavorite;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }
}
