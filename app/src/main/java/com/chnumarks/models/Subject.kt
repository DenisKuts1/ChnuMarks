package com.chnumarks.models

/**
 * Created by denak on 22.02.2018.
 */
class Subject(val id: String, var name: String, var group: Group) {
    val labs = ArrayList<Lab>()
    val students = ArrayList<Boolean>()

}