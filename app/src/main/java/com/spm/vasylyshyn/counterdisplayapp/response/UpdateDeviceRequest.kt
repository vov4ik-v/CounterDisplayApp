package com.spm.vasylyshyn.counterdisplayapp.response

import com.spm.vasylyshyn.counterdisplayapp.enums.TypeDevice
import kotlinx.serialization.Serializable

//TODO: Update the model due to backend changes
@Serializable
data class UpdateDeviceRequest(
    val deviceType: TypeDevice,
    val cantoraName: String,
    val address: String,
    val price: Int,
    val frequency: Int
)