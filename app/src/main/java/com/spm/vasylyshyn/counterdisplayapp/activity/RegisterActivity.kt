package com.spm.vasylyshyn.counterdisplayapp.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterSuccessResponse
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterRequest
import com.spm.vasylyshyn.counterdisplayapp.service.AuthService
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private val apiService: AuthService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val regBtn: Button = findViewById(R.id.reg_btn)
        val password: EditText = findViewById(R.id.passwordEditText)
        val confirmPassword: EditText = findViewById(R.id.confirmPasswordEditText)
        val firstname: EditText = findViewById(R.id.firstnameEditText)
        val lastname: EditText = findViewById(R.id.lastnameEditText)
        val username: EditText = findViewById(R.id.usernameEditText)
        val email: EditText = findViewById(R.id.emailEditText)
        regBtn.setOnClickListener {
            if (email.text.isEmpty()
                || firstname.text.isEmpty()
                || lastname.text.isEmpty()
                || username.text.isEmpty()
                || confirmPassword.text.isEmpty()
                || password.text.isEmpty()
            ) {
                val alertDialog = AlertDialog.Builder(this@RegisterActivity).create()
                alertDialog.setTitle("Увага")
                alertDialog.setMessage("Введіть  всі дані.")
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, _ -> dialog.dismiss() }
                alertDialog.show()
            } else {
                val registerRequest = RegisterRequest(
                    email.text.toString(),
                    firstname.text.toString(),
                    lastname.text.toString(),
                    username.text.toString(),
                    password.text.toString(),
                    confirmPassword.text.toString()
                )

                apiService.registerUser(registerRequest).enqueue(object : Callback<RegisterSuccessResponse> {
                    override fun onResponse(
                        call: Call<RegisterSuccessResponse>,
                        response: Response<RegisterSuccessResponse>
                    ) {
                        if (response.code() == 200) {
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            Log.i("CounterDisplayApp.RegisterActivity", "registerUser successful}")
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<RegisterSuccessResponse>, t: Throwable) {
                        Log.e("CounterDisplayApp.RegisterActivity", "registerUser failed with message: ${t.message.toString()}")
                    }
                })
            }
        }
    }
}