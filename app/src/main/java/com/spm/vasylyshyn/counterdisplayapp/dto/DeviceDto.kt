package com.spm.vasylyshyn.counterdisplayapp.dto

data class DeviceDto(
    val numberOfDevice:Int,
    val cantoraName:String,
    val counterType:String,
    val price:Int,
    val address:String,
    val frequency:Long,
    val displayCounts: List<DisplayCountDto>,
    val createdDate: String,
)