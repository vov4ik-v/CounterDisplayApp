package com.spm.vasylyshyn.counterdisplayapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import com.spm.vasylyshyn.counterdisplayapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(MainFragment())
        var username: String;
        var phoneNumber: String;
        var userId: Int

        val profileIcon: ImageView = findViewById(R.id.profileIcon)
        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.bottom_navigation_main -> replaceFragment(MainFragment())
                R.id.bottom_navigation_statistics -> replaceFragment(StatisticsFragment())
                R.id.bottom_navigation_economy -> replaceFragment(AdviceFragment())


            }
            true

        }
        profileIcon.setOnClickListener(View.OnClickListener {
            if (LoginActivity.token.isNotEmpty()) {
                val gson = GsonBuilder()
                    .create()
                val ip = IP.ip
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://${ip}:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                val apiService = retrofit.create(UserService::class.java)

                apiService.getCurrentUser(MainActivity.getHeaderMap())
                    .enqueue(object : Callback<User> {
                        override fun onResponse(
                            call: Call<User>,
                            response: Response<User>
                        ) {
                            if (response.isSuccessful) {
                                username = response.body()!!.username.toString()
                                phoneNumber = response.body()!!.phoneNumber.toString()
                                userId = response.body()!!.id.toInt()
                                val intent = Intent(applicationContext, ProfileActivity::class.java)
                                intent.putExtra("username",username)
                                intent.putExtra("phoneNumber",phoneNumber)
                                intent.putExtra("userId",userId)

                                startActivity(intent)
                            }
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Log.d("Test response", t.message.toString())
                        }
                    })


            } else {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
            // place your clicking handle code here.
        })


    }

    companion object {
        private fun getHeaderMap(): Map<String, String> {
            val headerMap = mutableMapOf<String, String>()
            val token = LoginActivity.token
            headerMap["Authorization"] = token
            return headerMap
        }
    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentPlace, fragment)
        fragmentTransaction.commit()


    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}

