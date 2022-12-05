package com.team8.dlfl.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.team8.dlfl.R
import com.team8.dlfl.databinding.ActivityMainBinding
import com.team8.dlfl.service.MyFirebaseMessagingService
import com.team8.dlfl.view.auth.AuthActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    fun changeAuthActivity(){
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

        // FCM 설정, Token 값 가져오기
        MyFirebaseMessagingService().getFirebaseToken()

        // DynamicLink 수신확인
        initDynamicLink()
    }

    // DynamicLink 동적 링크
    private fun initDynamicLink() {
        val dynamicLinkData = intent.extras
        if (dynamicLinkData != null) {
            var dataStr = "DynamicLink 수신받은 값\n"
            for (key in dynamicLinkData.keySet()) {
                dataStr += "key: $key / value: ${dynamicLinkData.getString(key)}\n"
            }

            //binding.tvToken.text = dataStr // 테스트용으로 화면에 보여주려고 넣은듯
            // 이걸로 즐찾에 있는 목록 표시할수 있을 듯
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController

        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}