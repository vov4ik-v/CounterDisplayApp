package com.spm.vasylyshyn.counterdisplayapp.activity

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.response.JWTTokenSuccessResponse
import com.spm.vasylyshyn.counterdisplayapp.response.LoginRequest
import com.spm.vasylyshyn.counterdisplayapp.service.AuthService
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val apiService: AuthService by inject()
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val loginBtn: Button = findViewById(R.id.login_btn)
        val regBtn: Button = findViewById(R.id.reg_btn)
        val loginGoogleBtn: Button = findViewById(R.id.google_login_btn)
        val password: EditText = findViewById(R.id.passwordEditText)
        val login: EditText = findViewById(R.id.logibEditText)
        loginBtn.setOnClickListener {
            if (login.text.isEmpty() || password.text.isEmpty()) {
                val alertDialog = AlertDialog.Builder(this@LoginActivity).create()
                alertDialog.setTitle("Увага")
                alertDialog.setMessage("Введіть пошту і пароль.")
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, _ -> dialog.dismiss() }
                alertDialog.show()
            } else {
                val loginRequest = LoginRequest(login.text.toString(), password.text.toString())

                apiService.authenticateUser(loginRequest).enqueue(object : Callback<JWTTokenSuccessResponse> {
                    override fun onResponse(
                        call: Call<JWTTokenSuccessResponse>,
                        response: Response<JWTTokenSuccessResponse>
                    ) {
                        val body = response.body()
                        if (body == null) {
                            Log.e("CounterDisplayApp.LoginActivity", "authenticateUser failed due to response.body() == null")
                            return
                        }

                        val edit = sharedPreferences.edit()
                        edit.putString("token", body.token)
                        edit.apply()
                        token = body.token
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        Log.i("CounterDisplayApp.LoginActivity", "authenticateUser successful")
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<JWTTokenSuccessResponse>, t: Throwable) {
                        Log.e("CounterDisplayApp.LoginActivity", "authenticateUser failed with message: ${t.message.toString()}")
                    }
                })
            }
        }
        regBtn.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }
        loginGoogleBtn.setOnClickListener {
            val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // TODO: add .requestIdToken("MyToken")
                .requestEmail()
                .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 9001)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        if (requestCode == 9001) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            task.addOnSuccessListener {
                // TODO: Perform the sign-in action
                val it1: GoogleSignInAccount = it
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener { exception ->
                Log.e("CounterDisplayApp.LoginActivity", "Error getting google sign in info: $exception")
            }
        }
    }

    companion object {
        var token: String = ""
    }
}