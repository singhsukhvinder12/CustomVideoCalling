package com.customvideocalling.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProfileResponse {
    @SerializedName("code")
    @Expose
    var code: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    /*   "code": 200,
    "success": true,
    "message": "Success",
    "data": {
        "id": "45662491-bb48-4c2d-9f3e-4fd65cbf348c",
        "email": "nick001@gmail.com",
        "userDetail": {
            "fName": "Nick",
            "lName": "sharma",
            "dob": "1990-01-12",
            "uniqueId": "55555",
            "planId": 0,
            "rememberToken": "",
            "info": "",
            "grade": "",
            "needed": "",
            "help": "",
            "superhero": "",
            "summery": "",
            "image": ""
        }
    }*/

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("userDetail")
        @Expose
        var userDetail: UserDetail? = null


    }
    inner class UserDetail {

        @SerializedName("dob")
        @Expose
        var dob: String? = null

        @SerializedName("uniqueId")
        @Expose
        var uniqueId: String? = null

        @SerializedName("planId")
        @Expose
        var planId: String? = null

        @SerializedName("rememberToken")
        @Expose
        var rememberToken: String? = null

        @SerializedName("fName")
        @Expose
        var fName: String? = null

        @SerializedName("lName")
        @Expose
        var lName: String? = null

        @SerializedName("info")
        @Expose
        var info: String? = null

        @SerializedName("grade")
        @Expose
        var grade: String? = null

        @SerializedName("help")
        @Expose
        var help: String? = null

        @SerializedName("needed")
        @Expose
        var needed: String? = null

        @SerializedName("summery")
        @Expose
        var summery: String? = null

        @SerializedName("superhero")
        @Expose
        var superhero: String? = null

    }


}
