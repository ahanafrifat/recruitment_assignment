package com.aah.recruitmentassignment.utils

import android.content.ContentResolver
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.TextView
import com.aah.recruitmentassignment.R
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    fun log(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    fun message(
        view: View?,
        msg: String?,
        textColor: Int,
        backgroundColor: Int
    ) {
        if (view == null) return
        val snack = Snackbar.make(view, msg!!, Snackbar.LENGTH_SHORT)
        val snackBarView = snack.view
        snackBarView.setBackgroundColor(backgroundColor)
        val snackBarText = snackBarView.findViewById<TextView>(R.id.snackbar_text)
        snackBarText.setTextColor(textColor)
        snack.show()
    }

    fun message(
        view: View?,
        msg: String?
    ) {
        try {
            if (view == null) return
            val snack = Snackbar.make(view, msg!!, Snackbar.LENGTH_SHORT)
            val snackBarView = snack.view
            snackBarView.setBackgroundColor(Color.GRAY)
            val snackBarText = snackBarView.findViewById<TextView>(R.id.snackbar_text)
            snackBarText.setTextColor(Color.WHITE)
            snack.show()
        }
        catch (e: Exception){
            return
        }
    }

    fun getTodayMillis(): Long {
        val d = Date()
        val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = dateFormat.format(d)
        try {
            return dateFormat.parse(date).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0L
    }
}