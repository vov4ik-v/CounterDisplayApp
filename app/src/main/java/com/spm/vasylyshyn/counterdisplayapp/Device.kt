package com.spm.vasylyshyn.counterdisplayapp

import java.time.LocalDateTime
import java.util.Date

class Device {
    var typeDevice: TypeDevice? = null
    var cantoraName:String? = null
    var address: String? = null
    var price:Int = 0
    var frequency:Long = 0
    var serialNumber = 0
    var displayCounts: List<DisplayCount>? = null
    var createdData: LocalDateTime = LocalDateTime.now()

    constructor()
    constructor(
        typeDevice: TypeDevice?,
        address: String?,
        serialNumber: Int,
    ) {
        this.typeDevice = typeDevice
        this.address = address
        this.serialNumber = serialNumber
    }

    constructor(
        typeDevice: TypeDevice?,
        cantoraName: String?,
        address: String?,
        price: Int,
        frequency: Long,
        serialNumber: Int,
    ) {
        this.typeDevice = typeDevice
        this.cantoraName = cantoraName
        this.address = address
        this.price = price
        this.frequency = frequency
        this.serialNumber = serialNumber
    }


}
