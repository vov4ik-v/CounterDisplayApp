package com.spm.vasylyshyn.counterdisplayapp.service

import com.spm.vasylyshyn.counterdisplayapp.response.JWTTokenSuccessResponse
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterSuccessResponse
import com.spm.vasylyshyn.counterdisplayapp.response.LoginRequest
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterRequest

interface AuthService {
    suspend fun authenticateUser(loginRequest: LoginRequest): Result<JWTTokenSuccessResponse>
    suspend fun registerUser(registerRequest: RegisterRequest): Result<RegisterSuccessResponse>
}