package com.chnumarks.fragments.toolbars

import android.os.Build
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chnumarks.MainActivity
import com.chnumarks.R
import com.chnumarks.fragments.menu.EditFragment
import com.chnumarks.listeners.NavigationDrawerListener
import com.chnumarks.setLightStatusBar

/**
 * Created by denak on 20.02.2018.
 */
class EditToolbarFragment : Fragment() {
    lateinit var toolbar: Toolbar
    lateinit var tabLayout: TabLayout
    lateinit var fragment: EditFragment
    lateinit var drawerLayout: DrawerLayout

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.edit_toolbar_fragment, container, false)

        toolbar = view.findViewById(R.id.edit_toolbar)
        tabLayout = view.findViewById(R.id.edit_tab_layout)
        fragment.setUpTabLayout(tabLayout)
        setLightStatusBar(toolbar, activity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val offset = (25 * resources.displayMetrics.density + 0.5f).toInt()
            toolbar.setPadding(0, offset, 0, 0)
        }
        drawerLayout.addDrawerListener(NavigationDrawerListener(toolbar,activity as MainActivity))

        val mainActivity = activity as MainActivity
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)

        return view
    }

    fun setEditFragment(fragment: EditFragment){
        this.fragment = fragment
    }

}