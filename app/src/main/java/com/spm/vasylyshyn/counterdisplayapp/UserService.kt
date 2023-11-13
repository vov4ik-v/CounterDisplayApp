package com.spm.vasylyshyn.counterdisplayapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
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
    @Headers("Authorization:BEARER eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpZXMiOlt7ImF1dGhvcml0eSI6IlVTRVIifV0sImlkIjoiMSIsImlhdCI6MTY5OTc4ODc3NCwidXNlcm5hbWUiOiJ2YXNpbGl3aW5AZ21haWwuY29tIn0.xr6YJPvh_1y_1DaraG3_xfPbhCE3POnBh91Jrdz-eetPQtAGiv56nIFN7nOYwlqh7Vf2jB4-G4UNorrBOVd_XA")
        fun getDevicesForCurrentUser(): Call<List<DeviceDto>>
    @GET("user/{username}")
    fun getUserProfile(@Path("username") username: String ) : Call<User>
    @GET("user/byId/{id}")
    fun getUserById(@Path("id") id: Long ) : Call<User>
    @POST("user/update")
//    header
    fun updateUser(@Body user: User) : Call<Any>

}