package com.spm.vasylyshyn.counterdisplayapp

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.spm.vasylyshyn.counterdisplayapp.service.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

private const val TIME_OUT = 60_000

class CounterDisplayApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val ktorHttpClient = HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })

                engine {
                    connectTimeout = TIME_OUT
                    socketTimeout = TIME_OUT
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Logger Ktor =>", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                url {
                    protocol = URLProtocol.HTTP
                    host = "192.168.0.103"
                    port = 8088
                }
            }
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL_PATH)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
        val androidContext = this@CounterDisplayApplication
        val mainModule = module {
            single { AuthServiceKtor(ktorHttpClient) } bind AuthService::class
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