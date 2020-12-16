package com.aah.recruitmentassignment.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthModel():Parcelable {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("organization_name")
    @Expose
    var organizationName: String? = null

    @SerializedName("organization_logo")
    @Expose
    var organizationLogo: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    constructor(parcel: Parcel) : this() {
        success = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        token = parcel.readString()
        organizationName = parcel.readString()
        organizationLogo = parcel.readString()
        message = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(success)
        parcel.writeString(token)
        parcel.writeString(organizationName)
        parcel.writeString(organizationLogo)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuthModel> {
        override fun createFromParcel(parcel: Parcel): AuthModel {
            return AuthModel(parcel)
        }

        override fun newArray(size: Int): Array<AuthModel?> {
            return arrayOfNulls(size)
        }
    }
}