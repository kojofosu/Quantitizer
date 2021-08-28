package com.mcdev.quantitizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer
import com.mcdev.quantitizerlibrary.VerticalQuantitizer

class MainActivity : AppCompatActivity() {
    private lateinit var hQ: HorizontalQuantitizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hQ = findViewById(R.id.h_q)

        hQ.apply {
            value = 0
//            setPlusIcon(R.drawable.ic_angle_double_small_right)
//            setMinusIcon(R.drawable.ic_angle_double_small_left)
//            setPlusIconBackgroundColor(android.R.color.holo_red_dark)
//            setMinusIconBackgroundColor(android.R.color.holo_red_dark)
//            setValueBackgroundColor(android.R.color.holo_red_dark)
//            setValueTextColor("#FFFF00")
//            setMinusIconColor("#FFFF00")
//            setPlusIconColor("#FFFF00")

//            setIconWidthAndHeight(60, 60)
        }
    }
}