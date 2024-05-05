package com.spm.vasylyshyn.counterdisplayapp.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String,
)
