package com.spm.vasylyshyn.counterdisplayapp.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.spm.vasylyshyn.counterdisplayapp.R
import com.spm.vasylyshyn.counterdisplayapp.enity.Device

class LastDataOfDeviceAdapter(context: Activity, devices: ArrayList<Device>) : ArrayAdapter<Device>(
    context, 0, devices
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        @SuppressLint("ViewHolder") val listView = LayoutInflater.from(
            context
        ).inflate(
            R.layout.item_data, parent, false
        )
        val current = getItem(position)
        Log.d("CounterDisplayApp.LastDataOfDeviceAdapter", "CurrentView is: $current")
        val lineChart: LineChart = listView.findViewById(R.id.lineChartDeviceData)
        val description = Description()
        description.text = "Display count"
        description.setPosition(150f, 15f)
        lineChart.description = description
        lineChart.axisRight.setDrawLabels(false)
        val xValues = listOf("Січ", "Лют", "Бер", "Квіт", "Трав", "Черв", "Лип", "Серп", "Вер", "Жов", "Лист", "Груд")
        val xAxis: XAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
        xAxis.textSize = 9f
        xAxis.labelCount = 12
        xAxis.granularity = 1f

        val yAxis: YAxis = lineChart.axisLeft
        yAxis.axisMaximum = 7853000f
        yAxis.axisMaximum = 7857000f
        yAxis.axisLineWidth = 2f
        yAxis.axisLineColor = Color.BLACK
        yAxis.labelCount = 10

        val entries1 = listOf(
            Entry(0f, 7854212f),
            Entry(1f, 7854362f),
            Entry(2f, 7854522f),
            Entry(3f, 7854612f),
            Entry(4f, 7854782f),
            Entry(5f, 7854902f),
            Entry(6f, 7855000f),
            Entry(7f, 7855120f),
            Entry(8f, 7855235f),
            Entry(9f, 7855213f),
            Entry(10f, 7855289f),
            Entry(11f, 7855388f),
        )
        val entries2 = listOf(
            Entry(0f, 7854142f),
            Entry(1f, 7854281f),
            Entry(2f, 7854532f),
            Entry(3f, 7854751f),
            Entry(4f, 7854873f),
            Entry(5f, 7855041f),
            Entry(6f, 7855112f),
            Entry(7f, 7855343f),
            Entry(8f, 7855515f),
            Entry(9f, 7855713f),
            Entry(10f, 7855916f),
            Entry(11f, 7856142f),
        )
        val lineDataSet = LineDataSet(entries1, "WATER")
        lineDataSet.color = Color.BLACK
        lineDataSet.setDrawValues(false)
        val lineDataSet2 = LineDataSet(entries2, "GAS")
        lineDataSet2.setDrawValues(false)
        lineDataSet2.color = Color.RED

        val lineData = LineData(lineDataSet, lineDataSet2)
        lineChart.data = lineData
        lineChart.invalidate()
        return listView
    }
}