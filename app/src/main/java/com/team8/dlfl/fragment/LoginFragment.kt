package com.team8.dlfl.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.team8.dlfl.activity.AuthActivity
import com.team8.dlfl.activity.MainActivity
import com.team8.dlfl.databinding.FragmentLoginBinding
import com.team8.dlfl.model.LoginUserModel
import com.team8.dlfl.viewmodel.AuthViewModel


private const val TAG: String = "LoginFragment"

class LoginFragment : Fragment() {

    var binding : FragmentLoginBinding?=null
    private var auth : FirebaseAuth = Firebase.auth
    private var database: FirebaseDatabase = Firebase.database
    private val viewModel: AuthViewModel by activityViewModels()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding= FragmentLoginBinding.inflate(inflater)

        binding?.editTextEmail?.setText(viewModel.loginUser.email)
        binding?.editTextPassword?.setText(viewModel.loginUser.password)

        setLoginBtn()
        setRegisterBtnReplaceRegisterFragment()
        return binding?.root
    }

    private fun setLoginBtn() {
        binding?.btnLogin?.setOnClickListener {
            val email = binding?.editTextEmail?.text.toString()
            val password = binding?.editTextPassword?.text.toString()
            val loginUser = LoginUserModel(email,password)
            viewModel.login(loginUser)
        }
    }

    private fun setRegisterBtnReplaceRegisterFragment() {
        val registerFragment = RegisterFragment.newInstance()
        val lActivity = activity as AuthActivity
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