package com.aah.recruitmentassignment.ui.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aah.recruitmentassignment.R
import com.aah.recruitmentassignment.databinding.MainFragmentBinding
import com.aah.recruitmentassignment.models.AuthModel
import com.aah.recruitmentassignment.utils.*
import java.io.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var authModel: AuthModel
    private var token: String = ""

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var pdfFile: File
    private val TAG = "MainFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        return inflater.inflate(R.layout.main_fragment, container, false)
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        authModel = arguments?.getParcelable<AuthModel>(AUTH_MODEL)!!
        token = "Token ${authModel.token}"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        (activity as AppCompatActivity?)!!.supportActionBar!!.setHomeButtonEnabled(true)

        val items = listOf("Select", "Backend", "Mobile")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.applyingIn.setAdapter(adapter)

        binding.experienceInMonths.filters = arrayOf<InputFilter>(InputFilterMinMax(0, 100))
        binding.cvFile.setOnClickListener {
            openFile()
        }

        binding.submit.setOnClickListener {
            if (isAllRequireFieldsGiven()){
                viewModel.submit(
                    token,
                    binding.name.text.toString(),
                    binding.email.text.toString(),
                    binding.phone.text.toString(),
                    binding.fullAddress.text.toString(),
                    binding.nameOfUniversity.text.toString(),
                    binding.graduationYear.text.toString(),
                    binding.cgpa.text.toString(),
                    binding.experienceInMonths.text.toString(),
                    binding.currentWorkPlaceName.text.toString(),
                    binding.applyingIn.text.toString(),
                    binding.expectedSalary.text.toString(),
                    binding.fieldBuzzReference.text.toString(),
                    binding.githubProjectUrl.text.toString(),
                    binding.cvFile.text.toString(),
                    pdfFile
                )
            }
        }

        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(binding.root, it)
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {loading->
            loading?.let {
                if(it){
                    binding.group.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                else{
                    binding.group.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

        viewModel.progress.observe(viewLifecycleOwner, Observer {progress->
//            AppUtils.message(binding.root, it)
            progress?.let {
                if(it in 1..99){
//                    binding.progressBar.visibility = View.VISIBLE
                    binding.progressBar.progress = it
                }
                else{
                    binding.progressBar.visibility = View.GONE
                }
            }

        })
    }

    private fun isAllRequireFieldsGiven():Boolean {
        var given = true
        if(binding.name.text.toString().isBlank()){
            given = false
            binding.name.error = "Please enter a name"
        }
        if(binding.email.text.toString().isBlank()){
            given = false
            binding.email.error = "Please enter Email"
        }
        if(binding.phone.text.toString().isBlank()){
            given = false
            binding.phone.error = "Please enter phone no"
        }
        if(binding.nameOfUniversity.text.toString().isBlank()){
            given = false
            binding.nameOfUniversity.error = "Please enter name of University"
        }
        if(binding.graduationYear.text.toString().isBlank()){
            given = false
            binding.graduationYear.error = "Please enter graduation year"
        }
        if(binding.applyingIn.text.toString().isBlank()){
            given = false
            binding.applyingIn.error = "Please enter applying In"
        }
        if(binding.expectedSalary.text.toString().isBlank()){
            given = false
            binding.expectedSalary.error = "Please enter expected salary"
        }
        if(binding.githubProjectUrl.text.toString().isBlank()){
            given = false
            binding.githubProjectUrl.error = "Please enter github project url"
        }
//        if(binding.cvFile.text.toString().isBlank()){
//            given = false
//            binding.cvFileLabel.error = "Please upload cv file"
//            AppUtils.message(binding.root, "Please upload cv file")
//        }

        if(!::pdfFile.isInitialized){
            given = false
            AppUtils.message(binding.root, "Please upload cv file")
        }

        return given
    }

    fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }

        startActivityForResult(intent, PICK_PDF_FILE)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_PDF_FILE && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri ->
//                val returnCursor: Cursor? = context?.getContentResolver()?.query(
//                    uri,
//                    null,
//                    null,
//                    null,
//                    null
//                )
//                val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
//                val sizeIndex = returnCursor?.getColumnIndex(OpenableColumns.SIZE)
//                returnCursor?.moveToFirst()
//                pdfUri = uri
//                val fileName = returnCursor?.getString(nameIndex!!)
                val fileName = context?.contentResolver?.getFileName(uri)
                binding.cvFile.text = fileName
                AppUtils.log("Testfile", "Successful : ${fileName}")
                uploadFile(uri, fileName!!)
            }
        }
    }

    private fun uploadFile(cvUri: Uri, cvName: String) {

//        val reqFile = RequestBody.create("Form-Data".toMediaTypeOrNull(), cvUri))
//        val filePart = MultipartBody.Part.createFormData("Form-Data", cvName, reqFile)

//        pdfMulti = filePart

        val parcelFileDescriptor = context?.contentResolver?.openFileDescriptor(cvUri , "r", null)?:return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(context?.cacheDir, cvName)
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        pdfFile = file
    }

}