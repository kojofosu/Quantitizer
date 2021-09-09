package com.mcdev.quantitizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer
import com.mcdev.quantitizerlibrary.NoValueQuantitizer
import com.mcdev.quantitizerlibrary.QuantitizerListener
import com.mcdev.quantitizerlibrary.VerticalQuantitizer
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var hQ: HorizontalQuantitizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hQ = findViewById(R.id.quant)


        val priceTV = findViewById<TextView>(R.id.price_tv)

        val price = 417.86
        hQ.apply {
            value = 1
            buttonAnimationEnabled = true
//            setPlusIconBackgroundColor("#C19A6B")
//            setMinusIconBackgroundColor("#C19A6B")
//            setMinusIconColor("#ffffff")
//            setPlusIconColor("#ffffff")
//            setValueBackgroundColor("#47716E")
//            setValueTextColor("#FFFFFF")
        }

        hQ.setQuantitizerListener(object : QuantitizerListener {
            override fun onDecrease() {
                val quantity = hQ.value
                priceTV.setText("$${roundOffDecimal(price * quantity)}")
            }

            override fun onIncrease() {
                val quantity = hQ.value
                priceTV.setText("$${roundOffDecimal(price * quantity)}")

            }

        })
    }

    fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }
}