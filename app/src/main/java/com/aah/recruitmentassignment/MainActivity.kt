package com.aah.recruitmentassignment

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.aah.recruitmentassignment.databinding.MainActivityBinding
import com.aah.recruitmentassignment.ui.main.MainFragment
import com.aah.recruitmentassignment.utils.PICK_PDF_FILE

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.main_activity)
//        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val navController = Navigation.findNavController(binding.root)
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_FILE && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            data?.data?.also { uri ->
                // Perform operations on the document using its URI.
            }
        }
    }

    //    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
//        if (requestCode == your-request-code
//            && resultCode == Activity.RESULT_OK) {
//            // The result data contains a URI for the document or directory that
//            // the user selected.
//            resultData?.data?.also { uri ->
//                // Perform operations on the document using its URI.
//            }
//        }
//    }
}