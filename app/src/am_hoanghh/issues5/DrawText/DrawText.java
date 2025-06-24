package issues5.DrawText;

public class DrawText {
    private String content;
    private int font;
    private boolean isSelected;

    public DrawText(String content, int font) {
        this.content = content;
        this.font = font;
        this.isSelected = false;
    }

    public DrawText(String content, int font, boolean isSelected) {
        this.content = content;
        this.font = font;
        this.isSelected = isSelected;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
