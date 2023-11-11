package com.spm.vasylyshyn.counterdisplayapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsDeviceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_device)
        val intent: Intent = intent
        val periodType: Spinner = findViewById(R.id.spinnerTypePeriod)
        val buttonCalibrateDevice : Button = findViewById(R.id.buttonCalibrateDevice)
        val arrayPeriodType = arrayOf("Годин", "Днів", "Тижнів", "Місяць")
        val adapterSpinner =
            ArrayAdapter<String>(applicationContext, R.layout.spinner_item, arrayPeriodType)
        periodType.adapter = adapterSpinner
        val serialNumberDevice = intent.getIntExtra("serialNumber", 0)
        if (serialNumberDevice != 0) {
            val editPeriod: EditText = findViewById(R.id.numberPeriod)
            editPeriod.setText("5")
            val typeDevice: TextView = findViewById(R.id.settingTypeDevice)
            typeDevice.text = serialNumberDevice.toString()
            val textAddress = "ADDRESS"
            val editAddress: EditText = findViewById(R.id.editAdressDevice)
            editAddress.setText(textAddress)
        }
//        buttonCalibrateDevice.setOnClickListener(View.OnClickListener {
//            val intent = Intent(applicationContext, CalibratingActivity::class.java)
//            startActivity(intent)
//        })
    }
}