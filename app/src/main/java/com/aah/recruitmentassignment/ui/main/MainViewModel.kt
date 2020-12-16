package com.aah.recruitmentassignment.ui.main

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.recruitmentassignment.models.CvFile
import com.aah.recruitmentassignment.models.UploadModel
import com.aah.recruitmentassignment.network.networkService.UploadRequestBody
import com.aah.recruitmentassignment.network.networkService.UploadService
import com.aah.recruitmentassignment.utils.AppUtils
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"
    val message = MutableLiveData<String>()
    val isDataUploadSuccessFull = MutableLiveData<Boolean>()
    val isFileUploadSuccessFull = MutableLiveData<Boolean>()
    val uploadModelLiveData = MutableLiveData<UploadModel>()
    val loading = MutableLiveData<Boolean>()
    val progress = MutableLiveData<Int>()

    fun submit(
        token: String,
        name: String,
        email: String,
        phone: String,
        fullAddress: String,
        nameOfUniversity: String,
        graduationYear: String,
        cgpa: String,
        experienceInMonths: String,
        currentWorkPlaceName: String,
        applyingIn: String,
        expectedSalary: String,
        fieldBuzzReference: String,
        githubProjectUrl: String,
        cvFileName: String,
        cvFile: File
    ) {
        val tsyncId = UUID.randomUUID().toString()
        val timestamp = AppUtils.getTodayMillis()
        progress.value = 0
        loading.value = true
        try {
            val graduationYearTemp = graduationYear.toInt()
            if (graduationYearTemp < 2015 || graduationYearTemp > 2020) {
                message.value = "Graduation year must be between 2015 to 2020"
                return
            }

            if(!cgpa.isBlank()) {
                val cgpaTemp = cgpa.toDouble()
                if (cgpaTemp < 2.0 || cgpaTemp > 4.0) {
                    message.value = "CGPA out of range"
                    return
                }
            }

            val expectedSalaryTemp = expectedSalary.toInt()
            if (expectedSalaryTemp < 15000 || expectedSalaryTemp > 60000) {
                message.value = "Expected salary out of range"
                return
            }

            if (applyingIn.equals("Select")) {
                message.value = "Applying in not selected"
                return
            }

            AppUtils.log(
                TAG,
                "tsyncId: $tsyncId , name: $name , " + "email: $email , phone: $phone , fullAddress: $fullAddress , nameOfUniversity: $nameOfUniversity " +
                        ", graduationYear: $graduationYear , cgpa: $cgpa , experienceInMonths: $experienceInMonths , currentWorkPlaceName: $currentWorkPlaceName , " +
                        "applyingIn: $applyingIn ,expectedSalary: $expectedSalary ,fieldBuzzReference: $fieldBuzzReference , " +
                        "githubProjectUrl: $githubProjectUrl ,cvFileName: $cvFileName , timestamp: $timestamp"
            )

            val cvFileTemp = CvFile()
            val tsyncIdForFile = UUID.randomUUID().toString()
            cvFileTemp.tsyncId = tsyncIdForFile

            val uploadModel = UploadModel()
            uploadModel.tsyncId = tsyncId
            uploadModel.name = name
            uploadModel.email = email
            uploadModel.phone = phone
            if (!fullAddress.isBlank())
                uploadModel.fullAddress = fullAddress
            uploadModel.nameOfUniversity = nameOfUniversity
            uploadModel.graduationYear = graduationYear.toInt()
            if (!cgpa.isBlank())
                uploadModel.cgpa = cgpa.toDouble()
            if (!experienceInMonths.isBlank())
                uploadModel.experienceInMonths = experienceInMonths.toInt()
            if (!currentWorkPlaceName.isBlank())
                uploadModel.currentWorkPlaceName = currentWorkPlaceName
            uploadModel.applyingIn = applyingIn
            uploadModel.expectedSalary = expectedSalary.toInt()
            if (!fieldBuzzReference.isBlank())
                uploadModel.fieldBuzzReference = fieldBuzzReference
            uploadModel.githubProjectUrl = githubProjectUrl
            uploadModel.onSpotUpdateTime = timestamp
            uploadModel.onSpotCreationTime = timestamp
            uploadModel.cvFile = cvFileTemp

            AppUtils.log(TAG, "before" + Gson().toJson(uploadModel))

            UploadService.uploadForm(MutableLiveData(), uploadModel, token)
                .observeForever { uploadModelResponse ->
                    if (uploadModelResponse.success!!) {
                        AppUtils.log(TAG, "after: " + Gson().toJson(uploadModelResponse))
                        uploadModelLiveData.value = uploadModelResponse
                        message.value = uploadModelResponse.message
                        uploadFile(token, cvFile, uploadModelResponse)
                    } else {
                        message.value = uploadModelResponse.message
                        loading.value = false
                    }
                    isDataUploadSuccessFull.value = uploadModelResponse.success

                }

        } catch (e: Exception) {
            message.value = "${e.message}"
            progress.value = 0
            loading.value = false
        }
    }

    private fun uploadFile(token: String, cvFile: File, uploadModel: UploadModel) {
        val fileToken = uploadModel.cvFile?.id!!

        val body = UploadRequestBody(cvFile, "Form-Data", object : UploadRequestBody.UploadCallback {
                override fun onProgressUpdate(percentage: Int) {
                    progress.value = percentage
                }
            })

        val part = MultipartBody.Part.createFormData("Form-Data", cvFile.name, body)

        UploadService.uploadFile(MutableLiveData(), part, fileToken, token)
            .observeForever { uploadModelResponse ->

                if (uploadModelResponse.success!!) {

                    AppUtils.log(TAG, "after: " + Gson().toJson(uploadModelResponse))
                    uploadModelLiveData.value = uploadModelResponse
                    message.value = uploadModelResponse.message
                    progress.value = 100

                } else {
                    progress.value = 0
                    message.value = uploadModelResponse.message
                }

                isFileUploadSuccessFull.value = uploadModelResponse.success
                loading.value = false

            }
    }

}