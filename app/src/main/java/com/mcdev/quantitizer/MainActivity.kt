package com.mcdev.quantitizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer

class MainActivity : AppCompatActivity() {
    private lateinit var hQ: HorizontalQuantitizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hQ = findViewById(R.id.h_q)

        hQ.apply {
            value = 0
            setPlusIconBackgroundColor("#FFFF00")
            setPlusIconColor(android.R.color.holo_green_light)
            setMinusIconBackgroundColor("#FFFF00")
            setValueBackgroundColor(android.R.color.holo_red_dark)
//            setIconWidthAndHeight(60, 60)
        }
    }
}