package com.spm.vasylyshyn.counterdisplayapp.dto

import kotlinx.serialization.Serializable

@Serializable
data class DisplayCountDto(
    val id:Int,
    val displayCount:Int,
    val createdDate:String
)
