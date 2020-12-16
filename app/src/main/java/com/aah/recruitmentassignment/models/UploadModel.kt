package com.aah.recruitmentassignment.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UploadModel {
    @SerializedName("tsync_id")
    @Expose
    var tsyncId: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("full_address")
    @Expose
    var fullAddress: String? = null

    @SerializedName("name_of_university")
    @Expose
    var nameOfUniversity: String? = null

    @SerializedName("graduation_year")
    @Expose
    var graduationYear: Int? = null

    @SerializedName("cgpa")
    @Expose
    var cgpa: Double? = null

    @SerializedName("experience_in_months")
    @Expose
    var experienceInMonths: Int? = null

    @SerializedName("current_work_place_name")
    @Expose
    var currentWorkPlaceName: String? = null

    @SerializedName("applying_in")
    @Expose
    var applyingIn: String? = null

    @SerializedName("expected_salary")
    @Expose
    var expectedSalary: Int? = null

    @SerializedName("field_buzz_reference")
    @Expose
    var fieldBuzzReference: String? = null

    @SerializedName("github_project_url")
    @Expose
    var githubProjectUrl: String? = null

    @SerializedName("cv_file")
    @Expose
    var cvFile: CvFile? = null

    @SerializedName("on_spot_update_time")
    @Expose
    var onSpotUpdateTime: Long? = null

    @SerializedName("on_spot_creation_time")
    @Expose
    var onSpotCreationTime: Long? = null

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}