package com.farfromcampus.jeemocktestseries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farfromcampus.jeemocktestseries.daos.Mocktestdao
import com.farfromcampus.jeemocktestseries.models.Mocktest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.farfromcampus.jeemocktestseries.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (GoogleSignIn.getLastSignedInAccount(this) == null) {
            startActivity(Intent(this, getstartedActivity::class.java))
            finish()
        }

        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }


//    override fun onStart() {
//        super.onStart()
//        val mock_id = "testingfirstmocktest"
//        val intent =Intent(this,ockreviewActivity::class.java)
//        binding.button.setOnClickListener {
//            intent.putExtra("mock_id",mock_id)
//            startActivity(intent)
//        }
//    }
}