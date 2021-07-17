package com.mcdev.quantitizerlibrary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.mcdev.quantitizerlibrary.databinding.ActivityHorizontalQuantitizerBinding

class HorizontalQuantitizer @JvmOverloads constructor(context: Context,
                                                      attributeSet: AttributeSet? = null ,
                                                      defStyle: Int = 0):
    ConstraintLayout(context, attributeSet, defStyle){

    private val binding = ActivityHorizontalQuantitizerBinding.inflate(LayoutInflater.from(context), this, true)
    private var currentValue: Int = 0

    init {
        /*decrease*/
        binding.decreaseIb.setOnClickListener {
            doDec()
        }

        /*increase*/
        binding.increaseIb.setOnClickListener {
            binding.increaseIb.setOnClickListener {
                doInc()
            }
        }
    }

    private fun doInc() {
        val increasedValue: Int = currentValue.inc()
        binding.quantityTv.text = increasedValue.toString()
        currentValue = increasedValue
    }

    private fun doDec() {
        val decreasedValue: Int = currentValue.dec()
        binding.quantityTv.text = decreasedValue.toString()
        currentValue = decreasedValue
    }

}