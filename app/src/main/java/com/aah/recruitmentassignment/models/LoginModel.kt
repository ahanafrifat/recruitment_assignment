package com.aah.recruitmentassignment.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginModel {
    @SerializedName("username")
    @Expose
    var username: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    constructor() {}
    constructor(username: String?, password: String?) {
        this.username = username
        this.password = password
    }
}