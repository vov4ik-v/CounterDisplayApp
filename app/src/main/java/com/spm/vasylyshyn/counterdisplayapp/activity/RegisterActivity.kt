package com.spm.vasylyshyn.counterdisplayapp.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterSuccessResponse
import com.spm.vasylyshyn.counterdisplayapp.response.RegisterRequest
import com.spm.vasylyshyn.counterdisplayapp.service.AuthService
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {
    private val apiService: AuthService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val regBtn: Button = findViewById(R.id.reg_btn)
        val password: EditText = findViewById(R.id.passwordEditText)
        val confirmPassword: EditText = findViewById(R.id.confirmPasswordEditText)
        val username: EditText = findViewById(R.id.usernameEditText)
        val email: EditText = findViewById(R.id.emailEditText)
        regBtn.setOnClickListener {
            if (email.text.isEmpty()
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
                    password.text.toString(),
                    username.text.toString(),
                )

                lifecycleScope.launch {
                    val result: Result<RegisterSuccessResponse> = apiService.registerUser(registerRequest)
                    result.fold(
                        onSuccess = {
                            Log.i("CounterDisplayApp.RegisterActivity", "registerUser successful: ${it.message}")
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                        },
                        onFailure = {
                            Log.e("CounterDisplayApp.RegisterActivity", "registerUser failed with message: ${it.message.toString()}")
                        }
                    )
                }
            }
        }
    }
}