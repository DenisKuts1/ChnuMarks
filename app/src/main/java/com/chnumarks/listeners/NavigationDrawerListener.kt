package com.chnumarks.listeners

import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.View
import com.chnumarks.MainActivity
import com.chnumarks.clearLightStatusBar
import com.chnumarks.setLightStatusBar

/**
 * Created by denak on 20.02.2018.
 */
class NavigationDrawerListener(val toolbar: Toolbar, val activity: MainActivity): DrawerLayout.DrawerListener {

    override fun onDrawerStateChanged(newState: Int) {}

    override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {}

    override fun onDrawerClosed(drawerView: View?) {
        if (drawerView != null) {
            setLightStatusBar(toolbar, activity)
        }
    }

    override fun onDrawerOpened(drawerView: View?) {
        if (drawerView != null) {
            clearLightStatusBar(toolbar, activity)
        }
    }
}