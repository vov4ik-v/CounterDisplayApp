package com.spm.vasylyshyn.counterdisplayapp.enity

import com.spm.vasylyshyn.counterdisplayapp.enums.TypeDevice
import java.time.LocalDateTime

class Device {
    var typeDevice: TypeDevice? = null
    var cantoraName:String? = null
    var address: String? = null
    var price:Int = 0
    var frequency:Long = 0
    var serialNumber = 0
    var listDisplayCounts: List<DisplayCount> = ArrayList<DisplayCount>()
    var createdData: LocalDateTime? = null

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
        mapDisplayCounts: List<DisplayCount>,
        createdData: LocalDateTime?
    ) {
        this.typeDevice = typeDevice
        this.cantoraName = cantoraName
        this.address = address
        this.price = price
        this.frequency = frequency
        this.serialNumber = serialNumber
        this.listDisplayCounts = mapDisplayCounts
        this.createdData = createdData
    }


}
