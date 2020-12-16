package com.aah.recruitmentassignment.ui.login.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.aah.recruitmentassignment.R
import com.aah.recruitmentassignment.models.AuthModel
import com.aah.recruitmentassignment.models.LoginModel
import com.aah.recruitmentassignment.network.networkService.LoginService

class LoginViewModel : ViewModel() {

    val isSuccessFull = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val authLiveData = MutableLiveData<AuthModel>()
//    val otpLiveData = MutableLiveData<OtpModel>()

    fun setSuccessFull(boolean:Boolean){
        isSuccessFull.value = boolean
    }
    fun login(username: String, password: String) {
        loading.value = true

        if(username.isEmpty() || !username.contains("@")){
            message.value = "Invalid username"
            loading.value = false
            return
        }
        if(!isPasswordValid(password)){
            message.value = "Invalid password"
            loading.value = false
            return
        }

        val loginModel = LoginModel(username, password)
        LoginService.requestLogin(MutableLiveData(), loginModel).observeForever { authModel ->
            if (authModel.success!!) {
                authLiveData.value = authModel
            }
            else {
                message.value = authModel.message
            }
            isSuccessFull.value = authModel.success
            loading.value = false
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}