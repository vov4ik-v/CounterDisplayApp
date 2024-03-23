package com.spm.vasylyshyn.counterdisplayapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.enity.User
import com.spm.vasylyshyn.counterdisplayapp.response.UpdateUserRequest
import com.spm.vasylyshyn.counterdisplayapp.service.UserService
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditUserInformationActivity : AppCompatActivity() {
    private val userService: UserService by inject()

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