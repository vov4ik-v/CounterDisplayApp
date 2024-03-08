package com.spm.vasylyshyn.counterdisplayapp.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.spm.vasylyshyn.counterdisplayapp.response.JWTTokenSuccessResponse
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.URL_PATH
import com.spm.vasylyshyn.counterdisplayapp.response.LoginRequest
import com.spm.vasylyshyn.counterdisplayapp.service.AuthService
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
        val reg_btn:Button = findViewById(R.id.reg_btn)
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
                    .baseUrl(URL_PATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
//                val loginRequest:LoginRequest = LoginRequest("vasiliwin@gmail.com", "vova")
                val loginRequest:LoginRequest = LoginRequest(login.text.toString(), password.text.toString())
                val apiService = retrofit.create(AuthService::class.java)

                apiService.authenticateUser(loginRequest).enqueue(object : Callback<JWTTokenSuccessResponse> {
                    override fun onResponse(
                        call: Call<JWTTokenSuccessResponse>,
                        response: Response<JWTTokenSuccessResponse>
                    ) {
                        token = response.body()?.token.toString()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<JWTTokenSuccessResponse>, t: Throwable) {
                        Log.d("Test response", t.message.toString())
                    }
                })
            }
        }
        reg_btn.setOnClickListener{
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    companion object {
        var token:String = ""
    }
}