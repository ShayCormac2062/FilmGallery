package com.example.filmgallery.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.example.filmgallery.R
import com.google.android.material.bottomnavigation.BottomNavigationView

object CommonUtils {

    fun makeToast(context: Context?, string: String) {
        Toast.makeText(
            context,
            string,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun enableNavigationButton(activity: Activity?, id: Int) {
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
            ?.menu
            ?.getItem(id)
            ?.isChecked = true
    }

}
