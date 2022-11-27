package com.team8.dlfl.view.mainview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.team8.dlfl.databinding.ActivityMainBinding
import com.team8.dlfl.R
import com.team8.dlfl.view.authview.AuthActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    fun startAuthActivity(){
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.entryFragment, R.id.twitterWebFragment, R.id.markFragment,R.id.myPageFragment)  //  top level 메뉴들
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNav.setupWithNavController(navController)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController

        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}