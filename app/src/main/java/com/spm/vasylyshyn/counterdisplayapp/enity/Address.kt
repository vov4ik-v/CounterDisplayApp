package com.spm.vasylyshyn.counterdisplayapp.enity

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val region: String,
    val city: String,
    val street: String,
    val number: String,
)
