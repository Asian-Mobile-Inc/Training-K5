package com.example.asian.issue5.model;

public class DrawText {
    private String mFont;
    private Boolean mSelected;

    public DrawText(String mFont, Boolean mSelected) {
        this.mFont = mFont;
        this.mSelected = mSelected;
    }

    public String getFont() {
        return mFont;
    }

    public void setFont(String font) {
        this.mFont = font;
    }

    public Boolean getSelected() {
        return mSelected;
    }

    public void setSelected(Boolean mSelected) {
        this.mSelected = mSelected;
    }
}
