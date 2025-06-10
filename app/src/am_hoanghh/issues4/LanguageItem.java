package issues4;

public class LanguageItem {
    private int icResId;
    private String languageName;
    private String languageCode;
    private boolean isSelected;

    public LanguageItem(int icResId, String languageName, String languageCode) {
        this.icResId = icResId;
        this.languageName = languageName;
        this.languageCode = languageCode;
        this.isSelected = false;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
