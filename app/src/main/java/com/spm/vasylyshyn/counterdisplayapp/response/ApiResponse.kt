package com.spm.vasylyshyn.counterdisplayapp.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val message: String,
    val success: Boolean
)
