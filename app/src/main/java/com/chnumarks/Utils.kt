package com.chnumarks

import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.View
import com.chnumarks.models.Group
import com.chnumarks.models.Subject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by denak on 10.02.2018.
 */
fun setLightStatusBar(view: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        view.systemUiVisibility = view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun clearLightStatusBar(view: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        view.systemUiVisibility = 0
    }
}

fun getSchedule(groups: HashMap<String, Group>): HashMap<Int, ArrayList<Subject>> {
    val result = HashMap<Int, ArrayList<Subject>>()
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().uid!!

    db.collection("schedule").orderBy("name").get().addOnSuccessListener {
        it.forEach {
            val subjects = ArrayList<Subject>()
            val day = (it["name"] as String).toInt()
            val week = it["week"] as Int - 1
            (it["subjects"] as List<String>).forEach { subject ->
                db.collection("subjects").document(subject).get().addOnSuccessListener {
                    if (it["user"] == userId){
                        val group = groups[it["group"] as String]
                        subjects += Subject(it.id, it["name"] as String, group!!)
                    }
                }
            }
            result[day + week * 5] = subjects
        }
    }
    return result
}
