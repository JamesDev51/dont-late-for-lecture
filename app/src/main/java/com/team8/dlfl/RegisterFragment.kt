package com.team8.dlfl

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.team8.dlfl.databinding.FragmentRegisterBinding

private const val TAG: String = "RegisterFragment"

class RegisterFragment : Fragment() {

    private var binding: FragmentRegisterBinding? = null
    private var auth : FirebaseAuth = Firebase.auth
    private var database: FirebaseDatabase = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRegisterBinding.inflate(inflater,container,false)



        binding?.btnRegister?.setOnClickListener{
            val email = binding?.editTextEmail?.text.toString()
            val password = binding?.editTextPassword?.text.toString()
            val password2 = binding?.editTextPassword2?.text.toString()
            val name = binding?.editTextName?.text.toString()
            val phone = binding?.editTextMobile?.text.toString()

            createAccount(email,password,password2,name,phone)
        }
        setLoginBtnReplaceLoginFragment()

        return binding?.root
    }

    private fun createAccount(email: String, password: String, password2: String, name: String, phone: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && password2.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty() && password == password2) {
            Log.d(TAG,"회원가입 시도")

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this.activity as Activity) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG,"회원가입 성공")

                        val uid = task.result.user?.uid

                        val hashMap = HashMap<String, String>()
                        uid?.let { hashMap.put("uid", it) }
                        name.let{hashMap.put("name",name)}
                        phone.let{hashMap.put("phone",phone)}

                        val reference = database.reference
                        uid?.let { reference.child(it).setValue(hashMap) }

                        val loginFragment = LoginFragment.newInstance()
                        val lActivity = activity as LoginActivity

                        lActivity.replaceFragment(loginFragment)


                        Toast.makeText(
                            this.activity  as Activity, "계정 생성 완료.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.d(TAG,"회원가입 실패")
                        Toast.makeText(
                            this.activity as Activity, "계정 생성 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }



    private fun setLoginBtnReplaceLoginFragment() {
        val loginFragment = LoginFragment.newInstance()
        val lActivity = activity as LoginActivity
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