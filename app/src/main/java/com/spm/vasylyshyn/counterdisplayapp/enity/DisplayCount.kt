package com.spm.vasylyshyn.counterdisplayapp.enity

import java.time.LocalDateTime
data class DisplayCount(
    val id: Int = 0,
    val displayCount: Int = 0,
    val createdDate: LocalDateTime = LocalDateTime.now(),
)
Minor: can we use a primary constractor with data class instead?
class DisplayCount {
    var id:Int = 0
    var displayCount:Int = 0
    var createdDate:LocalDateTime = LocalDateTime.now()

    constructor()
    constructor(id: Int, displayCount: Int, createdDate: LocalDateTime) {
        this.id = id
        this.displayCount = displayCount
        this.createdDate = createdDate
    }

}
