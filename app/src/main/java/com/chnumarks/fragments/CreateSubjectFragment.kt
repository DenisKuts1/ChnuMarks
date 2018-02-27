package com.chnumarks.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import com.chnumarks.R
import com.chnumarks.adapters.GroupArrayAdapter
import com.chnumarks.models.Group
import com.chnumarks.models.Student
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by denak on 21.02.2018.
 */
class CreateSubjectFragment : Fragment() {
    lateinit var spinner: Spinner
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.create_subject_fragment, container, false)
        spinner = view.findViewById(R.id.create_subject_group_spinner)
        getGroups()
        addLab()
        return view
    }

    fun getGroups() {
        val db = FirebaseFirestore.getInstance()
        db.collection("groups").orderBy("name").get().addOnSuccessListener {
            val groups = ArrayList<Group>()
            it.forEach {
                val group = Group(it["name"] as String)
                it.reference.collection("students").orderBy("name").get().addOnSuccessListener {
                    it.forEach { group.students.add(Student(it["name"] as String)) }
                }
                groups += group
            }
            val adapter = GroupArrayAdapter(activity, groups)
            spinner.adapter = adapter
            spinner.setSelection(0)
            spinner.onItemSelectedListener = adapter
        }
    }

    fun addLab(){
        val createLabFragment = CreateLabFragment()
        fragmentManager.beginTransaction().add(R.id.create_lab_main_layout, createLabFragment).commit()
    }


}