package com.customvideocalling.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ScheduleListResponse {
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
         @SerializedName("fromDate")
         @Expose
         var fromDate: String? = null
         @SerializedName("toDate")
         @Expose
         var toDate:String? = null
    }
}

