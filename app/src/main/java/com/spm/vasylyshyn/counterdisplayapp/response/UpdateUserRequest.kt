package com.spm.vasylyshyn.counterdisplayapp.response

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val username: String,
    val phoneNumber: String,
)
