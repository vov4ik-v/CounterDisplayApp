package com.spm.vasylyshyn.counterdisplayapp.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.API_URL_PATH
import com.spm.vasylyshyn.counterdisplayapp.enums.TypeDevice
import com.spm.vasylyshyn.counterdisplayapp.response.ApiResponse
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterDeviceRequest
import com.spm.vasylyshyn.counterdisplayapp.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterDeviceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_device)
        val intent: Intent = intent
        val deviceType: Spinner = findViewById(R.id.spinnerTypeDevice)
        val buttonRegisterDevice:Button = findViewById(R.id.button_register_device)
        val arrayDeviceType = arrayOf("GAS", "WATER", "LIGHT")
        val adapterSpinner =
            ArrayAdapter<String>(applicationContext, R.layout.spinner_item, arrayDeviceType)
        deviceType.adapter = adapterSpinner
        val serialNumberDevice:EditText = findViewById(R.id.serialNumberDevice)
        val addressDevice :EditText = findViewById(R.id.addressDevice)
        val password :EditText = findViewById(R.id.passwordDevice)


        buttonRegisterDevice.setOnClickListener(View.OnClickListener {
            if (serialNumberDevice.text.isEmpty() || addressDevice.text.isEmpty() || password.text.isEmpty()) {
                val alertDialog = AlertDialog.Builder(this@RegisterDeviceActivity).create()
                alertDialog.setTitle("Увага")
                alertDialog.setMessage("Заповніть всі дані.")
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, whichw -> dialog.dismiss() }
                alertDialog.show()
            } else {
                val typeForDevice: TypeDevice = when (deviceType.selectedItem.toString()) {
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
                val registerDeviceRequest:RegisterDeviceRequest = RegisterDeviceRequest(serialNumberDevice.text.toString().toLong(),typeForDevice,addressDevice.text.toString(), password.text.toString())
                val retrofit = Retrofit.Builder()
                    .baseUrl(API_URL_PATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val userService = retrofit.create(UserService::class.java)
                userService.addDeviceToUser(getHeaderMap(),registerDeviceRequest).enqueue(object :
                    Callback<ApiResponse> {
                    override fun onResponse(
                        call: Call<ApiResponse>,
                        response: Response<ApiResponse>
                    ) {

                        if (response.body()?.success == true) {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Log.d("Test response", t.message.toString())
                    }
                })
            }
        })

    }
    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        val token = LoginActivity.token
        headerMap["Authorization"] = token
        return headerMap
    }
}