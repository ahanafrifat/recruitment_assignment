package com.aah.recruitmentassignment.network.networkService

import androidx.lifecycle.MutableLiveData
import com.aah.recruitmentassignment.models.AuthModel
import com.aah.recruitmentassignment.models.LoginModel
import com.aah.recruitmentassignment.models.UploadModel
import com.aah.recruitmentassignment.network.retrofit.ApiClient
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UploadService {
    @Synchronized
    fun uploadForm(uploadModelMutableLiveData: MutableLiveData<UploadModel>, uploadModel: UploadModel, token:String): MutableLiveData<UploadModel> {

        ApiClient.api.uploadInfo(token, uploadModel).enqueue(object : Callback<UploadModel> {
            override fun onResponse(call: Call<UploadModel>, response: Response<UploadModel>) {
                if (response.isSuccessful && response.code() == 201) {
                    uploadModelMutableLiveData.postValue(response.body() as UploadModel)
                } else {
                    val failed= Gson().fromJson(response.errorBody()!!.string(), UploadModel::class.java)
                    uploadModelMutableLiveData.postValue(failed)
                }
            }

            override fun onFailure(call: Call<UploadModel>, t: Throwable) {
                val failed = UploadModel()
                failed.message = "Couldn't Connect"
                failed.success = false
                uploadModelMutableLiveData.postValue(failed)
            }

        })

        return uploadModelMutableLiveData
    }

    @Synchronized
    fun uploadFile(uploadModelMutableLiveData: MutableLiveData<UploadModel>, file: MultipartBody.Part, fileTokenId:String, token:String): MutableLiveData<UploadModel> {

        ApiClient.api.uploadFile(token ,fileTokenId, file).enqueue(object : Callback<UploadModel> {
            override fun onResponse(call: Call<UploadModel>, response: Response<UploadModel>) {
                if (response.isSuccessful && response.code() == 201) {
                    uploadModelMutableLiveData.postValue(response.body() as UploadModel)
                } else {
                    response.errorBody()?.let {
                        val failed= Gson().fromJson(it.string(), UploadModel::class.java)
                        uploadModelMutableLiveData.postValue(failed)
                    }

                    response.body()?.let {
                        uploadModelMutableLiveData.postValue(it)
                    }
//                    val failed= Gson().fromJson(response.errorBody()!!.string(), UploadModel::class.java)
//                    uploadModelMutableLiveData.postValue(failed)
                }
            }

            override fun onFailure(call: Call<UploadModel>, t: Throwable) {
                val failed = UploadModel()
                failed.message = "Couldn't Connect"
                failed.success = false
                uploadModelMutableLiveData.postValue(failed)
            }

        })

        return uploadModelMutableLiveData
    }
}