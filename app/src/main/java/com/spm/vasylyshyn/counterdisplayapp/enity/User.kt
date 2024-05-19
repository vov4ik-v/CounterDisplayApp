package com.spm.vasylyshyn.counterdisplayapp.enity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val email: String,
    val phoneNumber: String? = null,
    val imageUrl: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val defaultAddressForNewDevices: Address? = null,
)