package issues11.Model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Image {
    public static final int TYPE_DEFAULT = 1;
    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_UPLOAD = 2;
    public static final int TYPE_FAILED = 3;

    @SerializedName("image_id")
    private String id;
    @SerializedName("url")
    private String url;
    @SerializedName("created_at")
    private Timestamp createdAt;
    private int statusType;
    private boolean isSelected;
    private boolean isChecked;
    private boolean isLoading;

    public Image(String id, String url, Timestamp createdAt, int statusType, boolean isSelected, boolean isChecked, boolean isLoading) {
        this.id = id;
        this.url = url;
        this.createdAt = createdAt;
        this.statusType = statusType;
        this.isSelected = isSelected;
        this.isChecked = isChecked;
        this.isLoading = isLoading;
    }

    public Image(String id, String url, Timestamp createdAt, int statusType, boolean isSelected, boolean isChecked) {
        this.id = id;
        this.url = url;
        this.createdAt = createdAt;
        this.statusType = statusType;
        this.isSelected = isSelected;
        this.isChecked = isChecked;
    }

    public Image(String id, String url, int statusType, boolean isSelected, boolean isChecked) {
        this.id = id;
        this.url = url;
        this.statusType = statusType;
        this.isSelected = isSelected;
        this.isChecked = isChecked;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
