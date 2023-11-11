package com.spm.vasylyshyn.counterdisplayapp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.spm.vasylyshyn.counterdisplayapp.JWTTokenSuccessResponse
import com.spm.vasylyshyn.counterdisplayapp.response.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val login_btn: Button = findViewById(R.id.login_btn)
        val password: EditText = findViewById(R.id.passwordEditText)
        val login: EditText = findViewById(R.id.logibEditText)
        login_btn.setOnClickListener {
            if (login.text.isEmpty() || password.text.isEmpty()) {
                val alertDialog = AlertDialog.Builder(this@LoginActivity).create()
                alertDialog.setTitle("Увага")
                alertDialog.setMessage("Введіть пошту і пароль.")
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, whichw -> dialog.dismiss() }
                alertDialog.show()
            } else {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.31.86:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val loginRequest:LoginRequest = LoginRequest(login.text.toString(), password.text.toString())
                val apiService = retrofit.create(AuthService::class.java)

                apiService.authenticateUser(loginRequest).enqueue(object : Callback<JWTTokenSuccessResponse> {
                    override fun onResponse(
                        call: Call<JWTTokenSuccessResponse>,
                        response: Response<JWTTokenSuccessResponse>
                    ) {
                        Log.d("Test response", response.body().toString())
                    }

                    override fun onFailure(call: Call<JWTTokenSuccessResponse>, t: Throwable) {
                        Log.d("Test response", t.message.toString())
                    }
                })
            }
        }
    }
}