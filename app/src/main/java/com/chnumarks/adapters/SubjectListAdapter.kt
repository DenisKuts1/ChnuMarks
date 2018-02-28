package com.chnumarks.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.chnumarks.R
import com.chnumarks.fragments.menu.edit.EditSubjectFragment
import com.chnumarks.models.Subject

/**
 * Created by denak on 28.02.2018.
 */
class SubjectListAdapter(val context: Context, val subjects: ArrayList<Subject>) : BaseAdapter() {
    lateinit var fragment: EditSubjectFragment
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = if (p1 == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.edit_subject_list_item, p2, false)
        } else {
            p1
        }
        val textView = view.findViewById<TextView>(R.id.edit_subject_text)
        val image = view.findViewById<ImageView>(R.id.edit_subject_image)
        val subject = getItem(p0)

        textView.text = context.resources.getString(R.string.edit_subject_list_item, subject.name, subject.group.name)

        image.isClickable = true
        image.setOnClickListener({ _ ->
            run {
                subjects.remove(subject)
                this.notifyDataSetChanged()
                fragment.deleteSubject(subject)
            }
        })


        return view
    }

    override fun getItem(p0: Int) = subjects[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getCount() = subjects.size
}