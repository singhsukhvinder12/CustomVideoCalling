package com.customvideocalling.api

//* Created by Saira on 03/07/2019.


import com.customvideocalling.model.ClassSubjectListResponse
import com.customvideocalling.model.PlanListResponse
import com.customvideocalling.model.SlotListResponse
import com.customvideocalling.model.TokenHistoryListResponse
import com.example.artha.model.CommonModel
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

    @POST("sRegister")
    fun callStudentSignUp(@Body jsonObject:JsonObject):Call<JsonObject>

    @POST("tRegister")
    fun callTeacherSignUp(@Body jsonObject:JsonObject):Call<JsonObject>

    @POST("auth/logout/")
    fun callLogout(@Body mJsonObject:JsonObject):Call<JsonObject>

    @GET("sBookingList/{id}")
    fun getStudentBooings(@Path("id") id: String): Call<JsonObject>

    @GET("getCompleteClass/{id}")
    fun getStudentHistoryBooings(@Path("id") id: String): Call<JsonObject>

    @GET("streamOptions")
    fun getClassSubject():Call<ClassSubjectListResponse>

    @POST("addBooking")
    fun addBookingStudent(@Body jsonObject:JsonObject):Call<CommonModel>

    @POST("getAllSlots")
    fun getSlotList(@Body jsonObject:JsonObject):Call<SlotListResponse>

    @GET("getPlanlist")
    fun getPlanList():Call<PlanListResponse>

    @GET("sTokenHistory/{id}")
    fun getTokenHistoryList(@Path("id") id: String):Call<TokenHistoryListResponse>

    @POST("addToken")
    fun addToken(@Body jsonObject:JsonObject):Call<CommonModel>
}