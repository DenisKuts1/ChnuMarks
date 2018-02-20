package com.chnumarks.fragments.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.chnumarks.MainActivity
import com.chnumarks.R

/**
 * Created by denak on 20.02.2018.
 */
class WelcomeFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.welcome_fragment, container, false)
        retainInstance = true
        val button = view.findViewById<Button>(R.id.welcome_enter_button)
        button.setOnClickListener {
            (activity as MainActivity).authenticate()
        }
        return view
    }
}