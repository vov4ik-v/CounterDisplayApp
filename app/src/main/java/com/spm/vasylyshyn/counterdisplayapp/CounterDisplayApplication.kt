package com.spm.vasylyshyn.counterdisplayapp

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.spm.vasylyshyn.counterdisplayapp.service.AuthService
import com.spm.vasylyshyn.counterdisplayapp.service.DeviceService
import com.spm.vasylyshyn.counterdisplayapp.service.DisplayCountService
import com.spm.vasylyshyn.counterdisplayapp.service.UserService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

class CounterDisplayApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL_PATH)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
        val androidContext = this@CounterDisplayApplication
        val mainModule = module {
            single { retrofit.create(AuthService::class.java) } bind AuthService::class
            single { retrofit.create(DeviceService::class.java) } bind DeviceService::class
            single { retrofit.create(DisplayCountService::class.java) } bind DisplayCountService::class
            single { retrofit.create(UserService::class.java) } bind UserService::class
            single { PreferenceManager.getDefaultSharedPreferences(get()) } bind SharedPreferences::class
        }
        startKoin {
            androidContext(androidContext)
            modules(mainModule)
        }
    }
}