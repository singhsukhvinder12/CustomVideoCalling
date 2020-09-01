package com.customvideocalling.repositories

import androidx.lifecycle.MutableLiveData
import com.customvideocalling.Interfaces.CallBackResult
import com.example.artha.model.CommonModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.customvideocalling.api.ApiResponse
import com.customvideocalling.api.ApiService
import com.customvideocalling.api.GetRestAdapter
import com.customvideocalling.model.ClassSubjectListResponse
import com.customvideocalling.model.LoginResponse
import com.customvideocalling.model.facultyDesignationResponse
import com.customvideocalling.views.authentication.TeacherSignupActivity
import com.google.gson.JsonElement
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class SignUpRepository {
    private var data : MutableLiveData<LoginResponse>? = null
    private var data1 : MutableLiveData<CommonModel>? = null
    private var data2: MutableLiveData<facultyDesignationResponse>? = null
    private var data3: MutableLiveData<ClassSubjectListResponse>? = null
    private val gson = GsonBuilder().serializeNulls().create()
    private val IMAGE_EXTENSION = "/*"

    fun toRequestBody(value: JsonElement): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), value.asString)
    }

    init {
        data = MutableLiveData()
        data1 = MutableLiveData()
        data2 = MutableLiveData()
        data3 = MutableLiveData()

    }

    /*fun getTeacherSignUpData(jsonObject : JsonObject?) : MutableLiveData<LoginResponse> {
        if (jsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<LoginResponse>("" + mResponse.body(), LoginResponse::class.java)
                        else {
                            gson.fromJson<LoginResponse>(
                                 mResponse.errorBody()!!.charStream(),
                                LoginResponse::class.java
                            )
                        }


                        data!!.postValue(loginResponse)

                    }

                    override fun onError(mKey : String) {
                       // UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data!!.postValue(null)

                    }

                }, GetRestAdapter.getRestAdapter(false).callTeacherSignUp(jsonObject)

            )

        }
        return data!!

    }*/

    fun getStudentSignUpData(jsonObject : JsonObject?) : MutableLiveData<LoginResponse> {
        if (jsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<LoginResponse>("" + mResponse.body(), LoginResponse::class.java)
                        else {
                            gson.fromJson<LoginResponse>(
                                mResponse.errorBody()!!.charStream(),
                                LoginResponse::class.java
                            )
                        }


                        data!!.postValue(loginResponse)

                    }

                    override fun onError(mKey : String) {
                       // UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data!!.postValue(null)

                    }

                }, GetRestAdapter.getRestAdapter(false).callStudentSignUp(jsonObject)

            )

        }
        return data!!

    }

    fun addDeviceToken(callBack: CallBackResult.AddDeviceTokenCallBack, mJsonObject: JsonObject): MutableLiveData<CommonModel> {
        val call = GetRestAdapter.getRestAdapter(false).addDeviceToken(mJsonObject)
        call.enqueue(object : Callback<CommonModel> {
            override fun onResponse(
                call: Call<CommonModel>,
                response: retrofit2.Response<CommonModel>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onAddDeviceTokenSuccess(response.body()!!)
                    }
                    else {
                        callBack.onAddDeviceTokenFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onAddDeviceTokenFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<CommonModel>, t: Throwable) {
                callBack.onAddDeviceTokenFailed(t.message!!)
            }
        })
        return data1!!
    }

    fun getFacultyDesignation(callBack: CallBackResult.FacultyDesignationCallBack): MutableLiveData<facultyDesignationResponse> {
        val call = GetRestAdapter.getRestAdapter(false).getFacultyDesignation()
        call.enqueue(object : Callback<facultyDesignationResponse> {
            override fun onResponse(
                call: Call<facultyDesignationResponse>,
                response: retrofit2.Response<facultyDesignationResponse>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onFacultyDesignationSuccess(response.body()!!)
                    }
                    else {
                        callBack.onFacultyDesignationFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onFacultyDesignationFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<facultyDesignationResponse>, t: Throwable) {
                callBack.onFacultyDesignationFailed(t.message!!)
            }
        })
        return data2!!
    }

    fun getClassSubjectList(callBack: CallBackResult.ClassSubjectListCallBack): MutableLiveData<ClassSubjectListResponse> {
        val call = GetRestAdapter.getRestAdapter(false).getClassSubject()
        call.enqueue(object : Callback<ClassSubjectListResponse> {
            override fun onResponse(
                call: Call<ClassSubjectListResponse>,
                response: retrofit2.Response<ClassSubjectListResponse>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onGetClassSubjectListSuccess(response.body()!!)
                    }
                    else {
                        callBack.onGetClassSubjectListFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onGetClassSubjectListFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<ClassSubjectListResponse>, t: Throwable) {
                callBack.onGetClassSubjectListFailed(t.message!!)
            }
        })
        return data3!!
    }

    fun getTeacherSignUpData(jsonObject : JsonObject?,activity: TeacherSignupActivity): MutableLiveData<LoginResponse> {

        val callResponse: Call<LoginResponse>
        if (jsonObject != null) {
        val map = HashMap<String, RequestBody>()
        map["email"] = toRequestBody(jsonObject!!.get("email"))
        map["password"] = toRequestBody(jsonObject!!.get("password"))
        map["lName"] = toRequestBody(jsonObject!!.get("lName"))
        map["fName"] = toRequestBody(jsonObject!!.get("fName"))
            map["subjects"] =  toRequestBody(jsonObject!!.get("subjects"))
        map["education"] = toRequestBody(jsonObject!!.get("education"))
        map["teacherId"] = toRequestBody(jsonObject!!.get("teacherId"))
        map["address"] = toRequestBody(jsonObject!!.get("address"))
        map["grade"] = toRequestBody(jsonObject!!.get("grade"))
        map["hours"] = toRequestBody(jsonObject!!.get("hours"))
        map["arrested"] = toRequestBody(jsonObject!!.get("arrested"))
        map["arrestedDetails"] = toRequestBody(jsonObject!!.get("arrestedDetails"))
        map["charged"] = toRequestBody(jsonObject!!.get("charged"))
        map["chargedDetails"] = toRequestBody(jsonObject!!.get("chargedDetails"))
        map["bankName"] = toRequestBody(jsonObject!!.get("bankName"))
        map["userName"] = toRequestBody(jsonObject!!.get("userName"))
        map["ifcCode"] = toRequestBody(jsonObject!!.get("ifcCode"))
        map["accountNumber"] = toRequestBody(jsonObject!!.get("accountNumber"))
//IDProof
            val licenseRequestFile =
                RequestBody.create(IMAGE_EXTENSION.toMediaTypeOrNull(), activity.licenseImageFile!!)
            val licenseImage =
                MultipartBody.Part.createFormData(
                    "license",
                    activity.selectedImage,
                    licenseRequestFile
                )

        val documentRequestFile =
            RequestBody.create(IMAGE_EXTENSION.toMediaTypeOrNull(), activity.documentFile!!)
        val document =
            MultipartBody.Part.createFormData(
                "documents",
                activity.selectedDocument,
                documentRequestFile
            )

        val reportFile =
            RequestBody.create(IMAGE_EXTENSION.toMediaTypeOrNull(), activity.reportFile!!)
        val report =
            MultipartBody.Part.createFormData(
                "report",
                activity.selectedReport,
                reportFile
            )

        val cvFile =
            RequestBody.create(IMAGE_EXTENSION.toMediaTypeOrNull(), activity.cvFile!!)
        val cv =
            MultipartBody.Part.createFormData(
                "cv",
                activity.selectedCV,
                cvFile
            )
           /* callResponse = com.seasia.isms.api.GetRestAdapter.getRestAdapter(true)
                .addTeacherWithOneImage(map, proofImage)*/

            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<LoginResponse>("" + mResponse.body(), LoginResponse::class.java)
                        else {
                            gson.fromJson<LoginResponse>(
                                mResponse.errorBody()!!.charStream(),
                                LoginResponse::class.java
                            )
                        }


                        data!!.postValue(loginResponse)

                    }

                    override fun onError(mKey : String) {
                        // UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data!!.postValue(null)

                    }

                }, GetRestAdapter.getRestAdapter(false).callTeacherSignUp(map, licenseImage, document,report,cv )

            )

        }
        return data!!
    }
}