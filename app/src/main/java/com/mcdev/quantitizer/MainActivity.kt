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
            setIconWidthAndHeight(60, 60)
        }
    }
}