package com.spm.vasylyshyn.counterdisplayapp.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.API_URL_PATH
import com.spm.vasylyshyn.counterdisplayapp.dto.DeviceDto
import com.spm.vasylyshyn.counterdisplayapp.enums.TypeDevice
import com.spm.vasylyshyn.counterdisplayapp.response.UpdateDeviceRequest
import com.spm.vasylyshyn.counterdisplayapp.service.DeviceService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SettingsDeviceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_device)
        val intent: Intent = intent
        val periodType: Spinner = findViewById(R.id.spinnerTypePeriod)
        val saveButton: Button = findViewById(R.id.save_button)
        val arrayPeriodType = arrayOf("Днів")
        val adapterSpinner = ArrayAdapter(applicationContext, R.layout.spinner_item, arrayPeriodType)
        periodType.adapter = adapterSpinner
        val serialNumberDevice = intent.getIntExtra("serialNumber", 0)
        val price = intent.getIntExtra("price", 0)
        val cantoraName = intent.getStringExtra("cantoraName")
        val addressDevice = intent.getStringExtra("address")
        val frequencyDevice = intent.getLongExtra("frequency", 0)
        if (serialNumberDevice != 0) {
            val editPeriod: EditText = findViewById(R.id.numberPeriod)
            editPeriod.setText(frequencyDevice.toString())
            val editAddress: EditText = findViewById(R.id.editAddressDevice)
            editAddress.setText(addressDevice)
            val editCantoraName: TextView = findViewById(R.id.cantora_nama)
            editCantoraName.text = cantoraName
            val editPrice: TextView = findViewById(R.id.priceForOnes)
            editPrice.text = price.toString()
            val editDeviceType: Spinner = findViewById(R.id.spinnerTypeDevice)
            val arrayDeviceType = arrayOf("GAS", "WATER", "LIGHT")
            val adapterSpinner = ArrayAdapter(
                applicationContext,
                R.layout.spinner_item, arrayDeviceType
            )
            editDeviceType.adapter = adapterSpinner
            saveButton.setOnClickListener {
                if (editAddress.text.isEmpty()
                    || editCantoraName.text.isEmpty()
                    || editPeriod.text.isEmpty()
                    || editPrice.text.isEmpty()
                    || editDeviceType.isEmpty()
                ) {
                    val alertDialog = AlertDialog.Builder(this@SettingsDeviceActivity).create()
                    alertDialog.setTitle("Увага")
                    alertDialog.setMessage("Заповніть всі дані.")
                    alertDialog.setButton(
                        AlertDialog.BUTTON_NEUTRAL, "OK"
                    ) { dialog, whichw -> dialog.dismiss() }
                    alertDialog.show()
                } else {
                    val typeForDevice: TypeDevice = when (editDeviceType.selectedItem.toString()) {
                        "GAS" -> TypeDevice.GAS
                        "WATER" -> TypeDevice.WATER
                        else -> TypeDevice.LIGHT
                    }
                    val updateDeviceRequest = UpdateDeviceRequest(
                        deviceType = typeForDevice,
                        cantoraName = editCantoraName.text.toString(),
                        address = editAddress.text.toString(),
                        price = editPrice.text.toString().toInt(),
                        frequency = editPeriod.text.toString().toInt()
                    )
                    val retrofit = Retrofit.Builder()
                        .baseUrl(API_URL_PATH)
                        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                        .build()
                    val deviceService = retrofit.create(DeviceService::class.java)
                    deviceService.updateDevice(getHeaderMap(), serialNumberDevice, updateDeviceRequest)
                        .enqueue(object :
                            Callback<DeviceDto> {
                            override fun onResponse(
                                call: Call<DeviceDto>,
                                response: Response<DeviceDto>
                            ) {
                                if (response.code() == 200) {
                                    val intent = Intent(applicationContext, MainActivity::class.java)
                                    Log.i("CounterDisplayApp.SettingsDeviceActivity", "updateDevice successful")
                                    startActivity(intent)
                                }
                            }

                            override fun onFailure(call: Call<DeviceDto>, t: Throwable) {
                                Log.e(
                                    "CounterDisplayApp.SettingsDeviceActivity",
                                    "updateDevice failed with message ${t.message.toString()}"
                                )
                            }
                        })
                }
            }
        }
    }

    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        val token = LoginActivity.token
        headerMap["Authorization"] = token
        return headerMap
    }
}