package com.customvideocalling.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class facultyDesignationResponse {
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
    var message: String? = null
 /*   @SerializedName("goodsDeliveryMessage")
    @Expose
    var goodsDeliveryMessage: String? = null
    @SerializedName("recordsTotal")
    @Expose
    var recordsTotal: Int? = null
    @SerializedName("recordsFiltered")
    @Expose
    var recordsFiltered: Int? = null*/

     class Result {
        @SerializedName("faculty")
        @Expose
        var facultyList: ArrayList<facultyItem>? = null
        @SerializedName("designation")
        @Expose
        var designationList: ArrayList<facultyItem>? = null
    }
    class facultyItem{
        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("name")
        @Expose
        var name: String? = null
    }
}

