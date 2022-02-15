package com.farfromcampus.jeemocktestseries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class signinActivity : AppCompatActivity() {
    //Added from some website
//    lateinit var mGoogleSignInClient: GoogleSignInClient
//    val Req_Code:Int=123
//    val firebaseAuth= FirebaseAuth.getInstance()
    //end
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)


        //ADDED AGAIN
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        // getting the value of gso inside the GoogleSigninClient
//        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)
//        // initialize the firebaseAuth variable
//        firebaseAuth= FirebaseAuth.getInstance()
        //

    }
}