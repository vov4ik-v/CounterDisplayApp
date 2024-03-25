package com.spm.vasylyshyn.counterdisplayapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.API_URL_PATH
import com.spm.vasylyshyn.counterdisplayapp.enity.User
import com.spm.vasylyshyn.counterdisplayapp.response.UpdateUserRequest
import com.spm.vasylyshyn.counterdisplayapp.service.UserService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class EditUserInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_user_information_activity)
        val username = intent.getStringExtra("username")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val editUsername: EditText = findViewById(R.id.ed_username)
        val editPhoneNumber: EditText = findViewById(R.id.ed_phone_number)
        editUsername.setText(username)
        editPhoneNumber.setText(phoneNumber)
        val buttonSaveProfile: Button = findViewById(R.id.btn_save_profile)
        buttonSaveProfile.setOnClickListener {
            val updateUserRequest = UpdateUserRequest(
                editUsername.text.toString(), editPhoneNumber.text.toString()
            )
            val retrofit = Retrofit.Builder()
                .baseUrl(API_URL_PATH)
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .build()
            val userService = retrofit.create(UserService::class.java)
            userService.updateUser(getHeaderMap(), updateUserRequest)
                .enqueue(object :
                    Callback<User> {
                    override fun onResponse(
                        call: Call<User>,
                        response: Response<User>
                    ) {
                        if (response.code() == 200) {
                            val intent = Intent(applicationContext, ProfileActivity::class.java)
                            intent.putExtra("username", response.body()?.username.toString())
                            intent.putExtra("phoneNumber", response.body()?.phoneNumber.toString())
                            Log.i("CounterDisplayApp.EditUserInformationActivity", "updateUser successful")
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("CounterDisplayApp.EditUserInformationActivity", "updateUser failed with message: ${t.message.toString()}")
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