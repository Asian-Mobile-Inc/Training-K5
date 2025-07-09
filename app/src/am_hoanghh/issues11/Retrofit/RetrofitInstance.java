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
    private static String sBaseUrl;
    private static Retrofit sRetrofit;
    private static final String ACCESS_TOKEN = "h8dOaQrEYtR7ZJRPxcwKp4fFeDJ2LabnhBT8jlKlx4o";

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .header("Authorization", " Bearer " + ACCESS_TOKEN)
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    public static Retrofit getRetrofitInstance() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .client(CLIENT)
                    .baseUrl(sBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }

    public static ApiInterface getApiInterface(String baseUrl) {
        sBaseUrl = baseUrl;
        return getRetrofitInstance().create(ApiInterface.class);
    }
}
