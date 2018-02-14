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
class ExpandableListAdapter : BaseExpandableListAdapter {
    private val context: Context
    private val headers: List<String>
    private val data: Map<String, List<String>>

    constructor(context: Context, headers: List<String>, data: Map<String, List<String>>) {
        this.context = context
        this.headers = headers
        this.data = data
    }

    override fun getGroup(p0: Int) = headers[p0]

    override fun isChildSelectable(p0: Int, p1: Int) = true

    override fun hasStableIds() = false

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        val title = getGroup(p0)
        val view = if (p2 == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.list_header, p3, false)
        } else {
            p2
        }
        val listHeaderText = view.findViewById<TextView>(R.id.list_header_text)
        listHeaderText.text = title
        return view
    }

    override fun getChildrenCount(p0: Int) = data[headers[p0]]!!.size

    override fun getChild(p0: Int, p1: Int) = data[headers[p0]]!![p1]

    override fun getGroupId(p0: Int) = p0.toLong()

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        val text = getChild(p0, p1)
        val view = if (p3 == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.list_item, p4, false)
        } else {
            p3
        }
        val listChildText = view.findViewById<TextView>(R.id.list_item_text)
        listChildText.text = text
        return view
    }

    override fun getChildId(p0: Int, p1: Int) = p1.toLong()

    override fun getGroupCount() = headers.size
}