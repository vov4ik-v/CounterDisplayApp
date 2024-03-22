package com.spm.vasylyshyn.counterdisplayapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.spm.vasylyshyn.counterdisplayapp.enity.Device
import com.spm.vasylyshyn.counterdisplayapp.enity.DisplayCount
import com.spm.vasylyshyn.counterdisplayapp.adapter.LastDataOfDeviceAdapter
import com.spm.vasylyshyn.counterdisplayapp.activity.MainActivity
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.enums.TypeDevice
import it.sephiroth.android.library.widget.HListView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ExecutionException

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class StatisticsFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var arrayDevice = ArrayList<Device>()

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
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        horizontalLIstOfLastData = view.findViewById<View>(R.id.lastDataOfDevice) as HListView
        val chooseAdress = view.findViewById<Spinner>(R.id.chooseAdress)
        chooseAdress.prompt = "Виберіть адресу"
        val arrayAdress = arrayOf("Стрийська 81/17", "Стрийська 115", "Гашека 13")
        val adapterSpinner = ArrayAdapter(requireActivity(), R.layout.spinner_item, arrayAdress)
        chooseAdress.adapter = adapterSpinner
        if ((activity as MainActivity?)?.isOnline(requireContext()) == true) {
            getData()
        }
        val arrayAdapter: ArrayAdapter<*> = LastDataOfDeviceAdapter(requireActivity(), arrayDevice)
        horizontalLIstOfLastData?.adapter = arrayAdapter
        return view
    }

    private var horizontalLIstOfLastData: HListView? = null

    private fun getData() {
        try {
            val device = Device(TypeDevice.GAS, "OBL", "Striska 12", 56)
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val counts: List<DisplayCount> = listOf(
                DisplayCount(1, 123, LocalDateTime.parse("2023-11-12T21:28:00", formatter)),
                DisplayCount(2, 167, LocalDateTime.parse("2023-11-13T21:28:00", formatter)),
                DisplayCount(3, 189, LocalDateTime.parse("2023-11-14T21:28:00", formatter))
            )
            device.listDisplayCounts = counts
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