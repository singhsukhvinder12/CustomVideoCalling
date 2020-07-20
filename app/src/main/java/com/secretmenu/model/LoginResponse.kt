package com.secretmenu.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("result")
    @Expose
    var result: Result? = null
    @SerializedName("goodsDeliveryMessage")
    @Expose
    var goodsDeliveryMessage: String? = null
    @SerializedName("recordsTotal")
    @Expose
    var recordsTotal: Int? = null
    @SerializedName("recordsFiltered")
    @Expose
    var recordsFiltered: Int? = null

    inner class Result {
        @SerializedName("userId")
        @Expose
        var userId: Int? = null
        @SerializedName("firstName")
        @Expose
        var firstName: String? = null
        @SerializedName("lastName")
        @Expose
        var lastName: String? = null
        @SerializedName("mobilePhone")
        @Expose
        var mobilePhone: String? = null
        @SerializedName("email")
        @Expose
        var email: String? = null
        @SerializedName("blockStatus")
        @Expose
        var blockStatus: String? = null
        @SerializedName("token")
        @Expose
        var token: String? = null
        @SerializedName("countryName")
        @Expose
        var countryName: String? = null
        @SerializedName("ocupation")
        @Expose
        var ocupation: String? = null
        @SerializedName("hobbies")
        @Expose
        var hobbies: String? = null
        @SerializedName("education")
        @Expose
        var education: String? = null
        @SerializedName("companyName")
        @Expose
        var companyName: String? = null
        @SerializedName("companyTaxNumber")
        @Expose
        var companyTaxNumber: String? = null
        @SerializedName("companyAccountNumber")
        @Expose
        var companyAccountNumber: String? = null
        @SerializedName("otpVerified")
        @Expose
        var otpVerified: String? = null
        @SerializedName("address")
        @Expose
        var address: String? = null

    }
}

