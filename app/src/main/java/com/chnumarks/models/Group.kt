package com.chnumarks.models

/**
 * Created by denak on 22.02.2018.
 */
class Group(val id: String, val name: String) {
    val students = ArrayList<Student>()
}