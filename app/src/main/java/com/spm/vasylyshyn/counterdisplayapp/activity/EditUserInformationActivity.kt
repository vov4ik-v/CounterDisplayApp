package com.spm.vasylyshyn.counterdisplayapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.URL_PATH
import com.spm.vasylyshyn.counterdisplayapp.enity.User
import com.spm.vasylyshyn.counterdisplayapp.response.UpdateUserRequest
import com.spm.vasylyshyn.counterdisplayapp.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditUserInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_user_information_activity)
        val username = intent.getStringExtra("username")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val userId = intent.getIntExtra("userId",0)
        val editUsername:EditText = findViewById(R.id.ed_username)
        val editPhoneNumber:EditText = findViewById(R.id.ed_phone_number)
        editUsername.setText(username)
        editPhoneNumber.setText(phoneNumber)
        val buttonSaveProfile:Button = findViewById(R.id.btn_save_profile)
        buttonSaveProfile.setOnClickListener(View.OnClickListener{
            val updateUserRequest: UpdateUserRequest = UpdateUserRequest(
                editUsername.text.toString(),editPhoneNumber.text.toString()
            )
            val retrofit = Retrofit.Builder()
                .baseUrl(URL_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val userService = retrofit.create(UserService::class.java)
            userService.updateUser(getHeaderMap(),updateUserRequest)
                .enqueue(object :
                    Callback<User> {
                    override fun onResponse(
                        call: Call<User>,
                        response: Response<User>
                    ) {
                        if (response.code()==200) {
                            val intent = Intent(applicationContext, ProfileActivity::class.java)
                            intent.putExtra("username", response.body()?.username.toString())
                            intent.putExtra("phoneNumber", response.body()?.phoneNumber.toString())
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.d("Test response", t.message.toString())
                    }
                })
        })
    }
    companion object {
        private fun getHeaderMap(): Map<String, String> {
            val headerMap = mutableMapOf<String, String>()
            val token = LoginActivity.token
            headerMap["Authorization"] = token
            return headerMap
        }
    }
}