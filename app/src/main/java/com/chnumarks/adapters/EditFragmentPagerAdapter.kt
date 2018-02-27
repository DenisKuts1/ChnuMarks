package com.chnumarks.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import com.chnumarks.R
import com.chnumarks.fragments.menu.edit.EditScheduleFragment
import com.chnumarks.fragments.menu.edit.EditSubjectFragment

/**
 * Created by denak on 14.02.2018.
 */
class EditFragmentPagerAdapter(val context: Context, fragmentManager: FragmentManager, manager: com.chnumarks.fragments.FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    val editScheduleFragment = EditScheduleFragment()
    val editSubjectFragment = EditSubjectFragment()

    init {
        editSubjectFragment.fragmentManager = manager
    }

    override fun getItem(position: Int) = when (position) {
        0 -> editScheduleFragment
        else -> editSubjectFragment
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int) = when (position) {
        0 -> context.resources.getString(R.string.tab_schedule)
        else -> context.resources.getString(R.string.tab_subjects)
    }
}