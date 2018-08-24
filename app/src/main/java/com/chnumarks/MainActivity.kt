package com.chnumarks

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.WindowManager

import com.chnumarks.fragments.FragmentManager
import com.chnumarks.fragments.menu.EditFragment
import com.chnumarks.fragments.menu.ScheduleFragment
import com.chnumarks.listeners.NavigationDrawerListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

/**
 * Created by denak on 15.02.2018.
 */

class MainActivity : AppCompatActivity() {

    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    val drawerLayout: DrawerLayout by lazy { findViewById<DrawerLayout>(R.id.main_drawer_layout) }
    private val manager by lazy { FragmentManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        println(111111)
        val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build()
        println(222222)
        FirebaseFirestore.getInstance().firestoreSettings = settings
        manager

    }


    fun authenticate() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build()
        val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
        startActivityForResult(signInIntent, 1)
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            updateUI(auth.currentUser)
        }

    }

    private fun updateUI(user: FirebaseUser?) {
        manager.setUpUserInfo(user!!)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java) as GoogleSignInAccount
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { _ ->
                    val newUser = auth.currentUser
                    manager.initialSetUp(newUser!!)
                    updateUI(newUser)
                }
            } catch (e: Throwable) {

            }

        }
    }

    override fun onBackPressed() {
        if (manager.onBackPressed()) super.onBackPressed()
    }

    /*fun changeFragment(title: CharSequence) {
        var fragment: Fragment? = null
        if (title == resources.getString(R.string.navigation_menu_edit)) {
            fragment = EditFragment()
            fragment.setUpTabLayout(tabLayout)
        } else if (title == resources.getString(R.string.navigation_menu_schedule)) {
            fragment = ScheduleFragment()
        }
        supportFragmentManager.beginTransaction().remove(currentFragment).add(R.id.main_content, fragment).commit()
        currentFragment = fragment
    }*/
}
