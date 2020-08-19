package com.customvideocalling.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("data")
    @Expose
    var result: Result? = null
    @SerializedName("message")
    @Expose
    var goodsDeliveryMessage: String? = null
 /*   @SerializedName("goodsDeliveryMessage")
    @Expose
    var goodsDeliveryMessage: String? = null
    @SerializedName("recordsTotal")
    @Expose
    var recordsTotal: Int? = null
    @SerializedName("recordsFiltered")
    @Expose
    var recordsFiltered: Int? = null*/

    inner class Result {
        @SerializedName("userId")
        @Expose
        var userId: String? = null
        @SerializedName("userName")
        @Expose
        var userName: String? = null
        @SerializedName("authToken")
        @Expose
        var token: String? = null
        @SerializedName("type")
        @Expose
        var type: Int? = null

    }
}

