package issues11.Model;

import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("image_id")
    private String id;
    @SerializedName("url")
    private String url;

    public Image(String id, String url) {
        this.id = id;
        this.url = url;
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
}
