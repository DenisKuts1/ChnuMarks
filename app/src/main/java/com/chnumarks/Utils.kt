package com.chnumarks

import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.View

/**
 * Created by denak on 10.02.2018.
 */
fun setLightStatusBar(view: View, activity: Activity){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        view.systemUiVisibility = view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun clearLightStatusBar(view: View, activity: Activity){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        view.systemUiVisibility = 0
    }

}
