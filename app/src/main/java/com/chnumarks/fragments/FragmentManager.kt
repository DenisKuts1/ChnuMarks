package com.chnumarks.fragments

import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.chnumarks.MainActivity
import com.chnumarks.R
import com.chnumarks.fragments.menu.*
import com.chnumarks.fragments.toolbars.CreateSubjectToolbar
import com.chnumarks.fragments.toolbars.EditToolbarFragment
import com.chnumarks.fragments.toolbars.MainToolbarFragment
import com.chnumarks.models.Group
import com.chnumarks.models.Student
import com.chnumarks.models.Subject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by denak on 20.02.2018.
 */
class FragmentManager(val activity: MainActivity) : NavigationView.OnNavigationItemSelectedListener {

    val editFragment by lazy { EditFragment().also { it.manager = this } }
    val scheduleFragment by lazy { ScheduleFragment() }
    val reportFragment by lazy { ReportFragment() }
    val settingsFragment by lazy { SettingsFragment() }
    val welcomeFragment by lazy { WelcomeFragment() }
    val createSubjectFragment by lazy { CreateSubjectFragment().also { it.manager = this } }

    val navigationFragment by lazy { NavigationDrawerFragment() }
    val mainToolbarFragment by lazy { MainToolbarFragment().also { it.drawerLayout = activity.drawerLayout } }
    val editToolbarFragment by lazy { EditToolbarFragment().also { it.drawerLayout = activity.drawerLayout } }
    val createSubjectToolbar by lazy {
        CreateSubjectToolbar().also {
            it.drawerLayout = activity.drawerLayout
            it.createSubjectFragment = createSubjectFragment
            it.manager = this
        }
    }

    val auth = FirebaseAuth.getInstance()

    var currentFragment: Fragment? = null
    var currentNavigationView: Fragment? = null
    var currentToolbarFragment: Fragment? = null

    val groups = HashMap<String, Group>()
    val schedule = HashMap<Int, ArrayList<Subject>>()

    init {
        FirebaseFirestore.getInstance().collection("groups").get().addOnSuccessListener {
            it.forEach {
                val group = Group(it.id, it["name"] as String)
                it.reference.collection("students").orderBy("name").get().addOnSuccessListener {
                    it.forEach {
                        val student = Student(it["name"] as String)
                        group.students[it.id] = student
                    }
                }
                groups[it.id] = group
            }
            val userId = FirebaseAuth.getInstance().uid!!

            FirebaseFirestore.getInstance().collection("schedule").orderBy("name").get().addOnSuccessListener {
                it.forEach {
                    val subjects = ArrayList<Subject>()
                    val day = (it["name"] as String).toInt()
                    val week = (it["week"] as Long).toInt() - 1
                    if (it["subjects"] != null) {
                        (it["subjects"] as List<String>).forEach {
                            FirebaseFirestore.getInstance().collection("subjects").document(it).get().addOnSuccessListener {
                                if (it["user"] == userId) {
                                    val group = groups[it["group"] as String]
                                    subjects += Subject(it.id, it["name"] as String, group!!)
                                }
                            }
                        }
                    }
                    schedule[day + week * 5] = subjects
                }
            }
        }

        if (auth.currentUser == null) {
            attachFragment(Fragments.WELCOME)
        } else {
            initialSetUp(auth.currentUser!!)
        }
    }

    fun onBackPressed(): Boolean {
        if (currentFragment == createSubjectFragment) {
            attachEditToolbarFragment()
            attachFragment(Fragments.EDIT)
            return false
        }
        return true
    }


    fun initialSetUp(user: FirebaseUser) {
        attachMainToolbarFragment()
        navigationFragment.setUser(user)
        navigationFragment.setNavigationListener(this)
        attachFragment(Fragments.NAVIGATION)
        attachFragment(Fragments.SCHEDULE)

    }

    fun setUpUserInfo(user: FirebaseUser) {
        navigationFragment.setUser(user)
    }

    fun attachMainToolbarFragment() {
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (currentToolbarFragment != null) {
            transaction.detach(currentToolbarFragment!!)
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
            transaction.detach(currentToolbarFragment!!)
        }
        if (editToolbarFragment.isDetached) {
            transaction.attach(editToolbarFragment)
        } else {
            transaction.add(R.id.main_content, editToolbarFragment)
        }
        transaction.commit()
        currentToolbarFragment = editToolbarFragment
    }

