package com.spm.vasylyshyn.counterdisplayapp

import java.text.DateFormat

data class User(
    val id:Int,
    val displayCount:Int,
    val numberOfDevice:Int,
    val createdData: DateFormat
)
