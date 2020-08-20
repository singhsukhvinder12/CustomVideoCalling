package com.customvideocalling.api

//* Created by Saira on 03/07/2019.


import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("user/scooter/rides/list")
    fun getRideHistory(@Query("page") page:String , @Query("limit") limit:String):Call<JsonObject>

    @PUT("uploadUser/")
    fun uploadUser(@Body mJsonObject:JsonObject):Call<JsonObject>


    @POST("login")
    fun callLogin(@Body jsonObject:JsonObject):Call<JsonObject>

    @POST("auth/logout/")
    fun callLogout(@Body mJsonObject:JsonObject):Call<JsonObject>

    @GET("sBookingList/{id}")
    fun getStudentBooings(@Path("id") id: String): Call<JsonObject>

    @GET("getCompleteClass/{id}")
    fun getStudentHistoryBooings(@Path("id") id: String): Call<JsonObject>
}