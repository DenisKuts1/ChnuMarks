package com.chnumarks.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.chnumarks.R

/**
 * Created by denak on 13.02.2018.
 */
class ExpandableListAdapter(private val context: Context, private val headers: List<String>, private val data: Map<String, List<String>>) : BaseExpandableListAdapter() {

    override fun getGroup(p0: Int) = headers[p0]

    override fun isChildSelectable(p0: Int, p1: Int) = true

    override fun hasStableIds() = false

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        val title = getGroup(p0)
        val view = if (p2 == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.edit_schedule_list_header, p3, false)
        } else {
            p2
        }
        val listHeaderText = view.findViewById<TextView>(R.id.list_header_text)
        listHeaderText.text = title
        return view
    }

    override fun getChildrenCount(p0: Int) = data[headers[p0]]!!.size + 1

    override fun getChild(p0: Int, p1: Int) = if (p1 != data[headers[p0]]!!.size) {
        data[headers[p0]]!![p1]
    } else {
        "Add new"
    }

    override fun getGroupId(p0: Int) = p0.toLong()

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        val text = getChild(p0, p1)
        val view: View
        if (p1 != data[headers[p0]]!!.size) {
            view = if (p3 == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.edit_schedule_list_item, p4, false)
            } else {
                p3
            }
            val listChildText = view.findViewById<TextView>(R.id.list_item_text)
            listChildText.text = text
        } else {
            view = if (p3 == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.edit_schedule_add_new_list_item, p4, false)
            } else {
                p3
            }
            val listChildText = view.findViewById<TextView>(R.id.edit_schedule_list_item_add_new_text)
            listChildText.text = text
        }
        return view
    }

    override fun getChildId(p0: Int, p1: Int) = p1.toLong()

    override fun getGroupCount() = headers.size

    override fun getChildType(groupPosition: Int, childPosition: Int) = if (childPosition == data[headers[groupPosition]]!!.size) {
        1
    } else {
        0
    }

    override fun getChildTypeCount() = 2
}