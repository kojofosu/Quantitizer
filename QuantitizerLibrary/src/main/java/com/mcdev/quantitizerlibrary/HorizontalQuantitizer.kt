package com.mcdev.quantitizerlibrary

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator
import com.mcdev.quantitizerlibrary.databinding.ActivityHorizontalQuantitizerBinding
import java.util.*

class HorizontalQuantitizer @JvmOverloads constructor(context: Context,
                                                      attributeSet: AttributeSet? = null ,
                                                      defStyle: Int = 0):
    ConstraintLayout(context, attributeSet, defStyle){

    private val DURATION = 300L
    private val translation = "translationX"
    private val binding = ActivityHorizontalQuantitizerBinding.inflate(LayoutInflater.from(context), this, true)
    private var currentValue: Int = 0

    init {
        setMinValue(currentValue)

        /*decrease*/
        binding.decreaseIb.setOnClickListener {
            doDec()
        }

        /*increase*/
        binding.increaseIb.setOnClickListener {
            doInc()
        }
    }

    fun setMinValue(value: Int) {
        //todo enforce minimum value
        currentValue = value
        binding.quantityTv.text = value.toString()
    }
    private fun doInc() {
        animateInc()
        val increasedValue: Int = currentValue.inc()
        currentValue = increasedValue
        animateNextInc()
    }

    private fun doDec() {
        animateDec()
        val decreasedValue: Int = currentValue.dec()
        currentValue = decreasedValue
        animateNextDec()
    }

    private fun animateInc() {
        val animator = ObjectAnimator.ofFloat(binding.quantityTv, translation, 0f, 200f);
        animator.interpolator = EasingInterpolator(Ease.BACK_IN)
        animator.start()
    }

    private fun animateNextInc() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                binding.quantityTv.text = currentValue.toString()

                val animator = ObjectAnimator.ofFloat(binding.quantityTv, translation, 200f, 0f);
                animator.interpolator = EasingInterpolator(Ease.BACK_OUT)
                animator.start()
            }, DURATION)
    }

    private fun animateDec() {
        val animator = ObjectAnimator.ofFloat(binding.quantityTv, translation, 0f, -200f);
        animator.interpolator = EasingInterpolator(Ease.BACK_IN)
        animator.start()
    }

    private fun animateNextDec() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                binding.quantityTv.text = currentValue.toString()

                val animator = ObjectAnimator.ofFloat(binding.quantityTv, translation, -200f, 0f);
                animator.interpolator = EasingInterpolator(Ease.BACK_OUT)
                animator.start()
            }, DURATION)
    }

    fun setIconWidthAndHeight(width: Int, height: Int) {
        val density = Resources.getSystem().displayMetrics.density

        binding.decreaseIb.requestLayout()
        binding.decreaseIb.layoutParams.width = width * density.toInt()
        binding.decreaseIb.layoutParams.height = height * density.toInt()

        binding.increaseIb.requestLayout()
        binding.increaseIb.minimumWidth = width * density.toInt()
        binding.increaseIb.minimumHeight = height * density.toInt()
    }

    fun getSelectedValue(): Int {
        return currentValue
    }
}