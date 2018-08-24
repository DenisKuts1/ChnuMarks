package com.chnumarks.fragments

import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chnumarks.MainActivity
import com.chnumarks.R
import com.chnumarks.clearLightStatusBar
import com.chnumarks.listeners.NavigationDrawerListener
import com.chnumarks.setLightStatusBar
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by denak on 15.02.2018.
 */
class NavigationDrawerFragment : Fragment() {

    private lateinit var navigationView: NavigationView
    private lateinit var user: FirebaseUser
    private lateinit var listener: NavigationView.OnNavigationItemSelectedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.navigation_drawer_fragment, container, false)
        navigationView = view.findViewById(R.id.navigation_view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val offset = (25 * resources.displayMetrics.density + 0.5f).toInt()
            navigationView.getHeaderView(0).setPadding(0, offset, 0, 0)
        }
        navigationView.setNavigationItemSelectedListener(listener)
        drawUser()
        navigationView.menu.getItem(0).isChecked = true
        return view
    }

    fun setUser(user: FirebaseUser) {
        this.user = user
    }

    fun setNavigationListener(listener: NavigationView.OnNavigationItemSelectedListener){
        this.listener = listener
    }

    fun drawUser() {
        val profileName = navigationView.getHeaderView(0).findViewById<TextView>(R.id.navigation_header_profile_name)
        val profileEmail = navigationView.getHeaderView(0).findViewById<TextView>(R.id.navigation_header_profile_email)
        val profileImage = navigationView.getHeaderView(0).findViewById<CircleImageView>(R.id.navigation_header_profile_image)

        profileName.text = user.displayName
        profileEmail.text = user.email
        Picasso.with(activity).load(user.photoUrl).into(profileImage)

    }

}