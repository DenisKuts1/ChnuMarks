package com.chnumarks.fragments.menu.edit


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView

import com.chnumarks.R
import com.chnumarks.adapters.ExpandableListAdapter
import android.opengl.ETC1.getWidth
import com.chnumarks.adapters.SubjectListAdapter
import com.chnumarks.models.Subject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class EditScheduleFragment : Fragment() {

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
        val view = inflater!!.inflate(R.layout.edit_schedule_fragment, container, false)
        firstWeekListView = view.findViewById(R.id.first_week_list)
        secondWeekListView = view.findViewById(R.id.second_week_list)
        prepareDataForList()
        val firstAdapter = ExpandableListAdapter(activity, days, firstListContent)
        val secondAdapter = ExpandableListAdapter(activity, days, secondListContent)
        firstWeekListView.setAdapter(firstAdapter)
        secondWeekListView.setAdapter(secondAdapter)
        val listener = ExpandableListView.OnGroupClickListener { p0, p1, p2, p3 ->
            setListViewHeight(p0, p2)
            false
        }
        firstWeekListView.setOnGroupClickListener(listener)
        secondWeekListView.setOnGroupClickListener(listener)
        return view
    }

    private fun prepareDataForList() {

        for (day in days) {
            val list = arrayListOf("first", "second")
            firstListContent += Pair(day, list)
            list += "third"
            secondListContent += Pair(day, list)
        }
    }

    fun setListViewHeight(listView: ExpandableListView, group: Int) {
        val listAdapter = listView.expandableListAdapter as ExpandableListAdapter
        var totalHeight = 0
        val desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.width,
                View.MeasureSpec.EXACTLY)
        for (i in 0 until listAdapter.groupCount) {
            val groupItem = listAdapter.getGroupView(i, false, null, listView)
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)

            totalHeight += groupItem.measuredHeight

            if (listView.isGroupExpanded(i) && i != group || !listView.isGroupExpanded(i) && i == group) {
                for (j in 0 until listAdapter.getChildrenCount(i)) {
                    val listItem = listAdapter.getChildView(i, j, false, null,
                            listView)
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)

                    totalHeight += listItem.measuredHeight

                }
            }
        }

        val params = listView.layoutParams
        var height = totalHeight + listView.dividerHeight * (listAdapter.groupCount - 1)
        if (height < 10)
            height = 200
        params.height = height
        listView.layoutParams = params
        listView.requestLayout()
    }


}
