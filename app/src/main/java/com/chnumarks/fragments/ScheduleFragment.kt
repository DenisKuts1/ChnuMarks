package com.chnumarks.fragments


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView

import com.chnumarks.R
import com.chnumarks.adapters.ExpandableListAdapter


class ScheduleFragment : Fragment() {

    private val days by lazy {
        arrayListOf(
                resources.getString(R.string.monday_schedule_fragment),
                resources.getString(R.string.tuesday_schedule_fragment),
                resources.getString(R.string.wednesday_schedule_fragment),
                resources.getString(R.string.thursday_schedule_fragment),
                resources.getString(R.string.friday_schedule_fragment)
        )
    }
    private val firstListContent = HashMap<String, List<String>>()
    private val secondListContent = HashMap<String, List<String>>()

    private lateinit var firstWeekListView: ExpandableListView
    private lateinit var secondWeekListView: ExpandableListView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_schedule, container, false)
        firstWeekListView = view.findViewById(R.id.first_week_list)
        secondWeekListView = view.findViewById(R.id.second_week_list)
        prepareDataForList()
        val firstAdapter = ExpandableListAdapter(activity, days, firstListContent)
        val secondAdapter = ExpandableListAdapter(activity, days, secondListContent)
        firstWeekListView.setAdapter(firstAdapter)
        secondWeekListView.setAdapter(secondAdapter)
        return view
    }

    private fun prepareDataForList() {
        /*TODO Getting data from database */
        for (day in days) {
            val list = arrayListOf("first", "second")
            firstListContent += Pair(day, list)
            list += "third"
            secondListContent += Pair(day, list)
        }
    }


}
