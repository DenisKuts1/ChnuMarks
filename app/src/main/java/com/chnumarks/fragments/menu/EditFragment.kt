package com.chnumarks.fragments.menu

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chnumarks.R
import com.chnumarks.adapters.EditFragmentPagerAdapter
import com.chnumarks.fragments.FragmentManager

/**
 * Created by denak on 14.02.2018.
 */
class EditFragment : Fragment() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var manager: FragmentManager
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.edit_fragment, container, false)
        viewPager = view.findViewById(R.id.edit_view_pager)
        viewPager.adapter = EditFragmentPagerAdapter(this.activity, childFragmentManager, manager)
        tabLayout.setupWithViewPager(viewPager)
        return view
    }

    fun setUpTabLayout(tabLayout: TabLayout) {
        this.tabLayout = tabLayout
    }

    override fun onDetach() {
        super.onDetach()
        viewPager.adapter = null
    }
}