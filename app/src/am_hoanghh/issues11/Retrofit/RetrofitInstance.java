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
    private static final String BASE_URL = "https://api.gyazo.com/api/";
    private static Retrofit sRetrofit;
    private static final String ACCESS_TOKEN = "L8QpJEHeSKHxpGrJdD-QFwvCFNzjW0aZWd3FA5odKD4";

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", " Bearer " + ACCESS_TOKEN)
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    public static Retrofit getRetrofitInstance() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .client(CLIENT)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }

    public static ApiInterface getApiInterface() {
        return getRetrofitInstance().create(ApiInterface.class);
    }
}
