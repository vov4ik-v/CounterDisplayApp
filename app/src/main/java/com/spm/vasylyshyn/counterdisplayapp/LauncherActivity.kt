package com.spm.vasylyshyn.counterdisplayapp

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                } catch (e: Exception) {
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