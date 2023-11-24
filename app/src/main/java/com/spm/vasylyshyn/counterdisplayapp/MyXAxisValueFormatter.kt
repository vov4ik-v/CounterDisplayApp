package com.spm.vasylyshyn.counterdisplayapp

import com.github.mikephil.charting.components.AxisBase

import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.DecimalFormat


class MyXAxisValueFormatter : IndexAxisValueFormatter() {
    private var mFormat: DecimalFormat? = null
    fun MyAxisValueFormatter() {

        // format values to 1 decimal digit
        mFormat = DecimalFormat("dd.MM.yyyy, HH:mm")
    }

    override fun getFormattedValue(value: Float, axis: AxisBase): String {
        // "value" represents the position of the label on the axis (x or y)
        return mFormat!!.format(value) + " $"
    }

    val decimalDigits: Int
        /** this is only needed if numbers are returned, else return 0  */
        get() = 1
}