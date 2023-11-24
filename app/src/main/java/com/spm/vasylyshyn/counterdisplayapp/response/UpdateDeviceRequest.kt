package com.spm.vasylyshyn.counterdisplayapp.response

import com.spm.vasylyshyn.counterdisplayapp.TypeDevice

data class UpdateDeviceRequest(
    val deviceType:TypeDevice,
    val cantoraName:String,
    val address:String,
    val price:Int,
    val frequency:Int
)