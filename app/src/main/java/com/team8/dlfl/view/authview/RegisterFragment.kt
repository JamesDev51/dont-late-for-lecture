package com.team8.dlfl.view.authview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.team8.dlfl.databinding.FragmentRegisterBinding
import com.team8.dlfl.viewmodel.AuthViewModel

private const val TAG: String = "RegisterFragment"

class RegisterFragment : Fragment() {

    private var binding: FragmentRegisterBinding? = null
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRegisterBinding.inflate(inflater)




        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.authActivity=this.activity as AuthActivity

        binding?.btnRegister?.setOnClickListener {
            viewModel.register(
            binding?.editTextEmail?.text.toString(),
            binding?.editTextPassword?.text.toString(),
            binding?.editTextPassword2?.text.toString(),
            binding?.editTextName?.text.toString(),
            binding?.editTextMobile?.text.toString()
            )
        }


        setLoginBtnReplaceLoginFragment()
        }
            private fun setLoginBtnReplaceLoginFragment() {
                val loginFragment = LoginFragment.newInstance()
                val lActivity = activity as AuthActivity
                val textLogin = binding?.textLogin;
                textLogin?.setOnClickListener {
                    lActivity.replaceFragment(loginFragment)
                }
        }

    companion object {

        @JvmStatic
        fun newInstance() =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}



