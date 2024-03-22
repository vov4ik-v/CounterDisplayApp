package com.spm.vasylyshyn.counterdisplayapp.response

import kotlinx.serialization.Serializable

@Serializable
data class JWTTokenSuccessResponse(
    val success: Boolean,
    val token: String
)
