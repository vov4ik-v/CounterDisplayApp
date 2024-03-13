package com.spm.vasylyshyn.counterdisplayapp.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.spm.vasylyshyn.counterdisplayapp.API_URL_PATH
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.response.JWTTokenSuccessResponse
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
        val login_google_btn: Button = findViewById(R.id.google_login_btn)
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
                    .baseUrl(API_URL_PATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
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
        login_google_btn.setOnClickListener {
            val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // TODO: add .requestIdToken("MyToken")
                .requestEmail()
                .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
                Log.w("Error getting documents: ", exception)
            }
        }
    }

    companion object {
        var token:String = ""
    }
}