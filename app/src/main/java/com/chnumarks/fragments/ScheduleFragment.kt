package com.chnumarks.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView

import com.chnumarks.R


class ScheduleFragment : Fragment() {

    val days = arrayListOf(
            resources.getString(R.string.monday_schedule_fragment),
            resources.getString(R.string.tuesday_schedule_fragment),
            resources.getString(R.string.wednesday_schedule_fragment),
            resources.getString(R.string.thursday_schedule_fragment),
            resources.getString(R.string.friday_schedule_fragment)
    )

    lateinit var firstWeekListView: ExpandableListView
    lateinit var secondWeekListView: ExpandableListView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_schedule, container, false)
        firstWeekListView = view.findViewById(R.id.first_week_list)
        secondWeekListView = view.findViewById(R.id.first_week_list)
        val firstListData = ArrayList<ArrayList<Map<String, String>>>()
        return view
    }



}
