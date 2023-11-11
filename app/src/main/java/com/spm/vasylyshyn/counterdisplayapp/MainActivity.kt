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
import com.spm.vasylyshyn.counterdisplayapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(MainFragment())
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
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            // place your clicking handle code here.
        })


    }

//    fun onClick(view: View) {
//        try {
//            when (view.id) {
//                R.id.profileIcon -> {
//                    val intent = Intent(applicationContext, AuthActivity::class.java)
//                    startActivity(intent)
//                }
//
//                R.id.settingButton -> {
//                    val parrent = view.parent as View
//                    val serialNumber = parrent.findViewById<TextView>(R.id.serialNumberDeviceOnItem)
//                    for (i in MainFragment.uploadDevice()) {
//                        println(i)
//                        if (i.serialNumber === Integer.valueOf(serialNumber.text.toString())) {
//                            Log.d("ok", String.valueOf(i.typeDevice))
//                            val intent1 = Intent(
//                                applicationContext,
//                                SettingsDeviceActivity::class.java
//                            )
//                            intent1.putExtra("serialNumber", i.serialNumber)
//                            startActivity(intent1)
//                            break
//                        }
//                    }
//                }
//
//                R.id.addDeviceButton -> {
//                    val intent = Intent(applicationContext, AuthActivity::class.java)
//                    startActivity(intent)
//                }
//            }
//        } catch (e: java.lang.IllegalStateException) {
//            Log.e("exception", e.toString())
//        }
//    }


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

