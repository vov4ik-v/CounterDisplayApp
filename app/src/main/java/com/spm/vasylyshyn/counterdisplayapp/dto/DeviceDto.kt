package com.spm.vasylyshyn.counterdisplayapp.dto

import kotlinx.serialization.Serializable

@Serializable
data class DeviceDto(
    val numberOfDevice: Int,
    val cantoraName: String,
    val counterType: String?,
    val price: Int,
    val address: String,
    val frequency: Long,
    val displayCounts: List<DisplayCountDto>,
    // TODO: Uncomment after migration changes 
    // val createdDate: String? = null,
)