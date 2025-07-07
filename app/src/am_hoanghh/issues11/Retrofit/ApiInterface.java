package issues11.Retrofit;

import java.util.List;

import issues11.Model.Image;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("images")
    Call<List<Image>> getAllImages();

    @GET("images/{image_id}")
    Call<Image> getImageById(@Path("image_id") String id);
}
