package com.team8.dlfl.loginout.model

data class RegisterUserModel (
    var email :String,
    var password :String,
    var password2 :String,
    var name : String,
    var phone :String
){
    lateinit var uid:String
}