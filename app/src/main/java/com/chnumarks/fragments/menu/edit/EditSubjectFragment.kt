package com.chnumarks.fragments.menu.edit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import com.chnumarks.R
import com.chnumarks.adapters.SubjectListAdapter
import com.chnumarks.fragments.FragmentManager
import com.chnumarks.models.Group
import com.chnumarks.models.Student
import com.chnumarks.models.Subject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by denak on 14.02.2018.
 */
class EditSubjectFragment : Fragment() {

    lateinit var manager: FragmentManager
    val subjectsMap = HashMap<Subject, String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.edit_subject_fragment, container, false)
        val addNew = view.findViewById<LinearLayout>(R.id.edit_subjects_add_new)
        addNew.setOnClickListener {
            manager.attachFragment(FragmentManager.Fragments.SUBJECT)
        }
        val list = view.findViewById<ListView>(R.id.edit_subject_list)
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().uid!!

        db.collection("subjects").whereEqualTo("user", userId).get().addOnSuccessListener {
            val subjects = ArrayList<Subject>()
            it.forEach {
                val group = manager.groups[it["group"] as String]
                val subject = Subject(it.id, it["name"] as String, group!!)
                subjects += subject
                subjectsMap[subject] = it.id
            }
            val adapter = SubjectListAdapter(context!!, subjects)
            adapter.fragment = this
            list.adapter = adapter
        }
        return view
    }

    fun deleteSubject(subject: Subject){
        val id = subjectsMap[subject]!!
        val db = FirebaseFirestore.getInstance()
        db.collection("subjects").document(id).collection("labs").get().addOnSuccessListener {
            it.forEach {
                it.reference.delete()
            }
        }
        db.collection("subjects").document(id).delete()
    }


}