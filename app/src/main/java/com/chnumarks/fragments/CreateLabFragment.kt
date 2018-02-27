package com.chnumarks.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.chnumarks.R
import com.chnumarks.models.Lab

/**
 * Created by denak on 27.02.2018.
 */
class CreateLabFragment : Fragment() {

    lateinit var spinner: Spinner
    lateinit var description: EditText
    lateinit var maxPointsEdit: EditText
    var maxPoints: Int = 0
    lateinit var lab: Lab

    var shouldListen = true

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.create_subject_lab, container, false)
        spinner = view.findViewById(R.id.create_lab_spinner)
        description = view.findViewById(R.id.create_lab_description)
        maxPointsEdit = view.findViewById(R.id.create_lab_max_points)

        if (this::lab.isInitialized) {
            spinner.setSelection(lab.type.ordinal + 1)
            description.setText(lab.name, TextView.BufferType.EDITABLE)
            maxPoints = lab.maxMarks
            maxPointsEdit.setText(resources.getString(R.string.lab_max_points, maxPoints), TextView.BufferType.EDITABLE)
        }
        setListeners()


        return view
    }


    fun setListeners(){
        description.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (maxPoints != 0 && description.text.isNotEmpty() && spinner.selectedItemId != 0L) {
                    lab = Lab(description.text.toString(), maxPoints, Lab.Type.values()[spinner.selectedItemId.toInt() - 1])
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        maxPointsEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(!shouldListen) return
                if(p0 != null) {
                    shouldListen = false
                    maxPoints = p0.toString().toInt()
                    maxPointsEdit.setText(resources.getString(R.string.lab_max_points, maxPoints), TextView.BufferType.EDITABLE)
                    shouldListen = true
                    if (maxPoints != 0 && description.text.isNotEmpty() && spinner.selectedItemId != 0L) {
                        lab = Lab(description.text.toString(), maxPoints, Lab.Type.values()[spinner.selectedItemId.toInt() - 1])
                    }
                } else {
                    maxPoints = 0
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (maxPoints != 0 && description.text.isNotEmpty() && spinner.selectedItemId != 0L) {
                    lab = Lab(description.text.toString(), maxPoints, Lab.Type.values()[spinner.selectedItemId.toInt() - 1])
                }
            }
        }
    }

}