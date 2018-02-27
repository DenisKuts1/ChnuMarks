package com.chnumarks.fragments.menu.edit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.chnumarks.R
import com.chnumarks.fragments.FragmentManager

/**
 * Created by denak on 14.02.2018.
 */
class EditSubjectFragment : Fragment(){

    lateinit var fragmentManager: FragmentManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.edit_subject_fragment, container, false)
        val addNew = view.findViewById<LinearLayout>(R.id.edit_subjects_add_new)
        addNew.setOnClickListener {
            fragmentManager.attachCreateSubjectFragment()
        }
        return view
    }



}