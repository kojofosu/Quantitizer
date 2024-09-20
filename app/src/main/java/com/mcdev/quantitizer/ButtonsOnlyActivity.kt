package com.mcdev.quantitizer

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mcdev.quantitizerlibrary.*

class ButtonsOnlyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buttons_only)

        val hq = findViewById<HorizontalQuantitizer>(R.id.h_quant)
        val vq = findViewById<VerticalQuantitizer>(R.id.v_qaunt)
        val nvq = findViewById<NoValueQuantitizer>(R.id.nv_quant)

        hq.apply {
//            value = 1
            buttonAnimationEnabled = false
            textAnimationStyle = AnimationStyle.SLIDE_IN
            animationDuration = 400L
            isReadOnly = true

            minValue = 3
            maxValue = 7
            setQuantitizerListener(object : QuantitizerListener{
                override fun onIncrease() {
                    Toast.makeText(this@ButtonsOnlyActivity, "inc", Toast.LENGTH_SHORT).show()
                }
                override fun onDecrease() {
                    Toast.makeText(this@ButtonsOnlyActivity, "desc", Toast.LENGTH_SHORT).show()
                }

                override fun onValueChanged(value: Int) {
                    Toast.makeText(this@ButtonsOnlyActivity, "value changed to : $value", Toast.LENGTH_SHORT).show()
                }

            })
            changeShapeAndColorDecrease(Color.parseColor("#FF4500"), R.drawable.heart_shape) // Orange Red
            changeShapeAndColorIncrease(Color.parseColor("#1E90FF"), R.drawable.shape_x) // Dodger Blue
//            setPlusIconBackgroundColor("#C19A6B")
//            setMinusIconBackgroundColor("#C19A6B")
//            setMinusIconColor("#ffffff")
//            setPlusIconColor("#ffffff")
//            setValueBackgroundColor("#47716E")
//            setValueTextColor("#FFFFFF")
        }

        vq.apply {
            value = 1
            buttonAnimationEnabled = false
            textAnimationStyle = AnimationStyle.FALL_IN
            isReadOnly = false
            minValue = 5
            maxValue = 8

            setQuantitizerListener(object : QuantitizerListener {
                override fun onIncrease() {
                    Toast.makeText(this@ButtonsOnlyActivity, "inc", Toast.LENGTH_SHORT).show()
                }

                override fun onDecrease() {
                    Toast.makeText(this@ButtonsOnlyActivity, "desc", Toast.LENGTH_SHORT).show()
                }

                override fun onValueChanged(value: Int) {
                    Toast.makeText(
                        this@ButtonsOnlyActivity,
                        "value changed to : $value",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
            changeShapeAndColorDecrease(Color.parseColor("#32CD32"), R.drawable.circular_background) // Lime Green
            changeShapeAndColorIncrease(Color.parseColor("#FFD700"), R.drawable.shape_y) // Gold
//            setPlusIconBackgroundColor("#C19A6B")
//            setMinusIconBackgroundColor("#C19A6B")
//            setMinusIconColor("#ffffff")
//            setPlusIconColor("#ffffff")
//            setValueBackgroundColor("#47716E")
//            setValueTextColor("#FFFFFF")
        }

        nvq.apply {
            changeShapeAndColorDecrease(Color.parseColor("#FF69B4"), R.drawable.circular_background)
            changeShapeAndColorIncrease(Color.parseColor("#00CED1"), R.drawable.circular_background)
            buttonAnimationEnabled = false
//            setPlusIconBackgroundColor("#C19A6B")
//            setMinusIconBackgroundColor("#C19A6B")
//            setMinusIconColor("#ffffff")
//            setPlusIconColor("#ffffff")
//            setValueBackgroundColor("#47716E")
//            setValueTextColor("#FFFFFF")
        }
    }
}