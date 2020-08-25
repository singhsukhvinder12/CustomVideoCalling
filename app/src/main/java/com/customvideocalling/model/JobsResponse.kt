package com.customvideocalling.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class JobsResponse {
    @SerializedName("code")
    @Expose
    var code : Int? = null
    @SerializedName("message")
    @Expose
    var message : String? = null
    @SerializedName("data")
    @Expose
    var data : ArrayList<Data>? = null

    /*  "id": "030859da-1681-4f57-afd9-ffe14f086787",
            "bookingDate": "2020-08-19",
            "teacherId": "e66374dc-2793-4c82-b903-ed86ffe2f0df",
            "timeSlot": "4:00 PM",
            "fName": "KLteacher",
            "lName": "kledi"*/
    inner class Data {

        @SerializedName("id")
        @Expose
        var id : String? = null
        @SerializedName("bookingDate")
        @Expose
        var bookingDate : String? = null
        @SerializedName("userId")
        @Expose
        var userId : String? = null
        @SerializedName("teacherId")
        @Expose
        var teacherId : String? = null
        @SerializedName("timeSlot")
        @Expose
        var timeSlot : String? = null
        @SerializedName("fName")
        @Expose
        var fName : String? = null
        @SerializedName("lName")
        @Expose
        var lName : String? = null
        @SerializedName("channelName")
        @Expose
        var channelName : String? = null
        @SerializedName("accessToken")
        @Expose
        var accessToken : String? = null
        @SerializedName("status")
        @Expose
        var status : Int? = null
    }

}
