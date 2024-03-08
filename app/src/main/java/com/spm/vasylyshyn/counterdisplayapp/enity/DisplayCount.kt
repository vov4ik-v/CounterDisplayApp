package com.spm.vasylyshyn.counterdisplayapp.enity

import java.time.LocalDateTime

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
