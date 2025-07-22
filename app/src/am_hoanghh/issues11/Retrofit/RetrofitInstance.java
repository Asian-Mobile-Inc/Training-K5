package issues11.Retrofit;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final String ACCESS_TOKEN = "h8dOaQrEYtR7ZJRPxcwKp4fFeDJ2LabnhBT8jlKlx4o";
    private static final String AUTHORIZATION = "Authorization";

    public static Retrofit getRetrofitInstance(String baseUrl) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .header(AUTHORIZATION, "Bearer " + ACCESS_TOKEN)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiInterface getApiInterface(String baseUrl) {
        return getRetrofitInstance(baseUrl).create(ApiInterface.class);
    }
}
