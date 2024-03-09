package com.spm.vasylyshyn.counterdisplayapp.service

import com.spm.vasylyshyn.counterdisplayapp.enity.Device
import com.spm.vasylyshyn.counterdisplayapp.dto.DeviceDto
import com.spm.vasylyshyn.counterdisplayapp.response.ApiResponse
import com.spm.vasylyshyn.counterdisplayapp.response.UpdateDeviceRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path

interface DeviceService {
    @GET("device/getAllDevices")
    fun getAllDevicesResponse(): Call<List<DeviceDto>>
    @GET("device/getDeviceByNumber/{numberOfDevice}")
    fun getDeviceByNumber(@Path("numberOfDevice") numberOfDevice: Long): Call<DeviceDto>
    @POST("device/register")
    fun  register(@Body device: Device) : Call<ApiResponse>
    @POST("device/updateDevice/{deviceId}")
    fun  updateDevice(@HeaderMap headers: Map<String, String>,@Path("deviceId") deviceId: Int,@Body updateDeviceRequest: UpdateDeviceRequest) : Call<Device>

}