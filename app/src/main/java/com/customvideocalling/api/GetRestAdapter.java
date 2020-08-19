package com.customvideocalling.api;



import com.customvideocalling.utils.SharedPrefClass;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRestAdapter {
     private static final String LOCAL = "https://conference.infinitywebtechnologies.com:8089/api/v1/";
   //  private static final String LOCAL = "https://stgsd.appsndevs.com/IEMS/api/";
//    private static final String LOCAL = "https://stgsd.appsndevs.com/IsmsTest/api/";
    //private static final String LOCAL = "https://stgsd.appsndevs.com/ISMSQA/api/";
  // private static final String LOCAL = "https://stgsd.appsndevs.com/IsmsTest/api/";
    //    private static final String LOCAL = "http://stgsd.appsndevs.com/EducationProApi/api/";
    private static final String HOST_URL = LOCAL;
    private static final String APPLICATION_JSON = "application/json";

    private GetRestAdapter() {
    }

    public static ApiInterface getRestAdapter(boolean isAuthRequired) {
        ApiInterface retrofitInterface = null;
        OkHttpClient.Builder builder = UnsafeOkHttpClient.getUnsafeOkHttpClient().newBuilder();
        builder.readTimeout(3000, TimeUnit.SECONDS);
        builder.connectTimeout(15000, TimeUnit.SECONDS);

        //Print Api Logs
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.interceptors().add(logging);

        SharedPrefClass sharedPref = new SharedPrefClass();
    //    final String token = sharedPref.getPrefValue(PreferenceKeys.LOGIN_TOKEN);

        if (isAuthRequired) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().
                       //     addHeader("Authorization", "Bearer" + " " + token).
                            addHeader("Content-Type", APPLICATION_JSON).
                            addHeader("accept", APPLICATION_JSON).build();
                    return chain.proceed(request);
                }
            });
        } else {
            builder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("Content-Type", APPLICATION_JSON)
                            .addHeader("accept", APPLICATION_JSON).build();
                    return chain.proceed(request);
                }
            });
        }

        retrofitInterface = new Retrofit.Builder()
                .baseUrl(HOST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build()
                .create(ApiInterface.class);
        return retrofitInterface;
    }
}
