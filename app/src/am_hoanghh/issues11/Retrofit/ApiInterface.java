package issues11.Retrofit;

import java.io.File;
import java.util.List;

import issues11.Model.Image;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("images")
    Call<List<Image>> getAllImages();

    @Multipart
    @POST("upload")
    Call<Image> uploadImage(
            @Part MultipartBody.Part filePart
    );

    @DELETE("images/{image_id}")
    Call<Image> deleteImageById(@Path("image_id") String id);
}
