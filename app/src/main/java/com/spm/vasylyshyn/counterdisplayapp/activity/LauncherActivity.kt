package com.spm.vasylyshyn.counterdisplayapp.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.spm.vasylyshyn.counterdisplayapp.R
import org.koin.android.ext.android.inject

class LauncherActivity : AppCompatActivity() {
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences.getString("token", null)?.let { LoginActivity.token = it }
        setContentView(R.layout.activity_launcher)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_launcher)
        supportActionBar?.hide()
        val welcomeThread: Thread = object : Thread() {
            override fun run() {
                try {
                    super.run()
                    sleep(1000)
                } catch (_: Exception) {
                } finally {
                    val i = Intent(
                        applicationContext,
                        MainActivity::class.java
                    )
                    startActivity(i)
                    finish()
                }
            }
        }
        welcomeThread.start()
    }
}