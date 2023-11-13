package com.spm.vasylyshyn.counterdisplayapp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import it.sephiroth.android.library.widget.HListView
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment() : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val addDeviceButton: Button = view.findViewById(R.id.addDeviceButton)
        uploadDevice { devices ->
            val arrayAdapter = CardScrollAdapter(activity, devices as ArrayList<Device>)
            val horizontalListView = view.findViewById<HListView>(R.id.scroll_device)
            horizontalListView.adapter = arrayAdapter
            addDeviceButton.setOnClickListener {
                val alertDialog = AlertDialog.Builder(/* context = */ context).create()
                alertDialog.setTitle("Увага")
                alertDialog.setMessage("Дана функція не доступна на демо версії додатку.")
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, _ -> dialog.dismiss() }
                alertDialog.show()
            }
        }
        return view
    }

    fun fara() {

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        private val client = OkHttpClient()


//        fun uploadDevice(): ArrayList<Device> {
//            val retrofit = Retrofit.Builder()
//                .baseUrl("http://192.168.31.86:8080/api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//            val apiService = retrofit.create(UserService::class.java)
//            val arrayDevices: ArrayList<Device> = ArrayList();
//
//            apiService.getDevicesForCurrentUser().enqueue(object :
//                retrofit2.Callback<List<DeviceDto>> {
//                override fun onResponse(
//                    call: retrofit2.Call<List<DeviceDto>>,
//                    response: retrofit2.Response<List<DeviceDto>>
//                ) {
//                    for(el in response.body()!!) {
//                        arrayDevices.add(Device(TypeDevice.GAS,el.cantoraName,el.address,el.price,el.frequency,el.numberOfDevice))
//                    }
//                    Log.d("Test response", arrayDevices[0].address.toString())
//
//                }
//
//                override fun onFailure(call: retrofit2.Call<List<DeviceDto>>, t: Throwable) {
//                    Log.d("Test response", t.message.toString())
//                }
//            })
////            for (i in set) {
////                Log.d("ok", i.toString())
////                Log.d("ok", i.value.toString().substring(1, i.value.toString().length - 1))
////                map[i.key] = i.value.toString().substring(1, i.value.toString().length - 1).toInt()
////            }
//            for(ar in arrayDevices){
//                Log.d("el arr devices", ar.toString())
//
//            }
//            val arrayDevice: ArrayList<Device> = ArrayList()
//            arrayDevice.add(Device(TypeDevice.GAS,"Stree1", "Street11",200,2,421321))
//            arrayDevice.add(Device(TypeDevice.GAS,"Stree1", "Street11",200,2,421321))
//            arrayDevice.add(Device(TypeDevice.GAS,"Stree1", "Street11",200,2,421321))
//            return arrayDevice
//        }

        fun uploadDevice(callback: (List<Device>) -> Unit) {
            val gson = GsonBuilder()
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.10.10.8:8080/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val apiService = retrofit.create(UserService::class.java)

            apiService.getDevicesForCurrentUser().enqueue(object : Callback<List<DeviceDto>> {
                override fun onResponse(
                    call: Call<List<DeviceDto>>,
                    response: Response<List<DeviceDto>>
                ) {
                    if (response.isSuccessful) {
                        val arrayDevice: ArrayList<Device> = ArrayList()
                        for (el in response.body()!!) {
                            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm", Locale.ENGLISH)
                            val date = LocalDateTime.parse(el.createdDate, formatter)
                            val displayCounts:ArrayList<DisplayCount> = ArrayList()
                            val typeForDevice: TypeDevice = when (el.counterType) {
                                "GAS" -> {
                                    TypeDevice.GAS
                                }

                                "WATER" -> {
                                    TypeDevice.WATER
                                }

                                else -> {
                                    TypeDevice.LIGHT
                                }
                            }
                            for(displayCount in el.displayCounts){
                                val displayCountCreatedDate = LocalDateTime.parse(displayCount.createdDate,formatter)
                                val displayCountForDevice: DisplayCount = DisplayCount(displayCount.id, displayCount.displayCount,displayCountCreatedDate)
                                displayCounts.add(displayCountForDevice)
                            }
                            arrayDevice.add(
                                Device(
                                    typeForDevice,
                                    cantoraName = el.cantoraName,
                                    address = el.address,
                                    price = el.price,
                                    frequency = el.frequency,
                                    serialNumber = el.numberOfDevice,
                                    mapDisplayCounts = displayCounts,
                                    createdData = date
                                )
                            )
                        }
                        callback(arrayDevice)
                    }
                }

                override fun onFailure(call: Call<List<DeviceDto>>, t: Throwable) {
                    Log.d("Test response", t.message.toString())
                    callback(emptyList())
                }
            })
        }
    }

}