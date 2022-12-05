package com.team8.dlfl.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.team8.dlfl.R
import com.team8.dlfl.databinding.ActivityAuthBinding
import com.team8.dlfl.view.main.MainActivity


class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    fun replaceFragment(fragment: Fragment){

        supportFragmentManager.beginTransaction().run {
            replace(binding.ffLogin.id,fragment)
            commit()
        }
    }

    private fun replaceLoginFragment(){

        val loginFragment = LoginFragment.newInstance()
        replaceFragment(loginFragment)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityAuthBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_auth)

        replaceLoginFragment()
    }

    fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    companion object {

        @JvmStatic fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


}