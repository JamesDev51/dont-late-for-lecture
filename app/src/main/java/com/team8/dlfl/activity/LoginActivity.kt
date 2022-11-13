package com.team8.dlfl.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.team8.dlfl.fragment.LoginFragment
import com.team8.dlfl.R
import com.team8.dlfl.databinding.ActivityLoginBinding

const val EMAIL = "email"
const val PASSWORD = "password"
const val PASSWORD2 = "password2"
const val NAME = "name"
const val PHONE = "phone"

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

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

        binding= ActivityLoginBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_login)

        replaceLoginFragment()
    }

    companion object {

        @JvmStatic fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


}