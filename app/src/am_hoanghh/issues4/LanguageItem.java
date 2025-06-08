package issues4;

public class LanguageItem {
    private int icResId;
    private String languageName;

    public LanguageItem(int icResId, String languageName) {
        this.icResId = icResId;
        this.languageName = languageName;
    }

    public int getIcResId() {
        return icResId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setIcResId(int icResId) {
        this.icResId = icResId;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}
