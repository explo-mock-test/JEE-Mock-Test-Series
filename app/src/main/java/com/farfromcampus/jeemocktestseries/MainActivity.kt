package com.farfromcampus.jeemocktestseries

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.farfromcampus.jeemocktestseries.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        if (Firebase.auth.currentUser == null) {
            startActivity(Intent(this, getstartedActivity::class.java))
            finish()
        }

        val navController = this.findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.homeFragment,
                R.id.mockTestFragment,
                R.id.aboutFragment
            )
            .setOpenableLayout(drawerLayout).build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.navView, navController)
        binding.navView.menu.findItem(R.id.navLogout).setOnMenuItemClickListener { item ->
            logout()
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    private fun logout() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.My_id))
            .requestEmail()
            .build()
        // getting the value of gso inside the GoogleSigninClient
        val mGoogleSignInClient= GoogleSignIn.getClient(this,gso)
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                Firebase.auth.signOut()
                val intent = Intent(this, getstartedActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
    }
}