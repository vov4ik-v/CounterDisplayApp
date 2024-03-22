package com.spm.vasylyshyn.counterdisplayapp.enity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String,
    val email: String,
    val address: Address?,
)