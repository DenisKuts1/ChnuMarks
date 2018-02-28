package com.chnumarks.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.chnumarks.R

/**
 * Created by denak on 27.02.2018.
 */
class LabSpinnerAdapter(val context: Context, arrayId: Int): BaseAdapter() {

    val array: List<String>

    init {
        array = context.resources.getStringArray(R.array.lab_types).toList()
    }
    override fun getItem(p0: Int) = array[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getCount() = array.size

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val text = getItem(p0)
        val view = if (p1 == null) {
            val id = if (isEnabled(p0)) {
                R.layout.create_group_spinner_item_enabled
            } else {
                R.layout.create_group_spinner_item_disabled
            }
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(id, p2, false)
        } else {
            p1
        }
        val textView = view.findViewById<TextView>(R.id.create_subject_spinner_text)
        textView.text = text
        return view
    }

    override fun isEnabled(position: Int) = position != 0
}