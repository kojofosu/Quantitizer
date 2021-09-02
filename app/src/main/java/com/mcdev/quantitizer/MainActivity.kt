package com.mcdev.quantitizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer
import com.mcdev.quantitizerlibrary.NoValueQuantitizer
import com.mcdev.quantitizerlibrary.QuantitizerListener
import com.mcdev.quantitizerlibrary.VerticalQuantitizer

class MainActivity : AppCompatActivity() {
    private lateinit var hQ: NoValueQuantitizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hQ = findViewById(R.id.h_q)

        hQ.apply {
//            value = 0
//            setbackground(R.color.black)

            setPlusIcon(R.drawable.ic_angle_double_small_right)
            setMinusIcon(R.drawable.ic_angle_double_small_left)
            setPlusIconBackgroundColor(android.R.color.holo_blue_light)
            setMinusIconBackgroundColor(android.R.color.holo_blue_light)
            setValueBackgroundColor(android.R.color.holo_blue_light)
//            setValueTextColor("#FFFF00")
            setMinusIconColor("#FFFF00")
            setPlusIconColor("#FFFF00")

//            setIconWidthAndHeight(60, 60)
        }

        hQ.setQuantitizerListener(object: QuantitizerListener{
            override fun onIncrease() {
                Toast.makeText(this@MainActivity, "inc", Toast.LENGTH_SHORT).show()
            }

            override fun onDecrease() {
                Toast.makeText(this@MainActivity, "dec", Toast.LENGTH_SHORT).show()
            }
        })
    }
}