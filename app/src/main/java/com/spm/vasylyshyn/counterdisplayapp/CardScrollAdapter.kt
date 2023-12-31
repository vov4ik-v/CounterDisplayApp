package com.spm.vasylyshyn.counterdisplayapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.spm.vasylyshyn.counterdisplayapp.MainFragment.Companion.uploadDevice
import java.lang.String
import kotlin.Int

class CardScrollAdapter internal constructor(context: Activity?, devices: List<Device>) :
    ArrayAdapter<Device?>(context!!, 0, devices as List<Device?>) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        @SuppressLint("ViewHolder") val listItemView = LayoutInflater.from(
            context
        ).inflate(
            R.layout.item_device, parent, false
        )
        val currentDevice = getItem(position)
        val nameDevice = listItemView.findViewById<TextView>(R.id.nameDeviceInItem)
        val serialNumber = listItemView.findViewById<TextView>(R.id.serialNumberDeviceOnItem)
        val typeDeviceImage = listItemView.findViewById<ImageView>(R.id.typeDeviceImage)
        serialNumber.visibility = View.INVISIBLE
        if (currentDevice != null) {
            if (currentDevice.typeDevice === TypeDevice.GAS) {
                typeDeviceImage.setImageResource(R.drawable.gas_type)
            } else {
                typeDeviceImage.setImageResource(R.drawable.water_type)
            }
            typeDeviceImage.maxHeight = 75
            typeDeviceImage.maxWidth = 75
            serialNumber.text = String.valueOf(currentDevice.serialNumber)
            nameDevice.text = currentDevice.address
        }
        val settingButton: ImageButton = listItemView.findViewById(R.id.settingButton)
        settingButton.setOnClickListener{
            val serialNumber:TextView = listItemView.findViewById(R.id.serialNumberDeviceOnItem)
            uploadDevice { devices ->
                for (i in devices) {
                    if (i.serialNumber === Integer.valueOf(serialNumber.text.toString())) {
                        Log.d("ok", String.valueOf(i.typeDevice))
                        val intent1 = Intent(context, SettingsDeviceActivity::class.java)
                        intent1.putExtra("serialNumber", i.serialNumber)
                        intent1.putExtra("price", i.price)
                        intent1.putExtra("counterType", i.typeDevice)
                        intent1.putExtra("cantoraName", i.cantoraName)
                        intent1.putExtra("address", i.address)
                        intent1.putExtra("frequency", i.frequency)
                        startActivity(context,intent1, Bundle())
                        break
                    }
                }
            }


        }
        return listItemView

    }
}