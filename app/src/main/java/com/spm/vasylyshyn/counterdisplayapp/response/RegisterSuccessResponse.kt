package com.spm.vasylyshyn.counterdisplayapp.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterSuccessResponse(
    val success: Boolean,
    val message: String
)
