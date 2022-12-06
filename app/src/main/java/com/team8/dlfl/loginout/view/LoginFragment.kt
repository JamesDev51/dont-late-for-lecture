package com.team8.dlfl.loginout.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.team8.dlfl.databinding.FragmentLoginBinding
import com.team8.dlfl.loginout.model.LoginUserModel
import com.team8.dlfl.loginout.viewmodel.RegisterFragment


private const val TAG: String = "LoginFragment"

class LoginFragment : Fragment() {

    var binding : FragmentLoginBinding?=null
    private val viewModel: AuthViewModel by activityViewModels()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding= FragmentLoginBinding.inflate(inflater)

        viewModel.authActivity=this.activity as AuthActivity
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