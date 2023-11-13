package com.spm.vasylyshyn.counterdisplayapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import it.sephiroth.android.library.widget.HListView
import java.util.Date
import java.util.concurrent.ExecutionException

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class StatisticsFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var arrayDevice = ArrayList<Device>();

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
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        horizontalLIstOfLastData = view.findViewById<View>(R.id.lastDataOfDevice) as HListView
        val chooseAdress = view.findViewById<Spinner>(R.id.chooseAdress)
        chooseAdress.prompt = "Виберіть адресу"
        val arrayAdress = arrayOf("Стрийська 81/17", "Стрийська 115", "Гашека 13")
        val adapterSpinner = ArrayAdapter(requireActivity(), R.layout.spinner_item, arrayAdress)
        chooseAdress.adapter = adapterSpinner
        if ((activity as MainActivity?)!!.isOnline(requireContext())) {
            getData()
        }
        val arrayAdapter: ArrayAdapter<*> = LastDataOfDeviceAdapter(requireActivity(), arrayDevice)
        horizontalLIstOfLastData!!.adapter = arrayAdapter
        return view
    }
    private var horizontalLIstOfLastData: HListView? = null


    fun getData() {
        try {
            val map: MutableMap<Date, Int> = HashMap()
//            val jsonObject: JsonObject =
//                GetJson().AsJSONObject("http://imet.pythonanywhere.com/get_data?email=alex@gmail.com&type=gas&counter=3663434534&week=week")
//            val set: Set<Map.Entry<String, com.google.gson.JsonElement>> = jsonObject.entrySet()
//            for (i in set) {
//                Log.d("ok", i.toString())
//                Log.d("ok", i.value.toString().substring(1, i.value.toString().length - 1))
//                map[i.key] = i.value.toString().substring(1, i.value.toString().length - 1).toInt()
//            }
//            map["12.10.2002"] = 123
//            map["2"] = 223
//            map["3"] = 323
//            map["4"] = 423
            val device = Device(TypeDevice.GAS, "Striska 12", 56)
//            device.listDisplayCounts = map // edit
            arrayDevice.add(device)
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            Toast.makeText(activity, "no internet", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    
}