package com.chnumarks.fragments.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chnumarks.R

/**
 * Created by denak on 18.02.2018.
 */
class ScheduleFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.schedule_fragment, container, false)

        return view
    }
}