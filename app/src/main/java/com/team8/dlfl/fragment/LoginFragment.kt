package com.team8.dlfl.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.team8.dlfl.activity.LoginActivity
import com.team8.dlfl.activity.MainActivity
import com.team8.dlfl.databinding.FragmentLoginBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val TAG: String = "LoginFragment"



class LoginFragment : Fragment() {

    private var binding : FragmentLoginBinding?=null
    private var auth : FirebaseAuth = Firebase.auth
    private var database: FirebaseDatabase = Firebase.database



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding= FragmentLoginBinding.inflate(inflater, container, false)

        setLoginBtn()


        setRegisterBtnReplaceRegisterFragment()



        return binding?.root
    }

    private fun setLoginBtn() {
        binding?.btnLogin?.setOnClickListener {
            val email = binding?.editTextEmail?.text.toString()
            val password = binding?.editTextPassword?.text.toString()
            loginAccount(email, password)
        }
    }

    private fun loginAccount(email: String, password: String) {
        Log.d("tag", "로그인 시도")
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this.activity as Activity) { task ->
                if (task.isSuccessful) {

                    Log.d(TAG,"로그인 성공")
                    Toast.makeText(this.activity  as Activity, "로그인 성공", Toast.LENGTH_LONG).show()

                    val intent = Intent(this.activity, MainActivity::class.java)
                    startActivity(intent)

                } else {

                    Log.d(TAG,"로그인 실패")
                    Toast.makeText(this.activity  as Activity, "로그인 실패", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun setRegisterBtnReplaceRegisterFragment() {
        val registerFragment = RegisterFragment.newInstance()
        val lActivity = activity as LoginActivity
        val textRegister = binding?.textRegister;


        textRegister?.setOnClickListener {
            lActivity.replaceFragment(registerFragment)
        }
    }

    companion object {

        @JvmStatic fun newInstance() =
                LoginFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}