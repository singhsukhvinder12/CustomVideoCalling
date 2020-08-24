package com.customvideocalling.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TeacherNotificationListResponse {
    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("data")
    @Expose
    var result: ArrayList<Result>? = null
    @SerializedName("message")
    @Expose
    var message: String? = null


     class Result {
         @SerializedName("bookId")
         @Expose
         var bookId: String? = null
         @SerializedName("teacherId")
         @Expose
         var teacherId:String? = null
         @SerializedName("description")
         @Expose
         var description:String? = null
         @SerializedName("status")
         @Expose
         var status:Int? = null
         @SerializedName("bookingDetail")
         @Expose
         var bookingDetail:BookingDetail? = null


    }
    class BookingDetail{
        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("studentId")
        @Expose
        var studentId: String? = null
        @SerializedName("bookingDate")
        @Expose
        var bookingDate: String? = null
        @SerializedName("timeSlot")
        @Expose
        var timeSlot: String? = null
        @SerializedName("userDetail")
        @Expose
        var userDetail: UserDetail? = null
    }

    class UserDetail{
        @SerializedName("fName")
        @Expose
        var firstName: String? = null
        @SerializedName("lName")
        @Expose
        var lastName: String? = null
    }
}

