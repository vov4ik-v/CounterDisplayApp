package com.spm.vasylyshyn.counterdisplayapp.response

import com.spm.vasylyshyn.counterdisplayapp.TypeDevice

data class UpdateUserRequest(
    val username: String,
    val phoneNumber: String,
)
