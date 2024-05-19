package com.spm.vasylyshyn.counterdisplayapp.fragment

import android.graphics.Color
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.activity.MainActivity
import com.spm.vasylyshyn.counterdisplayapp.adapter.LastDataOfDeviceAdapter
import com.spm.vasylyshyn.counterdisplayapp.enity.Device
import com.spm.vasylyshyn.counterdisplayapp.enity.DisplayCount
import com.spm.vasylyshyn.counterdisplayapp.enums.TypeDevice
import it.sephiroth.android.library.widget.HListView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ExecutionException

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class StatisticsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)

        // Setup ComposeView
        val composeView: ComposeView = view.findViewById(R.id.compose_view)
        composeView.setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LineChartScreen()
                }
            }
        }

        val horizontalLIstOfLastData: HListView? = view.findViewById(R.id.lastDataOfDevice)
        val chooseAdress = view.findViewById<Spinner>(R.id.chooseAdress)
        chooseAdress.prompt = "Виберіть адресу"
        val arrayAdress = arrayOf("Стрийська 81/17", "Стрийська 115", "Гашека 13")
        val adapterSpinner = ArrayAdapter(requireActivity(), R.layout.spinner_item, arrayAdress)
        chooseAdress.adapter = adapterSpinner
        if ((activity as MainActivity?)?.isOnline(requireContext()) == true) {
            getData()
        }
        val arrayAdapter: ArrayAdapter<*> = LastDataOfDeviceAdapter(requireActivity(), listOf())
        horizontalLIstOfLastData?.adapter = arrayAdapter
        return view
    }

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

    @Composable
    fun LineChartScreen() {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                LineChart(context).apply {
                    // Configure the chart here
                    setBackgroundColor(Color.BLACK)
                    description.isEnabled = false
                }
            },
            update = { chart ->
                val entriesThisYear = listOf(
                    Entry(0f, 15f), Entry(1f, 10f), Entry(2f, 5f), Entry(3f, 15f),
                    Entry(4f, 10f), Entry(5f, 20f), Entry(6f, 25f)
                )
                val entriesLastYear = listOf(
                    Entry(0f, 20f), Entry(1f, 15f), Entry(2f, 10f), Entry(3f, 15f),
                    Entry(4f, 10f), Entry(5f, 20f), Entry(6f, 22f)
                )

                val dataSetThisYear = LineDataSet(entriesThisYear, "Цього року").apply {
                    color = ColorTemplate.LIBERTY_COLORS[0]
                }
                val dataSetLastYear = LineDataSet(entriesLastYear, "Минулого року").apply {
                    color = ColorTemplate.JOYFUL_COLORS[0]
                }

                val lineData = LineData(dataSetThisYear, dataSetLastYear)
                chart.data = lineData
                chart.invalidate() // Refresh the chart
            }
        )
    }
}
