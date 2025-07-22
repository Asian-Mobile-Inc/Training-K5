package com.example.asian.issue11.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.asian.R;
import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("image_id")
    private String image_id;
    @SerializedName("url")
    private String url;
    private int select;
    private int background;
    private Bitmap bitmap;
    private String status;

    public Image() {
        this.select = R.drawable.ic_cancel;
        this.bitmap = null;
        this.status = "";
        this.background = R.drawable.my_bg_image;
    }

    public Image(String image_id, String url) {
        this.image_id = image_id;
        this.url = url;
        this.select = R.drawable.ic_cancel;
        this.bitmap = null;
        this.status = "";
        this.background = R.drawable.my_bg_image;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }
}
