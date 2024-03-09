package com.spm.vasylyshyn.counterdisplayapp.service

import com.spm.vasylyshyn.counterdisplayapp.dto.DeviceDto
import com.spm.vasylyshyn.counterdisplayapp.enity.User
import com.spm.vasylyshyn.counterdisplayapp.response.ApiResponse
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterDeviceRequest
import com.spm.vasylyshyn.counterdisplayapp.response.UpdateUserRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("user/")
    fun getCurrentUser(@HeaderMap headers: Map<String, String>): Call<User>
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
    fun updateUser(@HeaderMap headers: Map<String, String>,@Body updateUserRequest: UpdateUserRequest) : Call<User>

}