package com.chnumarks.fragments.toolbars

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.chnumarks.MainActivity
import com.chnumarks.R
import com.chnumarks.fragments.CreateSubjectFragment
import com.chnumarks.listeners.NavigationDrawerListener
import com.chnumarks.models.Group
import com.chnumarks.setLightStatusBar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by denak on 27.02.2018.
 */
class CreateSubjectToolbar : Fragment() {
    lateinit var toolbar: Toolbar
    lateinit var createSubjectFragment: CreateSubjectFragment
    lateinit var drawerLayout: DrawerLayout

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.create_subject_toolbar, container, false)

        // Setting up toolbar
        toolbar = view.findViewById(R.id.create_subject_toolbar)
        setLightStatusBar(toolbar, activity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val offset = (25 * resources.displayMetrics.density + 0.5f).toInt()
            toolbar.setPadding(0, offset, 0, 0)
        }

        drawerLayout.addDrawerListener(NavigationDrawerListener(toolbar, activity as MainActivity))

        // Setting toolbar as app's actionbar
        val mainActivity = activity as MainActivity
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)

        val check = view.findViewById<ImageView>(R.id.create_subject_check)
        check.isClickable = true
        check.setOnClickListener {
            val subject = HashMap<String, Any>()
            subject["name"] = createSubjectFragment.name.text.toString()
            subject["group"] = (createSubjectFragment.spinner.selectedItem as Group).id
            subject["students"] = createSubjectFragment.adapter.dialog.checked.toList()
            val db = FirebaseFirestore.getInstance()
            db.collection("subjects").add(subject).addOnSuccessListener {
                val labs = ArrayList<Map<String, Any>>()
                createSubjectFragment.labs.forEach {
                    val map = HashMap<String, Any>()
                    map["maxMark"] = it.maxMarks
                    map["name"] = it.name
                    map["type"] = it.type.name
                    labs += map
                }
                labs.forEach { lab -> it.collection("labs").add(lab) }
            }
            println(1231231313123)

        }

        return view
    }


}