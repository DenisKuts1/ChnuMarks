package com.chnumarks

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {

    val textView by lazy { findViewById<TextView>(R.id.textView) }
    val toolbar by lazy { findViewById<Toolbar>(R.id.main_toolbar) }
    val auth by lazy { FirebaseAuth.getInstance() }
    val database by lazy { FirebaseDatabase.getInstance()}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.toolbar_menu, menu)
        //textView.text = "Hello"
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user == null) {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.web_client_id))
                    .requestEmail()
                    .build()
            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            startActivityForResult(signInIntent, 1)
        } else {
            updateUI(user)
        }

    }

    fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val navigationView = findViewById<NavigationView>(R.id.navigation_view)

            val profileName = navigationView.getHeaderView(0).findViewById<TextView>(R.id.navigation_header_profile_name)
            val profileEmail = navigationView.getHeaderView(0).findViewById<TextView>(R.id.navigation_header_profile_email)
            val profileImage = navigationView.getHeaderView(0).findViewById<CircleImageView>(R.id.navigation_header_profile_image)
            profileName.text = user.displayName
            profileEmail.text = user.email
            Picasso.with(this).load(user.photoUrl).into(profileImage)
        } else {
            textView.text = "Ops..."
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    val newUser = auth.currentUser
                    updateUI(newUser)
                }

            } catch (e: ApiException) {

            }
        }
    }
}
