package com.example.biodun.mdb.Utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Biodun on 4/21/2017.
 */
object NetworkUtil {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
