package com.spm.vasylyshyn.counterdisplayapp.service

import com.spm.vasylyshyn.counterdisplayapp.response.JWTTokenSuccessResponse
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterSuccessResponse
import com.spm.vasylyshyn.counterdisplayapp.response.LoginRequest
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/signin")
    fun authenticateUser(@Body loginRequest: LoginRequest): Call<JWTTokenSuccessResponse>

    @POST("auth/signup")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterSuccessResponse>

    @POST("path/to/endpoint")
    fun postRequest(@Body body: Map<String, Any>): Call<ResponseBody>
}