package com.spm.vasylyshyn.counterdisplayapp

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Arrays
import java.util.Date

class LastDataOfDeviceAdapter(context: Activity?, devices: ArrayList<Device>) : ArrayAdapter<Device>(
    context!!,0,devices){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        @SuppressLint("ViewHolder") val listView = LayoutInflater.from(
            context
        ).inflate(
            R.layout.item_data, parent, false
        )
        val current = getItem(position)
        print(current)
        val lineChart: LineChart = listView.findViewById(R.id.lineChartDeviceData)
        val description:Description = Description()
        description.text = "Student Record"
        description.setPosition(150f,15f)
        lineChart.description = description
        lineChart.axisRight.setDrawLabels(false)
        val xValues = listOf("Kamal","June","July","Jerry")
        val xAxis:XAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
        xAxis.labelCount = 4
        xAxis.granularity = 1f

        val yAxis:YAxis = lineChart.axisLeft
        yAxis.axisMaximum = 0f
        yAxis.axisMaximum = 100f
        yAxis.axisLineWidth = 2f
        yAxis.axisLineColor = Color.BLACK
        yAxis.labelCount = 10

        val entries1:ArrayList<Entry> = ArrayList<Entry>()
        entries1.add(Entry(0f,10f))
        entries1.add(Entry(1f,10f))
        entries1.add(Entry(2f,15f))
        entries1.add(Entry(3f,45f))
        val entries2:ArrayList<Entry> = ArrayList<Entry>()
        entries2.add(Entry(0f,5f))
        entries2.add(Entry(1f,15f))
        entries2.add(Entry(2f,25f))
        entries2.add(Entry(3f,30f))

        val lineDataSet = LineDataSet(entries1,"Maths")
        lineDataSet.color = Color.BLUE

        val lineDataSet2 = LineDataSet(entries2,"Science")
        lineDataSet.color = Color.RED

        val lineData = LineData(lineDataSet,lineDataSet2)
        lineChart.data = lineData
        lineChart.invalidate()
//        val listEntry: ArrayList<Entry> = ArrayList<Entry>()
////        val map: Map<Date,Int> = current?.listDisplayCounts!!
//        var k = 0f
//        val arrayLabels = ArrayList<String>()
//        val array = ArrayList<Date>()
////        for (i in map.keys) {
////            array.add(i)
////        }
//        arrayLabels.add("29/11/2018")
//        arrayLabels.add("30/11/2018")
//        arrayLabels.add("1/12/2018")
//        arrayLabels.add("2/12/2018")
//        arrayLabels.add("3/12/2018")
//        arrayLabels.add("4/12/2018")
//        arrayLabels.add("5/12/2018")
////        for (i in array) {
////            listEntry.add(Entry(i, (2.2+k).toFloat()))
////            k += 3
////        }
//        val dataSet = LineDataSet(listEntry, "gas")
//        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
//        dataSet.setDrawValues(true)
//        dataSet.setDrawFilled(true)
//        dataSet.fillAlpha = 170
////        if (current.typeDevice === TypeDevice.GAS) {
////            dataSet.fillColor = ContextCompat.getColor(context, R.color.gasChart)
////        } else {
////            dataSet.fillColor = ContextCompat.getColor(context, R.color.waterChart)
////        }
//        val lineData = LineData(dataSet)
//        val xAxis: XAxis = lineChart.xAxis
//        val yAxis: YAxis = lineChart.axisLeft
//        yAxis.gridColor = ContextCompat.getColor(context, R.color.white)
//        val rightAxis: YAxis = lineChart.axisRight
//        rightAxis.isEnabled = false
//        yAxis.textColor = ContextCompat.getColor(context, R.color.white)
//        xAxis.textColor = ContextCompat.getColor(context, R.color.white)
//        xAxis.position = XAxis.XAxisPosition.BOTTOM
//        xAxis.setDrawGridLines(false)
//        xAxis.labelRotationAngle = 45f
//        lineChart.description.isEnabled = false
//        lineChart.legend.isEnabled = false
//        lineChart.data = lineData
//        lineChart.invalidate()
        return listView
    }
}