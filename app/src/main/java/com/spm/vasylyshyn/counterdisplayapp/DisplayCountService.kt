package com.spm.vasylyshyn.counterdisplayapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DisplayCountService {
    @GET("display_count/getDisplayCountsByDeviceNumber/{deviceNumber}")
    fun getDisplayCountsByDeviceNumber(@Path("deviceNumber") deviceNumber: Long  ): Call<List<Device>>
    @GET("display_count/getAllDisplayCounts")
    fun getAllDisplayCounts(): Call<List<Device>>
    @POST("display_count/addDisplayCount")
    fun addDisplayCount(@Body displayCount: DisplayCount, @Query("device_id") deviceId:Long  ) : Call<Unit>
}