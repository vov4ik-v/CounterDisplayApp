package com.spm.vasylyshyn.counterdisplayapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        val constraintProfile: ConstraintLayout = findViewById(R.id.constraint_profile)
        val username:TextView = findViewById(R.id.tv_user_name)
        val devices:LinearLayout = findViewById(R.id.linear_devices)
        val notification:LinearLayout = findViewById(R.id.linear_notifications)
        val language:LinearLayout = findViewById(R.id.linear_language)
        val help:LinearLayout = findViewById(R.id.linear_help)
        val logout:LinearLayout = findViewById(R.id.linear_out)
        username.text = intent.getStringExtra("username")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val userId:Int = intent.getIntExtra("userId",0)

        constraintProfile.setOnClickListener(View.OnClickListener{
            val intent = Intent(applicationContext, EditUserInformationActivity::class.java)
            intent.putExtra("phoneNumber",phoneNumber)
            intent.putExtra("username",username.text)
            intent.putExtra("userId",userId)
            startActivity(intent)

        })
        devices.setOnClickListener(View.OnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        })
        notification.setOnClickListener(View.OnClickListener{
            val alertDialog = AlertDialog.Builder(this@ProfileActivity).create()
            alertDialog.setTitle("Увага")
            alertDialog.setMessage("Дана функція не доступна на даній версії додатку.")
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, which -> dialog.dismiss() }
            alertDialog.show()
        })
        language.setOnClickListener(View.OnClickListener{
            val alertDialog = AlertDialog.Builder(this@ProfileActivity).create()
            alertDialog.setTitle("Увага")
            alertDialog.setMessage("Дана функція не доступна на даній версії додатку.")
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, which -> dialog.dismiss() }
            alertDialog.show()
        })
        help.setOnClickListener(View.OnClickListener{
            val alertDialog = AlertDialog.Builder(this@ProfileActivity).create()
            alertDialog.setTitle("Увага")
            alertDialog.setMessage("Дана функція не доступна на даній версії додатку.")
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, which -> dialog.dismiss() }
            alertDialog.show()
        })
        logout.setOnClickListener(View.OnClickListener{
            LoginActivity.token = ""
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        })


//        login_btn.setOnClickListener {
//            if (login.text.isEmpty() || password.text.isEmpty()) {
//                val alertDialog = AlertDialog.Builder(this@ProfileActivity).create()
//                alertDialog.setTitle("Увага")
//                alertDialog.setMessage("Введіть пошту і пароль.")
//                alertDialog.setButton(
//                    AlertDialog.BUTTON_NEUTRAL, "OK"
//                ) { dialog, whichw -> dialog.dismiss() }
//                alertDialog.show()
//            } else {
//                val retrofit = Retrofit.Builder()
//                    .baseUrl("http://192.168.31.86:8080/api/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
////                val loginRequest:LoginRequest = LoginRequest("vasiliwin@gmail.com", "vova")
//                val loginRequest:LoginRequest = LoginRequest(login.text.toString(), password.text.toString())
//                val apiService = retrofit.create(AuthService::class.java)
//
//                apiService.authenticateUser(loginRequest).enqueue(object : Callback<JWTTokenSuccessResponse> {
//                    override fun onResponse(
//                        call: Call<JWTTokenSuccessResponse>,
//                        response: Response<JWTTokenSuccessResponse>
//                    ) {
//                        token = response.body()?.token.toString()
//                        val intent = Intent(applicationContext, MainActivity::class.java)
//                        startActivity(intent)
//                    }
//
//                    override fun onFailure(call: Call<JWTTokenSuccessResponse>, t: Throwable) {
//                        Log.d("Test response", t.message.toString())
//                    }
//                })
//            }
//        }
    }
}