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
import com.chnumarks.setLightStatusBar
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by denak on 15.02.2018.
 */
class NavigationDrawerFragment : Fragment(), DrawerLayout.DrawerListener, NavigationView.OnNavigationItemSelectedListener  {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.title){
            resources.getString(R.string.navigation_menu_edit) -> {
                (activity as MainActivity).changeFragment(EditFragment())
            }
        }
        return true
    }

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.navigation_drawer_fragment, container, false)
        drawerLayout.addDrawerListener(this)
        navigationView = view.findViewById(R.id.navigation_view)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val offset = (25 * resources.displayMetrics.density + 0.5f).toInt()
            navigationView.getHeaderView(0).setPadding(0, offset,0,0)
        }
        navigationView.setNavigationItemSelectedListener(this)
        return view
    }

    fun setParameters(toolbar: Toolbar, drawerLayout: DrawerLayout) {
        this.toolbar = toolbar
        this.drawerLayout = drawerLayout
    }

    fun setUser(user: FirebaseUser?){
        if (user != null) {
            val profileName = navigationView.getHeaderView(0).findViewById<TextView>(R.id.navigation_header_profile_name)
            val profileEmail = navigationView.getHeaderView(0).findViewById<TextView>(R.id.navigation_header_profile_email)
            val profileImage = navigationView.getHeaderView(0).findViewById<CircleImageView>(R.id.navigation_header_profile_image)
            profileName.text = user.displayName
            profileEmail.text = user.email
            Picasso.with(activity).load(user.photoUrl).into(profileImage)
        }
    }

    override fun onDrawerStateChanged(newState: Int) {}

    override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {}

    override fun onDrawerClosed(drawerView: View?) {
        if (drawerView != null) {
            setLightStatusBar(toolbar, activity)
        }
    }

    override fun onDrawerOpened(drawerView: View?) {
        if (drawerView != null) {
            clearLightStatusBar(toolbar, activity)
        }
    }

}