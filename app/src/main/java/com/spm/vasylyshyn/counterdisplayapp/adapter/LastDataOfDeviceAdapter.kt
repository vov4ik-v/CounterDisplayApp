package com.spm.vasylyshyn.counterdisplayapp.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
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
        description.text = "Display count"
        description.setPosition(150f,15f)
        lineChart.description = description
        lineChart.axisRight.setDrawLabels(false)
        val xValues = listOf("Січ","Лют","Бер","Квіт","Трав","Черв","Лип","Серп","Вер","Жов","Лист","Груд")
        val xAxis:XAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
        xAxis.textSize = 9f
        xAxis.labelCount = 12
        xAxis.granularity = 1f

        val yAxis:YAxis = lineChart.axisLeft
        yAxis.axisMaximum = 7853000f
        yAxis.axisMaximum = 7857000f
        yAxis.axisLineWidth = 2f
        yAxis.axisLineColor = Color.BLACK
        yAxis.labelCount = 10

        val entries1:ArrayList<Entry> = ArrayList<Entry>()
        entries1.add(Entry(0f,7854212f))
        entries1.add(Entry(1f,7854362f))
        entries1.add(Entry(2f,7854522f))
        entries1.add(Entry(3f,7854612f))
        entries1.add(Entry(4f,7854782f))
        entries1.add(Entry(5f,7854902f))
        entries1.add(Entry(6f,7855000f))
        entries1.add(Entry(7f,7855120f))
        entries1.add(Entry(8f,7855235f))
        entries1.add(Entry(9f,7855213f))
        entries1.add(Entry(10f,7855289f))
        entries1.add(Entry(11f,7855388f))
        val entries2:ArrayList<Entry> = ArrayList<Entry>()
        entries2.add(Entry(0f,7854142f))
        entries2.add(Entry(1f,7854281f))
        entries2.add(Entry(2f,7854532f))
        entries2.add(Entry(3f,7854751f))
        entries2.add(Entry(4f,7854873f))
        entries2.add(Entry(5f,7855041f))
        entries2.add(Entry(6f,7855112f))
        entries2.add(Entry(7f,7855343f))
        entries2.add(Entry(8f,7855515f))
        entries2.add(Entry(9f,7855713f))
        entries2.add(Entry(10f,7855916f))
        entries2.add(Entry(11f,7856142f))

        val lineDataSet = LineDataSet(entries1,"WATER")
        lineDataSet.color = Color.BLACK
        lineDataSet.setDrawValues(false)
        val lineDataSet2 = LineDataSet(entries2,"GAS")
        lineDataSet2.setDrawValues(false)
        lineDataSet2.color = Color.RED

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