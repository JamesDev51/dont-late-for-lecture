package com.team8.dlfl.viewmodel

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team8.dlfl.view.authview.AuthActivity
import com.team8.dlfl.view.authview.LoginFragment
import com.team8.dlfl.model.LoginUserModel
import com.team8.dlfl.model.RegisterUserModel
import com.team8.dlfl.repository.AuthRepository
import com.team8.dlfl.view.mainview.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "AuthViewModel"

class AuthViewModel():ViewModel() {

    @SuppressLint("StaticFieldLeak")
    lateinit var authActivity: AuthActivity

    @SuppressLint("StaticFieldLeak")
    lateinit  var mainActivity: MainActivity

    private var _email = MutableLiveData<String>("")
    val email: LiveData<String> get() = _email

    private var _name = MutableLiveData<String>("")
    val name: LiveData<String> get() = _name

    private var _phone = MutableLiveData<String>("")
    val phone: LiveData<String> get() = _phone

    private val repository: AuthRepository = AuthRepository()
    private lateinit var registerUser: RegisterUserModel
    var loginUser: LoginUserModel = LoginUserModel("", "")

    fun init(){
        repository.observeInfo(_email,_name,_phone)
    }


    fun modifyName(newName: String) {
        _name.value = newName
        repository.postName(newName)
    }
    fun modifyPhone(newPhone: String) {
        _phone.value = newPhone
        repository.postPhone(newPhone)
    }




    fun register(email:String, password: String, password2: String, name: String,phone: String){
        this.registerUser=RegisterUserModel(email,password,password2,name,phone)
        if(isValidInfo()){
            CoroutineScope(Dispatchers.IO).launch {
                val result=repository.createAccount(registerUser)
                Log.d(TAG,"repository 결과: $result")
                if(result.status){
                    loginUser= LoginUserModel(registerUser.email,registerUser.password)
                    changeViewLoginFragment()
                }
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    Toast.makeText(
                        authActivity, result.message,
                        Toast.LENGTH_SHORT
                    ).show()
                },0)
            }
        }
    }

    private fun changeViewLoginFragment() {
        val loginFragment = LoginFragment.newInstance()
        authActivity.replaceFragment(loginFragment)
    }

    private fun isValidInfo(): Boolean{
        //비어있으면 토스트 띄우기
        if(registerUser.email.isEmpty() || registerUser.password.isEmpty() || registerUser.password2.isEmpty() || registerUser.name.isEmpty() || registerUser.phone.isEmpty()){
            Toast.makeText(
                authActivity, "비어있는 필드가 존재합니다.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        if(registerUser.password != registerUser.password2){
            Toast.makeText(
                authActivity, "비밀번호와 확인 비밀번호가 일치하지 않습니다.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    fun login(loginUser:LoginUserModel) {
        Log.d(TAG, "로그인")
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.loginAccount(loginUser)
            Log.d(TAG,"repository 결과: $result")
            if(result.status){
                authActivity.startMainActivity()
            }
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                Toast.makeText(
                    authActivity, result.message,
                    Toast.LENGTH_SHORT
                ).show()
            },0)
        }
    }
    fun logout() {
        Log.d(TAG, "로그아웃")
        repository.logout()
        mainActivity.startAuthActivity()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            Toast.makeText(
                mainActivity, "로그아웃 하였습니다.",
                Toast.LENGTH_SHORT
            ).show()
        },0)

    }


}