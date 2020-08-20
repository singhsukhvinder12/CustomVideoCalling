package com.customvideocalling.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ClassSubjectListResponse {
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


    inner class Result {
        @SerializedName("class")
        @Expose
        var classList: ArrayList<Subject>? = null
        @SerializedName("subject")
        @Expose
        var subjectList: ArrayList<Subject>? = null

    }

     class Subject{
        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("name")
        @Expose
        var name:String? = null
    }
}

