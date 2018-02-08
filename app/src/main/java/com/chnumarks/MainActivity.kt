package com.chnumarks

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {

    val textView by lazy { findViewById<TextView>(R.id.textView) }
    val toolbar by lazy { findViewById<Toolbar>(R.id.main_toolbar) }
    val profileImage by lazy { findViewById<CircleImageView>(R.id.navigation_header_profile_image) }
    val profileName by lazy { findViewById<TextView>(R.id.navigation_header_profile_name) }
    val profileEmail by lazy { findViewById<TextView>(R.id.navigation_header_profile_email) }

    /*val mAuth by lazy {
        FirebaseAuth.getInstance()
    }*/

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
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build()
        val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
        startActivityForResult(signInIntent, 1)
        /*val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {

        } else {
            textView.text = user.email
        }*/

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                profileName.text = account.displayName
                profileEmail.text = account.email
                Picasso.with(this).load(account.photoUrl).into(profileImage)

                /*val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                s += account.displayName + " " + account.email
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(this, { t ->
                            run {
                                if (t.isSuccessful) {
                                    val user = FirebaseAuth.getInstance().currentUser
                                    s += user!!.email ?: ""
                                } else {
                                    s += "Uh oh..."
                                }
                                textView.text = t.exception!!.message
                            }
                        })*/
            } catch (e: ApiException) {
                //s += e.localizedMessage
                textView.text = "Ops..."
            }
            //textView.text = s
        }
    }
}
