package com.team8.dlfl.loginout.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.team8.dlfl.databinding.FragmentMyPageBinding
import com.team8.dlfl.main.MainActivity


class MyPageFragment : Fragment() {

    var binding : FragmentMyPageBinding? = null
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater)

        authViewModel.mainActivity= activity as MainActivity
        authViewModel.init()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        authViewModel.email.observe(viewLifecycleOwner){
            binding?.txtEmail?.text = authViewModel.email.value
        }
        authViewModel.name.observe(viewLifecycleOwner){
            binding?.editTxtName?.setText(authViewModel.name.value)
        }
        authViewModel.phone.observe(viewLifecycleOwner){
            binding?.editTxtPhone?.setText(authViewModel.phone.value)
        }

        binding?.editTxtName?.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                val name = binding?.editTxtName?.text.toString()
                authViewModel.modifyName(name)

            }
            false
        }

        binding?.editTxtPhone?.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                val phone=binding?.editTxtPhone?.text.toString()
                authViewModel.modifyPhone(phone)
            }
            false
        }

        binding?.btnLogout?.setOnClickListener {
            authViewModel.logout()
        }
    }
}