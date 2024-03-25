package com.spm.vasylyshyn.counterdisplayapp.enity

import com.spm.vasylyshyn.counterdisplayapp.enums.TypeDevice
import java.time.LocalDateTime

data class Device(
    var typeDevice: TypeDevice? = null,
    var cantoraName: String? = null,
    var address: String? = null,
    var price: Int = 0,
    var frequency: Long = 0,
    var serialNumber: Int = 0,
    var listDisplayCounts: List<DisplayCount> = ArrayList(),
    var createdData: LocalDateTime? = null
)
