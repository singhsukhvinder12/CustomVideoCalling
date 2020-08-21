package com.customvideocalling.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TokenHistoryListResponse {
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
         @SerializedName("token")
         @Expose
         var token: String? = null
         @SerializedName("tokenBalance")
         @Expose
         var tokenBalance:Int? = null
         @SerializedName("type")
         @Expose
         var type:String? = null
         @SerializedName("createdAt")
         @Expose
         var createdAt:String? = null


    }
}

