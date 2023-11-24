package com.spm.vasylyshyn.counterdisplayapp.response

import com.spm.vasylyshyn.counterdisplayapp.TypeDevice

data class RegisterDeviceRequest(
    val deviceNumber: Long,
    val deviceType:TypeDevice,
    val address:String,
    val password:String
)
