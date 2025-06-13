package issues5;

public class DrawText {
    private String content;
    private int font;

    public DrawText(String content, int font) {
        this.content = content;
        this.font = font;
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
}
