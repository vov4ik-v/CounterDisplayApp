package com.spm.vasylyshyn.counterdisplayapp

import com.spm.vasylyshyn.counterdisplayapp.response.LoginRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

    interface AuthService {
        @POST("auth/signin")
        fun authenticateUser(@Body loginRequest: LoginRequest): Call<JWTTokenSuccessResponse>
        @POST("auth/signup")
        fun registerUser(): Call<Any>
        @POST("path/to/endpoint")
        fun postRequest(@Body body: Map<String, Any>): Call<ResponseBody>
}