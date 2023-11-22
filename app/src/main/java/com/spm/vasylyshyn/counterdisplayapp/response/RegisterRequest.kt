package com.spm.vasylyshyn.counterdisplayapp.response

data class RegisterRequest(
    val email: String,
    val firstname: String,
    val lastname: String,
    val username: String,
    val confirmPassword: String,
    val password:String
)
