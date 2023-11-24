package com.spm.vasylyshyn.counterdisplayapp

import com.spm.vasylyshyn.counterdisplayapp.response.RegisterDeviceRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("user/")
    @Headers("Authorization:BEARER eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpZXMiOlt7ImF1dGhvcml0eSI6IlVTRVIifV0sImlkIjoiMTIiLCJpYXQiOjE2OTk1Nzg5NDQsInVzZXJuYW1lIjoidmFzaWxpd2luQGdtYWlsLmNvbSJ9.IJFtXk0Z91DEQPsR_ieJRhntLxbgCHmFfvy68EOHk_XxEMIibehQcmnHHF2bL4YjMDaq94bU-Ks4dm5bAW7InQ")
    fun getCurrentUser(): Call<User>
    @GET("user/allUser")
    fun getAllUsers(): Call<List<User>>
    @GET("user/getDevicesForCurrentUser")
        fun getDevicesForCurrentUser(@HeaderMap headers: Map<String, String>): Call<List<DeviceDto>>
    @POST("user/addDeviceToUser")
    fun addDeviceToUser(@HeaderMap headers: Map<String, String>,@Body registerDeviceRequest: RegisterDeviceRequest): Call<ApiResponse>
    @GET("user/{username}")
    fun getUserProfile(@Path("username") username: String ) : Call<User>
    @GET("user/byId/{id}")
    fun getUserById(@Path("id") id: Long ) : Call<User>
    @POST("user/update")
//    header
    fun updateUser(@Body user: User) : Call<Any>

}