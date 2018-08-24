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
class NavigationDrawerListener(val toolbar: Toolbar, val activity: MainActivity) : DrawerLayout.DrawerListener {

    override fun onDrawerStateChanged(newState: Int) {}

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

    override fun onDrawerClosed(drawerView: View) {
        setLightStatusBar(toolbar)
    }

    override fun onDrawerOpened(drawerView: View) {
        clearLightStatusBar(toolbar)
    }
}