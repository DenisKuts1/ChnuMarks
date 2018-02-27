package com.chnumarks.fragments

import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.chnumarks.MainActivity
import com.chnumarks.R
import com.chnumarks.fragments.menu.*
import com.chnumarks.fragments.toolbars.EditToolbarFragment
import com.chnumarks.fragments.toolbars.MainToolbarFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Created by denak on 20.02.2018.
 */
class FragmentManager(val activity: MainActivity) : NavigationView.OnNavigationItemSelectedListener {

    val editFragment = EditFragment()
    val scheduleFragment = ScheduleFragment()
    val reportFragment = ReportFragment()
    val settingsFragment = SettingsFragment()
    val welcomeFragment = WelcomeFragment()
    val createSubjectFragment = CreateSubjectFragment()

    val navigationFragment = NavigationDrawerFragment()
    val mainToolbarFragment = MainToolbarFragment()
    val editToolbarFragment = EditToolbarFragment()

    val auth = FirebaseAuth.getInstance()

    var currentFragment: Fragment? = null
    var currentNavigationView: Fragment? = null
    var currentToolbarFragment: Fragment? = null


    init {
        editToolbarFragment.drawerLayout = activity.drawerLayout
        mainToolbarFragment.drawerLayout = activity.drawerLayout
        editFragment.manager = this
        if (auth.currentUser == null) {
            attachWelcomeFragment()
        } else {
            initialSetUp(auth.currentUser!!)
        }
    }

    fun initialSetUp(user: FirebaseUser) {
        attachMainToolbarFragment()
        navigationFragment.setUser(user)
        navigationFragment.setNavigationListener(this)
        attachNavigationFragment()
        attachScheduleFragment()
    }

    fun setUpUserInfo(user: FirebaseUser) {
        navigationFragment.setUser(user)
    }

    fun attachMainToolbarFragment() {
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (currentToolbarFragment != null) {
            transaction.detach(currentToolbarFragment)
        }
        if (mainToolbarFragment.isDetached) {
            transaction.attach(mainToolbarFragment)
        } else {
            transaction.add(R.id.main_content, mainToolbarFragment)
        }
        transaction.commit()
        currentToolbarFragment = mainToolbarFragment
    }

    fun attachEditToolbarFragment() {
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (currentToolbarFragment != null) {
            transaction.detach(currentToolbarFragment)
        }
        if (editToolbarFragment.isDetached) {
            transaction.attach(editToolbarFragment)
        } else {
            transaction.add(R.id.main_content, editToolbarFragment)
        }
        transaction.commit()
        currentToolbarFragment = editToolbarFragment
    }

    fun attachWelcomeFragment() {
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (currentFragment != null) {
            transaction.detach(currentFragment)
                    .detach(currentToolbarFragment)
                    .detach(navigationFragment)
        }
        if (welcomeFragment.isDetached) {
            transaction.attach(welcomeFragment)
        } else {
            transaction.add(R.id.main_content, welcomeFragment)
        }
        transaction.commit()
        currentFragment = welcomeFragment
    }

    fun attachScheduleFragment() {
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (currentFragment != null) {
            transaction.detach(currentFragment)
        }
        if (scheduleFragment.isDetached) {
            transaction.attach(scheduleFragment)
        } else {
            transaction.add(R.id.main_content, scheduleFragment)
        }
        transaction.commit()
        currentFragment = scheduleFragment
    }

    fun attachNavigationFragment() {
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (currentNavigationView != null) {
            transaction.detach(currentNavigationView)
        }
        if (navigationFragment.isDetached) {
            transaction.attach(navigationFragment)
        } else {
            transaction.add(R.id.main_drawer_layout, navigationFragment)
        }
        transaction.commit()
        currentNavigationView = navigationFragment
    }

    fun attachEditFragment() {
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (currentFragment != null) {
            transaction.detach(currentFragment)
        }
        if (editFragment.isDetached) {
            transaction.attach(editFragment)
        } else {
            transaction.add(R.id.main_content, editFragment)
        }
        transaction.commit()
        currentFragment = editFragment
    }

    fun attachReportFragment() {
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (currentFragment != null) {
            transaction.detach(currentFragment)
        }
        if (reportFragment.isDetached) {
            transaction.attach(reportFragment)
        } else {
            transaction.add(R.id.main_content, reportFragment)
        }
        transaction.commit()
        currentFragment = reportFragment
    }

    fun attachSettingsFragment() {
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (currentFragment != null) {
            transaction.detach(currentFragment)
        }
        if (settingsFragment.isDetached) {
            transaction.attach(settingsFragment)
        } else {
            transaction.add(R.id.main_content, settingsFragment)
        }
        transaction.commit()
        currentFragment = settingsFragment
    }

    fun attachCreateSubjectFragment(){
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (currentFragment != null) {
            transaction.detach(currentFragment)
        }
        if (settingsFragment.isDetached) {
            transaction.attach(createSubjectFragment)
        } else {
            transaction.add(R.id.main_content, createSubjectFragment)
        }
        transaction.commit()
        currentFragment = settingsFragment
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_menu_schedule -> {
                if (currentFragment != scheduleFragment) {
                    if(currentToolbarFragment != mainToolbarFragment){
                        attachMainToolbarFragment()
                    }
                    attachScheduleFragment()
                }
            }
            R.id.navigation_menu_edit -> {
                if (currentFragment != editFragment) {
                    editToolbarFragment.setEditFragment(editFragment)
                    attachEditToolbarFragment()
                    attachEditFragment()
                }
            }
            R.id.navigation_menu_report -> {
                if (currentFragment != reportFragment) {
                    if(currentToolbarFragment != mainToolbarFragment){
                        attachMainToolbarFragment()
                    }
                    attachReportFragment()
                }
            }
            R.id.navigation_menu_settings -> {
                if (currentFragment != settingsFragment) {
                    if(currentToolbarFragment != mainToolbarFragment){
                        attachMainToolbarFragment()
                    }
                    attachSettingsFragment()
                }
            }
            R.id.navigation_menu_exit -> {
                auth.signOut()
                attachWelcomeFragment()
            }
        }
        activity.drawerLayout.closeDrawers()
        return true
    }
}