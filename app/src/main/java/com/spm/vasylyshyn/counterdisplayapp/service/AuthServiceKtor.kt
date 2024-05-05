package com.spm.vasylyshyn.counterdisplayapp.service

import com.spm.vasylyshyn.counterdisplayapp.response.JWTTokenSuccessResponse
import com.spm.vasylyshyn.counterdisplayapp.response.LoginRequest
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterRequest
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterSuccessResponse
import io.ktor.client.*
import io.ktor.client.request.*

class AuthServiceKtor(private val httpClient: HttpClient) : AuthService {
    override suspend fun authenticateUser(loginRequest: LoginRequest): Result<JWTTokenSuccessResponse> = runCatching {
        httpClient.post("/api/auth/signin") {
            body = loginRequest
        }
    }

    override suspend fun registerUser(registerRequest: RegisterRequest): Result<RegisterSuccessResponse> = runCatching {
        httpClient.post("/api/auth/signup") {
            body = registerRequest
        }
    }
}