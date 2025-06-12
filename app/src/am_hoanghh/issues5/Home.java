package issues5;

public class Home {
    private String title;
    private int image;
    private int like;
    private boolean isFavorite;

    public Home(String title, int image, int like, boolean isFavorite) {
        this.title = title;
        this.image = image;
        this.like = like;
        this.isFavorite = isFavorite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
