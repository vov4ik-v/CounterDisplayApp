package com.spm.vasylyshyn.counterdisplayapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterDeviceRequest
import com.spm.vasylyshyn.counterdisplayapp.response.UpdateDeviceRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SettingsDeviceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_device)
        val intent: Intent = intent
        val periodType: Spinner = findViewById(R.id.spinnerTypePeriod)
        val saveButton: Button = findViewById(R.id.save_button)
        val arrayPeriodType = arrayOf("Днів")
        val adapterSpinner =
            ArrayAdapter<String>(applicationContext, R.layout.spinner_item, arrayPeriodType)
        periodType.adapter = adapterSpinner
        val serialNumberDevice = intent.getIntExtra("serialNumber", 0)
        val price = intent.getIntExtra("price", 0)
        val counterType = intent.getStringExtra("counterType")
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
            val adapterSpinner = ArrayAdapter<String>(applicationContext, R.layout.spinner_item, arrayDeviceType)
            editDeviceType.adapter = adapterSpinner
            saveButton.setOnClickListener(View.OnClickListener {
                if (editAddress.text.isEmpty() || editCantoraName.text.isEmpty() || editPeriod.text.isEmpty() || editPrice.text.isEmpty()  || editDeviceType.isEmpty()) {
                    val alertDialog = AlertDialog.Builder(this@SettingsDeviceActivity).create()
                    alertDialog.setTitle("Увага")
                    alertDialog.setMessage("Заповніть всі дані.")
                    alertDialog.setButton(
                        AlertDialog.BUTTON_NEUTRAL, "OK"
                    ) { dialog, whichw -> dialog.dismiss() }
                    alertDialog.show()
                } else {
                    val typeForDevice: TypeDevice = when (editDeviceType.selectedItem.toString()) {
                        "GAS" -> {
                            TypeDevice.GAS

                        }

                        "WATER" -> {
                            TypeDevice.WATER
                        }

                        else -> {
                            TypeDevice.LIGHT
                        }
                    }
                    val updateDeviceRequest: UpdateDeviceRequest = UpdateDeviceRequest(
                        typeForDevice,
                        editCantoraName.text.toString(),
                        editAddress.text.toString(),
                        editPrice.text.toString().toInt(),
                        editPeriod.text.toString().toInt()
                    )
                    val retrofit = Retrofit.Builder()
                        .baseUrl("http://192.168.31.86:8080/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val deviceService = retrofit.create(DeviceService::class.java)
                    deviceService.updateDevice(getHeaderMap(),serialNumberDevice, updateDeviceRequest)
                        .enqueue(object :
                            Callback<Device> {
                            override fun onResponse(
                                call: Call<Device>,
                                response: Response<Device>
                            ) {
                        if (response.code()==200) {
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        }
                            }

                            override fun onFailure(call: Call<Device>, t: Throwable) {
                                Log.d("Test response", t.message.toString())
                            }
                        })
                }
            })
        }
    }
    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        val token = LoginActivity.token
        headerMap["Authorization"] = token
        return headerMap
    }
}