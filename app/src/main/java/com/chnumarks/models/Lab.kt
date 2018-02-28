package com.chnumarks.models

/**
 * Created by denak on 22.02.2018.
 */
class Lab(var name: String, var maxMarks: Int, var type: Type) {
    var id: Int = -1

    enum class Type(val type: String){
        LAB("Lab"),
        TEST("Test")
    }
}