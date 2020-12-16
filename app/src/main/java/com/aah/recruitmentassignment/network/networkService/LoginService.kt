package com.aah.recruitmentassignment.network.networkService

import androidx.lifecycle.MutableLiveData
import com.aah.recruitmentassignment.models.AuthModel
import com.aah.recruitmentassignment.models.LoginModel
import com.aah.recruitmentassignment.network.retrofit.ApiClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object LoginService {
    @Synchronized
    fun requestLogin(authModel: MutableLiveData<AuthModel>, loginModel: LoginModel): MutableLiveData<AuthModel> {

        ApiClient.api.requestLogin(loginModel).enqueue(object : Callback<AuthModel> {
            override fun onResponse(call: Call<AuthModel>, response: Response<AuthModel>) {
                if (response.isSuccessful && response.code() == 200) {
                    authModel.postValue(response.body() as AuthModel)
                } else {
                    val failed= Gson().fromJson(response.errorBody()!!.string(), AuthModel::class.java)
                    authModel.postValue(failed)
                }
            }

            override fun onFailure(call: Call<AuthModel>, t: Throwable) {
                val failed = AuthModel()
                failed.message = "Couldn't Connect"
                failed.success = false
                authModel.postValue(failed)
            }

        })

        return authModel
    }
}