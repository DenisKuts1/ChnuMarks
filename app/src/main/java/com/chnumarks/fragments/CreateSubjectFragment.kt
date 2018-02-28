package com.chnumarks.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import com.chnumarks.MainActivity
import com.chnumarks.R
import com.chnumarks.adapters.GroupArrayAdapter
import com.chnumarks.fragments.toolbars.CreateSubjectToolbar
import com.chnumarks.models.Group
import com.chnumarks.models.Lab
import com.chnumarks.models.Student
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by denak on 21.02.2018.
 */
class CreateSubjectFragment : Fragment() {
    lateinit var spinner: Spinner
    lateinit var name: EditText
    lateinit var adapter: GroupArrayAdapter
    val fragments = ArrayList<CreateLabFragment>()
    val labs = ArrayList<Lab>()
    lateinit var manager: FragmentManager
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.create_subject_fragment, container, false)
        spinner = view.findViewById(R.id.create_subject_group_spinner)
        name = view.findViewById(R.id.create_subject_name_edit)
        getGroups()
        addLab()
        return view
    }

    fun getGroups() {
        val db = FirebaseFirestore.getInstance()
        db.collection("groups").orderBy("name").get().addOnSuccessListener {
            val groups = ArrayList<Group>(manager.groups.values)
            groups.sortBy { it.name }
            adapter = GroupArrayAdapter(activity, groups)
            spinner.adapter = adapter
            spinner.setSelection(0)
            spinner.onItemSelectedListener = adapter
        }
    }

    fun addLab() {
        if (fragments.size != 0) {
            fragments.last().isLast = false
        }
        val createLabFragment = CreateLabFragment()
        createLabFragment.isLast = true
        createLabFragment.createSubjectFragment = this
        fragmentManager.beginTransaction().add(R.id.create_lab_main_layout, createLabFragment).commit()
        fragments += createLabFragment


    }

    fun setLab(lab: Lab) {
        labs += lab
        addLab()
    }

    fun draw() {
        labs.forEach { println(it.name) }
        println()
    }

}