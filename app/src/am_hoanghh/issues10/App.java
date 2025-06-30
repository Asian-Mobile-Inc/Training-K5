package issues10;

public class App {
    private int mIcon;
    private String mName;
    private boolean mIsFavorite;

    public App(int mIcon, String mName) {
        this.mIcon = mIcon;
        this.mName = mName;
    }

    public App(int mIcon, String mName, boolean mIsFavorite) {
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

    public boolean ismIsFavorite() {
        return mIsFavorite;
    }

    public void setmIsFavorite(boolean mIsFavorite) {
        this.mIsFavorite = mIsFavorite;
    }
}
