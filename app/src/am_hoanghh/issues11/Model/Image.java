package issues11.Model;

import com.google.gson.annotations.SerializedName;

public class Image {
    public static final int TYPE_DEFAULT = 1;
    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_UPLOAD = 2;
    public static final int TYPE_FAILED = 3;

    @SerializedName("image_id")
    private String id;
    @SerializedName("url")
    private String url;
    private int statusType;
    private boolean isSelected;

    public Image(String id, String url) {
        this.id = id;
        this.url = url;
        this.statusType = TYPE_IMAGE;
        this.isSelected = false;
    }

    public Image(int statusType) {
        this.statusType = statusType;
        this.isSelected = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatusType() {
        return statusType;
    }

    public void setStatusType(int statusType) {
        this.statusType = statusType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
