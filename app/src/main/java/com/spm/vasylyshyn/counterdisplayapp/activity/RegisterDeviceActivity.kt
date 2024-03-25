package com.spm.vasylyshyn.counterdisplayapp.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.enums.TypeDevice
import com.spm.vasylyshyn.counterdisplayapp.response.ApiResponse
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterDeviceRequest
import com.spm.vasylyshyn.counterdisplayapp.service.UserService
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterDeviceActivity : AppCompatActivity() {
    private val userService: UserService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_device)
        val deviceType: Spinner = findViewById(R.id.spinnerTypeDevice)
        val buttonRegisterDevice: Button = findViewById(R.id.button_register_device)
        val arrayDeviceType = arrayOf("GAS", "WATER", "LIGHT")
        val adapterSpinner = ArrayAdapter(applicationContext, R.layout.spinner_item, arrayDeviceType)
        deviceType.adapter = adapterSpinner
        val serialNumberDevice: EditText = findViewById(R.id.serialNumberDevice)
        val addressDevice: EditText = findViewById(R.id.addressDevice)
        val password: EditText = findViewById(R.id.passwordDevice)


        buttonRegisterDevice.setOnClickListener {
            if (serialNumberDevice.text.isEmpty() || addressDevice.text.isEmpty() || password.text.isEmpty()) {
                val alertDialog = AlertDialog.Builder(this@RegisterDeviceActivity).create()
                alertDialog.setTitle("Увага")
                alertDialog.setMessage("Заповніть всі дані.")
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, _ -> dialog.dismiss() }
                alertDialog.show()
            } else {
                val typeForDevice: TypeDevice = when (deviceType.selectedItem.toString()) {
                    "GAS" -> TypeDevice.GAS
                    "WATER" -> TypeDevice.WATER
                    else -> TypeDevice.LIGHT
                }
                val registerDeviceRequest = RegisterDeviceRequest(
                    serialNumberDevice.text.toString().toLong(),
                    typeForDevice,
                    addressDevice.text.toString(),
                    password.text.toString()
                )
                userService.addDeviceToUser(getHeaderMap(), registerDeviceRequest).enqueue(object :
                    Callback<ApiResponse> {
                    override fun onResponse(
                        call: Call<ApiResponse>,
                        response: Response<ApiResponse>
                    ) {

                        if (response.body()?.success == true) {
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            Log.i("CounterDisplayApp.RegisterDeviceActivity", "addDeviceToUser successful")
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Log.e("CounterDisplayApp.RegisterDeviceActivity", "addDeviceToUser failed with message: ${t.message.toString()}")
                    }
                })
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