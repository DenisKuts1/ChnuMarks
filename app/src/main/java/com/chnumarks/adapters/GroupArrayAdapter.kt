package com.chnumarks.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.chnumarks.MainActivity
import com.chnumarks.R
import com.chnumarks.fragments.dialogs.SelectGroupDialogFragment
import com.chnumarks.models.Group

/**
 * Created by denak on 27.02.2018.
 */
class GroupArrayAdapter(val context: Context, val list: ArrayList<Group>) : BaseAdapter(), AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    val dialog = SelectGroupDialogFragment()

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p2 == 0) return
        val names = ArrayList<CharSequence>()
        list[p2 - 1].students.values.sortedBy { it.name }.forEach { names += it.name }
        dialog.list = names
        dialog.title = if (isEnabled(p2)) {
            getItem(p2 - 1)!!.name
        } else {
            context.resources.getString(R.string.create_subject_group_hint)
        }
        dialog.show((context as MainActivity).supportFragmentManager, "")

    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val text = if (isEnabled(p0)) {
            getItem(p0 - 1)!!.name
        } else {
            context.resources.getString(R.string.create_subject_group_hint)
        }
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

    override fun getItem(p0: Int) = list[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getCount() = list.size + 1

    override fun isEnabled(position: Int) = position != 0
}