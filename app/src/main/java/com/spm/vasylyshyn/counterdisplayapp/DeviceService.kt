package com.spm.vasylyshyn.counterdisplayapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DeviceService {
    @GET("device/getAllDevices")
    fun getAllDevicesResponse(): Call<List<DeviceDto>>
    @GET("device/getDeviceByNumber/{numberOfDevice}")
    fun getDeviceByNumber(@Path("numberOfDevice") numberOfDevice: Long): Call<DeviceDto>
    @POST("device/register")
    fun  register(@Body device: Device) : Call<ApiResponse>

}