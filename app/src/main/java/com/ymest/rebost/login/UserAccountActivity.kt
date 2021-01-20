package com.ymest.rebost.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.ymest.rebost.R
import kotlinx.android.synthetic.main.activity_user_account.*

class UserAccountActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var client: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_account)
        mAuth = FirebaseAuth.getInstance()
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(this, googleSignInOptions)
        btnLogOut.setOnClickListener {
            mAuth.signOut()
            client.signOut()
            this.finish()
        }

        if (mAuth.currentUser != null){
            tvnameUser.text = mAuth.currentUser?.displayName
            tvEmailUser.text = mAuth.currentUser?.email
            Picasso.get().load(mAuth.currentUser?.photoUrl).into(ivProfile)
        }

    }
}