package com.spm.vasylyshyn.counterdisplayapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.spm.vasylyshyn.counterdisplayapp.adapter.CardScrollAdapter
import com.spm.vasylyshyn.counterdisplayapp.enity.Device
import com.spm.vasylyshyn.counterdisplayapp.dto.DeviceDto
import com.spm.vasylyshyn.counterdisplayapp.enity.DisplayCount
import com.spm.vasylyshyn.counterdisplayapp.activity.LoginActivity
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.activity.RegisterDeviceActivity
import com.spm.vasylyshyn.counterdisplayapp.enums.TypeDevice
import com.spm.vasylyshyn.counterdisplayapp.API_URL_PATH
import com.spm.vasylyshyn.counterdisplayapp.dto.DisplayCountDto
import com.spm.vasylyshyn.counterdisplayapp.service.UserService
import it.sephiroth.android.library.widget.HListView
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val addDeviceButton: Button = view.findViewById(R.id.addDeviceButton)
        if (LoginActivity.token.isNotEmpty()) {
            uploadDevice { devices ->
                if (devices.isNotEmpty()) {
                    val arrayAdapter = activity?.let { CardScrollAdapter(it, devices as ArrayList<Device>, this) }
                    val horizontalListView = view.findViewById<HListView>(R.id.scroll_device)
                    horizontalListView.adapter = arrayAdapter

                }
            }
        }
        addDeviceButton.setOnClickListener {
            val intent = Intent(context, RegisterDeviceActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        val token = LoginActivity.token
        headerMap["Authorization"] = token
        return headerMap
    }

    fun uploadDevice(callback: (List<Device>) -> Unit) {
        val retrofit =
            Retrofit.Builder().baseUrl(API_URL_PATH).addConverterFactory(Json.asConverterFactory("application/json".toMediaType())).build()
        val apiService = retrofit.create(UserService::class.java)

        apiService.getDevicesForCurrentUser(getHeaderMap()).enqueue(object : Callback<List<DeviceDto>> {
            override fun onResponse(
                call: Call<List<DeviceDto>>, response: Response<List<DeviceDto>>
            ) {
                onDevicesReceived(response, callback)
            }

            override fun onFailure(call: Call<List<DeviceDto>>, t: Throwable) {
                Log.d("CounterDisplayApp.MainFragment", "getDevicesForCurrentUser failed with message ${t.message.toString()}")
                callback(emptyList())
            }
        })
    }

    private fun onDevicesReceived(
        response: Response<List<DeviceDto>>, callback: (List<Device>) -> Unit
    ) {
        if (!response.isSuccessful) return

        val arrayDevice: List<Device> = response.body().orEmpty().map(::parseDeviceFromDto).toList()
        callback(arrayDevice)
    }

    private fun parseDeviceFromDto(deviceDto: DeviceDto): Device {
        val date = LocalDateTime.now()
        val typeForDevice: TypeDevice = when (deviceDto.counterType) {
            "GAS" -> TypeDevice.GAS
            "WATER" -> TypeDevice.WATER
            "LIGHT" -> TypeDevice.LIGHT
            else -> {
                Log.e("CounterDisplayApp.MainFragment", "Invalid counter type. Using Gas as default")
                TypeDevice.GAS
            }
        }
        val displayCounts: List<DisplayCount> = deviceDto.displayCounts.map(::parseDisplayCountFromDto).toList()
        val device = Device(
            typeForDevice,
            cantoraName = deviceDto.cantoraName,
            address = deviceDto.address,
            price = deviceDto.price,
            frequency = deviceDto.frequency,
            serialNumber = deviceDto.numberOfDevice,
            listDisplayCounts = displayCounts,
            createdData = date
        )
        return device
    }

    private fun parseDisplayCountFromDto(displayCount: DisplayCountDto): DisplayCount {
        val displayCountCreatedDate = runCatching {
            LocalDateTime.parse(displayCount.createdDate, DateTimeFormatter.ISO_DATE_TIME)
        }.getOrElse {
            Log.d("CounterDisplayApp.MainFragment", "Could not parse display count created date")
            LocalDateTime.now()
        }
        val displayCountForDevice = DisplayCount(
            displayCount.id, displayCount.displayCount, displayCountCreatedDate
        )
        return displayCountForDevice
    }
}