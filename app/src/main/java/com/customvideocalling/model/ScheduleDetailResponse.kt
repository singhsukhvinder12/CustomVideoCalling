package com.customvideocalling.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ScheduleDetailResponse {
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
         @SerializedName("name")
         @Expose
         var name: String? = null
         @SerializedName("slots")
         @Expose
         var slotsList: ArrayList<Slots>? = null
    }

    class Slots{
        @SerializedName("slot")
        @Expose
        var slot: String? = null
        @SerializedName("bookings")
        @Expose
        var bookings:String? = null
    }
}

