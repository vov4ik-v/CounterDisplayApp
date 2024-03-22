package com.spm.vasylyshyn.counterdisplayapp.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val firstname: String,
    val lastname: String,
    val username: String,
    val confirmPassword: String,
    val password: String
)