    fun attachCreateSubjecrToolbarFragment() {
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (currentToolbarFragment != null) {
            transaction.detach(currentToolbarFragment!!)
        }
        if (createSubjectToolbar.isDetached) {
            transaction.attach(createSubjectToolbar)
        } else {
            transaction.add(R.id.main_content, createSubjectToolbar)
        }
        transaction.commit()
        currentToolbarFragment = createSubjectToolbar
    }

    fun attachFragment(fragment: Fragments) {
        val transaction = activity.supportFragmentManager.beginTransaction()

        val newFragment: Fragment
        when (fragment) {
            Fragments.WELCOME -> {
                newFragment = welcomeFragment
                if (currentFragment != null) {
                    transaction.detach(currentToolbarFragment!!)
                            .detach(navigationFragment)
                }
            }
            Fragments.EDIT -> {
                newFragment = editFragment
            }
            Fragments.NAVIGATION -> {
                newFragment = navigationFragment
                if (currentNavigationView != null) {
                    transaction.detach(currentNavigationView!!)
                }
                if (newFragment.isDetached) {
                    transaction.attach(newFragment)
                } else {
                    transaction.add(R.id.main_drawer_layout, newFragment)
                }
                currentNavigationView = newFragment
            }
            Fragments.REPORT -> {
                newFragment = reportFragment
            }
            Fragments.SCHEDULE -> {
                newFragment = scheduleFragment
            }
            Fragments.SETTINGS -> {
                newFragment = settingsFragment
            }
            Fragments.SUBJECT -> {
                newFragment = createSubjectFragment
                attachCreateSubjecrToolbarFragment()
            }
        }
        if(fragment != Fragments.NAVIGATION) {
            if (currentFragment != null) {
                transaction.detach(currentFragment!!)
            }
            if (newFragment.isDetached) {
                transaction.attach(newFragment)
            } else {
                transaction.add(R.id.main_content, newFragment)
            }
            currentFragment = newFragment
        }
        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_menu_schedule -> {
                if (currentFragment != scheduleFragment) {
                    if (currentToolbarFragment != mainToolbarFragment) {
                        attachMainToolbarFragment()
                    }
                    attachFragment(Fragments.SCHEDULE)
                }
            }
            R.id.navigation_menu_edit -> {
                if (currentFragment != editFragment) {
                    editToolbarFragment.setEditFragment(editFragment)
                    attachEditToolbarFragment()
                    attachFragment(Fragments.EDIT)
                }
            }
            R.id.navigation_menu_report -> {
                if (currentFragment != reportFragment) {
                    if (currentToolbarFragment != mainToolbarFragment) {
                        attachMainToolbarFragment()
                    }
                    attachFragment(Fragments.REPORT)
                }
            }
            R.id.navigation_menu_settings -> {
                if (currentFragment != settingsFragment) {
                    if (currentToolbarFragment != mainToolbarFragment) {
                        attachMainToolbarFragment()
                    }
                    attachFragment(Fragments.SETTINGS)
                }
            }
            R.id.navigation_menu_exit -> {
                auth.signOut()
                attachFragment(Fragments.WELCOME)
            }
        }
        activity.drawerLayout.closeDrawers()
        return true
    }

    enum class Fragments {
        SUBJECT, SETTINGS, REPORT, EDIT, NAVIGATION, SCHEDULE, WELCOME
    }
}

/*    fun attachWelcomeFragment() {
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
    }*/

/*fun attachScheduleFragment() {
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
}*/

/*fun attachNavigationFragment() {
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
}*/

/*fun attachEditFragment() {
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
}*/

/*fun attachReportFragment() {
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
}*/

/*fun attachSettingsFragment() {
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
}*/

/*fun attachCreateSubjectFragment() {
    attachCreateSubjecrToolbarFragment()
    val transaction = activity.supportFragmentManager.beginTransaction()
    if (currentFragment != null) {
        transaction.detach(currentFragment)
    }
    if (createSubjectFragment.isDetached) {
        transaction.attach(createSubjectFragment)
    } else {
        transaction.add(R.id.main_content, createSubjectFragment)
    }
    transaction.commit()
    currentFragment = createSubjectFragment
}*/
