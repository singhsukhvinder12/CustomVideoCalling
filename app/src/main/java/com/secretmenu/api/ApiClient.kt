package com.secretmenu.api

/*
 Created by Saira on 03/07/2019.
*/


import android.text.TextUtils
import androidx.annotation.NonNull
import com.secretmenu.constants.GlobalConstants
import com.secretmenu.constants.GlobalConstants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiClient {
    @JvmStatic
    private var mApiInterface: ApiInterface? = null

    @JvmStatic
    fun getApiInterface(): ApiInterface {
        return setApiInterface()
    }

    @JvmStatic
    private fun setApiInterface(): ApiInterface {
        val mAuthToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFuc2FyaXNhaXJhQHNlYXNpYWluZm90ZWNoLmNvbSIsInVzZXJfaWQiOjQsInVzZXJfcmVsIjoiNWM5MjEwZWE2YTFmYzQyYzAzYjg3NTMxIiwiaWF0IjoxNTY5ODQ1MTE2fQ.POYee6s3fUZWlKJs0EprSOlvOBbB9edQBePFYrkzAmM"
        val lang = "en"
        val httpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
        val mBuilder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())


        if (!TextUtils.isEmpty(mAuthToken)) {
            val interceptor: Interceptor = object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(@NonNull chain: Interceptor.Chain): Response {
                    val original = chain.request()
                    val builder = original.newBuilder()
                            .header("lang", lang)
                            .header("Application-Type", GlobalConstants.PLATFORM)
                            .header("Device-Token", mAuthToken)
                            .header("Application-Token", "AIzaSyBqXAR8vhmDd3nNYmaC1B9VPlU8qHQbtP4")
                    val request = builder.build()
                    return chain.proceed(request)
                }
            }



            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor)
                mBuilder.client(httpClient.build())
                mApiInterface = mBuilder.build().create(ApiInterface::class.java)
            }
        } else
            mApiInterface = mBuilder.build().create(ApiInterface::class.java)

        return mApiInterface!!
    }

}
