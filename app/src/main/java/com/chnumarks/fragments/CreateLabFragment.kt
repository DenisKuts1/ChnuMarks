package com.chnumarks.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.text.method.KeyListener
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.chnumarks.R
import com.chnumarks.adapters.LabSpinnerAdapter
import com.chnumarks.models.Lab
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by denak on 27.02.2018.
 */
class CreateLabFragment : Fragment() {

    lateinit var spinner: Spinner
    lateinit var description: EditText
    lateinit var maxPointsEdit: EditText
    lateinit var submit: Button
    lateinit var lab: Lab
    lateinit var createSubjectFragment: CreateSubjectFragment

    var isLast = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.create_subject_lab, container, false)
        spinner = view.findViewById(R.id.create_lab_spinner)
        description = view.findViewById(R.id.create_lab_description)
        maxPointsEdit = view.findViewById(R.id.create_lab_max_points)
        submit = view.findViewById(R.id.create_lab_submit)
        if (this::lab.isInitialized) {
            spinner.setSelection(lab.type.ordinal + 1)
            description.setText(lab.name, TextView.BufferType.EDITABLE)
            maxPointsEdit.setText(lab.maxMarks.toString(), TextView.BufferType.EDITABLE)
        }
        spinner.adapter = LabSpinnerAdapter(context!!, R.array.lab_types)

        submit.setOnClickListener {
            if (description.text.isNotEmpty() && maxPointsEdit.text.isNotEmpty() && spinner.selectedItemId != 0L) {
                val type = if (spinner.selectedItemId == 1L) {
                    Lab.Type.LAB
                } else {
                    Lab.Type.TEST
                }
                if(this::lab.isInitialized){
                    lab.name = description.text.toString()
                    lab.maxMarks = maxPointsEdit.text.toString().toInt()
                    lab.type = type
                } else {
                    lab = Lab(description.text.toString(), maxPointsEdit.text.toString().toInt(), type)
                    createSubjectFragment.setLab(lab)
                    createSubjectFragment.draw()
                }
            }
        }

        return view
    }


}