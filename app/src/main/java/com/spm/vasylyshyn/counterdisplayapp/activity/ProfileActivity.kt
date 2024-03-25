package com.spm.vasylyshyn.counterdisplayapp.activity

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.spm.vasylyshyn.counterdisplayapp.R
import org.koin.android.ext.android.inject

class ProfileActivity : AppCompatActivity() {
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        val constraintProfile: ConstraintLayout = findViewById(R.id.constraint_profile)
        val username: TextView = findViewById(R.id.tv_user_name)
        val devices: LinearLayout = findViewById(R.id.linear_devices)
        val notification: LinearLayout = findViewById(R.id.linear_notifications)
        val language: LinearLayout = findViewById(R.id.linear_language)
        val help: LinearLayout = findViewById(R.id.linear_help)
        val logout: LinearLayout = findViewById(R.id.linear_out)
        username.text = intent.getStringExtra("username")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val userId: Int = intent.getIntExtra("userId", 0)

        constraintProfile.setOnClickListener {
            val intent = Intent(applicationContext, EditUserInformationActivity::class.java)
            intent.putExtra("phoneNumber", phoneNumber)
            intent.putExtra("username", username.text)
            intent.putExtra("userId", userId)
            startActivity(intent)

        }
        devices.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
        notification.setOnClickListener { showFeatureIsNotAvailableAlert() }
        language.setOnClickListener { showFeatureIsNotAvailableAlert() }
        help.setOnClickListener { showFeatureIsNotAvailableAlert() }
        logout.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.remove("token")
            editor.apply()
            LoginActivity.token = ""
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showFeatureIsNotAvailableAlert() {
        val alertDialog = AlertDialog.Builder(this@ProfileActivity).create()
        alertDialog.setTitle("Увага")
        alertDialog.setMessage("Дана функція не доступна на даній версії додатку.")
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, which -> dialog.dismiss() }
        alertDialog.show()
    }
}