package com.spm.vasylyshyn.counterdisplayapp.enity

import java.time.LocalDateTime

data class DisplayCount(
    val id: Int = 0,
    val displayCount: Int = 0,
    val createdDate: LocalDateTime = LocalDateTime.now(),
)
