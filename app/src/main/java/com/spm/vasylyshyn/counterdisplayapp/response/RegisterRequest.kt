package com.spm.vasylyshyn.counterdisplayapp.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String
)
