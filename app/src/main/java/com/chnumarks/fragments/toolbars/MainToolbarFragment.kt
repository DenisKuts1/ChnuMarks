package com.chnumarks.fragments.toolbars

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.chnumarks.MainActivity
import com.chnumarks.R
import com.chnumarks.setLightStatusBar

/**
 * Created by denak on 20.02.2018.
 */
class MainToolbarFragment : Fragment() {
    lateinit var toolbar: Toolbar

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.main_toolbar_fragment, container, false)

        // Setting up toolbar
        toolbar = view.findViewById(R.id.main_toolbar)
        setLightStatusBar(toolbar, activity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val offset = (25 * resources.displayMetrics.density + 0.5f).toInt()
            toolbar.setPadding(0, offset, 0, 0)
        }

        // Setting toolbar as app's actionbar
        val mainActivity = activity as MainActivity
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)

        return view
    }
}