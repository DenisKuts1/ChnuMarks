package com.chnumarks.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.chnumarks.R
import com.chnumarks.fragments.ScheduleFragment
import com.chnumarks.fragments.SubjectFragment

/**
 * Created by denak on 14.02.2018.
 */
class EditFragmentPagerAdapter : FragmentPagerAdapter {
    val context: Context

    constructor(context: Context, fragmentManager: FragmentManager) : super(fragmentManager) {
        this.context = context
    }

    override fun getItem(position: Int) = when (position) {
        0 -> ScheduleFragment()
        else -> SubjectFragment()

    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int) = when(position) {
        0 -> context.resources.getString(R.string.tab_schedule)
        else -> context.resources.getString(R.string.tab_subjects)
    }
}