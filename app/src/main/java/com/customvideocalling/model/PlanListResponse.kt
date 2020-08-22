package com.customvideocalling.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PlanListResponse {
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
         @SerializedName("id")
         @Expose
         var id: Int? = null
         @SerializedName("token_count")
         @Expose
         var tokenCount:Int? = null
         @SerializedName("token_amount")
         @Expose
         var tokenAmount:Float? = null
         @SerializedName("description")
         @Expose
         var description:String? = null


    }
}

