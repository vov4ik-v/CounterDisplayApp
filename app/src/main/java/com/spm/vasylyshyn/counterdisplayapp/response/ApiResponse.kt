package com.spm.vasylyshyn.counterdisplayapp.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val success: Boolean,
    val message: String,
    val timestamp: String
)
