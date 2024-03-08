package com.spm.vasylyshyn.counterdisplayapp.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.spm.vasylyshyn.counterdisplayapp.R

class CalibratingActivity : AppCompatActivity(), OnTouchListener {
    var relativeLayout: RelativeLayout? = null
    var msg = "ok"
    var context: Context? = null
    var dp = 0f
    var layoutParams: RelativeLayout.LayoutParams? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = applicationContext
        dp = context!!.resources.displayMetrics.density
        val zoneCalibrating: CardView = findViewById(R.id.zoneCalibrating)
        setContentView(R.layout.activity_calibrating)
        val imageView: ImageView = findViewById(R.id.calibrateImage)
        val options = BitmapFactory.Options()
        options.inSampleSize = 8
        relativeLayout = findViewById(R.id.relativeLayout)
        imageView.setImageBitmap(
            BitmapFactory.decodeResource(
                this.resources,
                R.drawable.test_calibrate_min,
                options
            )
        )
        val firstPoint: CardView = findViewById(R.id.firstPoint)
        val secondPoint: CardView = findViewById(R.id.secondPoint)
        val thirdPoint: CardView = findViewById(R.id.thirdPoint)
        val fourthPoint: CardView = findViewById(R.id.forthPoint)
        secondPoint.setOnTouchListener(this)
        thirdPoint.setOnTouchListener(this)
        fourthPoint.setOnTouchListener(this)
        firstPoint.setOnTouchListener(this)
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val zoneCalibrating :CardView = findViewById(R.id.zoneCalibrating)
        val firstPoint: CardView = findViewById(R.id.firstPoint)
        val secondPoint: CardView = findViewById(R.id.secondPoint)
        val thirdPoint: CardView = findViewById(R.id.thirdPoint)
        val fourthPoint: CardView = findViewById(R.id.forthPoint)
        Log.d("ok", motionEvent.orientation.toString())
        if (motionEvent.action == 2) {
            when (view.id) {
                R.id.firstPoint -> {
                    zoneCalibrating.x = motionEvent.rawX + (7.5 * dp).toFloat()
                    zoneCalibrating.y = motionEvent.rawY - (172.5 * dp).toFloat()
                    view.x = motionEvent.rawX
                    view.y = motionEvent.rawY - 180 * dp
                    secondPoint.x = motionEvent.rawX
                    thirdPoint.y = motionEvent.rawY - 180 * dp
                    zoneCalibrating.layoutParams = RelativeLayout.LayoutParams(
                        (thirdPoint.x - firstPoint.x).toInt(),
                        (secondPoint.y - firstPoint.y).toInt()
                    )
                }

                R.id.secondPoint -> {
                    view.x = motionEvent.rawX
                    view.y = motionEvent.rawY - 180 * dp
                    firstPoint.x = motionEvent.rawX
                    zoneCalibrating.x = firstPoint.x + (7.5 * dp).toFloat()
                    zoneCalibrating.y = firstPoint.y + (7.5 * dp).toFloat()
                    fourthPoint.y = motionEvent.rawY - 180 * dp
                    zoneCalibrating.layoutParams = RelativeLayout.LayoutParams(
                        (thirdPoint.x - firstPoint.x).toInt(),
                        (secondPoint.y - firstPoint.y).toInt()
                    )
                }

                R.id.thirdPoint -> {
                    zoneCalibrating.y = motionEvent.rawY - (172.5 * dp).toFloat()
                    view.x = motionEvent.rawX
                    view.y = motionEvent.rawY - 180 * dp
                    fourthPoint.x = motionEvent.rawX
                    firstPoint.y = motionEvent.rawY - 180 * dp
                    zoneCalibrating.x = firstPoint.x + (7.5 * dp).toFloat()
                    zoneCalibrating.y = firstPoint.y + (7.5 * dp).toFloat()
                    zoneCalibrating.layoutParams = RelativeLayout.LayoutParams(
                        (thirdPoint.x - firstPoint.x).toInt(),
                        (secondPoint.y - firstPoint.y).toInt()
                    )
                }

                R.id.forthPoint -> {
                    zoneCalibrating.x = firstPoint.x + (7.5 * dp).toFloat()
                    zoneCalibrating.y = firstPoint.y + (7.5 * dp).toFloat()
                    view.x = motionEvent.rawX
                    view.y = motionEvent.rawY - 180 * dp
                    thirdPoint.x = motionEvent.rawX
                    secondPoint.y = motionEvent.rawY - 180 * dp
                    zoneCalibrating.layoutParams = RelativeLayout.LayoutParams(
                        (thirdPoint.x - firstPoint.x).toInt(),
                        (secondPoint.y - firstPoint.y).toInt()
                    )
                }
            }
        }
        if (motionEvent.action == 1) {
            when (view.id) {
                R.id.firstPoint -> {
                    view.x = motionEvent.rawX
                    view.y = motionEvent.rawY - 180 * dp
                    secondPoint.x = motionEvent.rawX
                    thirdPoint.y = motionEvent.rawY - 180 * dp
                }

                R.id.secondPoint -> {
                    view.x = motionEvent.rawX
                    view.y = motionEvent.rawY - 180 * dp
                    firstPoint.x = motionEvent.rawX
                    fourthPoint.y = motionEvent.rawY - 180 * dp
                }

                R.id.thirdPoint -> {
                    view.x = motionEvent.rawX
                    view.y = motionEvent.rawY - 180 * dp
                    fourthPoint.x = motionEvent.rawX
                    firstPoint.y = motionEvent.rawY - 180 * dp
                }

                R.id.forthPoint -> {
                    view.x = motionEvent.rawX
                    view.y = motionEvent.rawY - 180 * dp
                    thirdPoint.x = motionEvent.rawX
                    secondPoint.y = motionEvent.rawY - 180 * dp
                }
            }
        }
        Log.d("params", motionEvent.action.toString())
        return true
    }
}