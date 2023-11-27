package com.spm.vasylyshyn.counterdisplayapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.spm.vasylyshyn.counterdisplayapp.JWTTokenSuccessResponse
import com.spm.vasylyshyn.counterdisplayapp.response.LoginRequest
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val reg_btn: Button = findViewById(R.id.reg_btn)
        val password: EditText = findViewById(R.id.passwordEditText)
        val confirmPassword: EditText = findViewById(R.id.confirmPasswordEditText)
        val firstname: EditText = findViewById(R.id.firstnameEditText)
        val lastname: EditText = findViewById(R.id.lastnameEditText)
        val username: EditText = findViewById(R.id.usernameEditText)
        val email: EditText = findViewById(R.id.emailEditText)
        reg_btn.setOnClickListener {
            if (email.text.isEmpty() || firstname.text.isEmpty() || lastname.text.isEmpty() || username.text.isEmpty() || confirmPassword.text.isEmpty() || password.text.isEmpty()) {
                val alertDialog = AlertDialog.Builder(this@RegisterActivity).create()
                alertDialog.setTitle("Увага")
                alertDialog.setMessage("Введіть  всі дані.")
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, whichw -> dialog.dismiss() }
                alertDialog.show()
            } else {
                val ip = IP.ip
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://${ip}:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val registerRequest:RegisterRequest = RegisterRequest(email.text.toString(),firstname.text.toString(),lastname.text.toString(),username.text.toString(), password.text.toString(),confirmPassword.text.toString())
                val apiService = retrofit.create(AuthService::class.java)

                apiService.registerUser(registerRequest).enqueue(object : Callback<RegisterSuccessResponse> {
                    override fun onResponse(
                        call: Call<RegisterSuccessResponse>,
                        response: Response<RegisterSuccessResponse>
                    ) {
                        if(response.code() == 200){
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<RegisterSuccessResponse>, t: Throwable) {
                        Log.d("Test response", t.message.toString())
                    }
                })
            }
        }
    }
}