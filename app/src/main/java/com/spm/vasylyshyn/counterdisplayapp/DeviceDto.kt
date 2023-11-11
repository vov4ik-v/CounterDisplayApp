package com.spm.vasylyshyn.counterdisplayapp

import java.text.DateFormat
import java.time.LocalDateTime

data class DeviceDto(
    val numberOfDevice:Int,
    val cantoraName:String,
    val counterType:String,
    val price:Int,
    val address:String,
    val frequency:Long,
    val displayCounts: List<DisplayCount>,
    val createdData: LocalDateTime,
)