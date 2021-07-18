package com.mcdev.quantitizerlibrary

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator
import com.mcdev.quantitizerlibrary.databinding.ActivityHorizontalQuantitizerBinding

class HorizontalQuantitizer @JvmOverloads constructor(context: Context,
                                                      attributeSet: AttributeSet? = null ,
                                                      defStyle: Int = 0):
    ConstraintLayout(context, attributeSet, defStyle){

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

        /*make edit text cursor visible when clicked*/
        binding.quantityTv.setOnClickListener {
            binding.quantityTv.isCursorVisible = true
        }

        binding.quantityTv.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentValue = if (s.toString().isNotEmpty() || s.toString() != "") {
                    Integer.parseInt(s.toString())
                }else{
                    0
                }
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }

        })
    }

    fun setMinValue(value: Int) {
        //todo enforce minimum value
        currentValue = value
        binding.quantityTv.text = Editable.Factory.getInstance().newEditable(value.toString())
    }
    private fun doInc() {
        binding.quantityTv.isCursorVisible = false
        animateInc()
        val increasedValue: Int = currentValue.inc()
        currentValue = increasedValue
        animateNextInc()
    }

    private fun doDec() {
        binding.quantityTv.isCursorVisible = false
        animateDec()
        val decreasedValue: Int = currentValue.dec()
        currentValue = decreasedValue
        animateNextDec()
    }

    private fun animateInc() {
        val animator = ObjectAnimator.ofFloat(binding.quantityTv, translation, 0f, 200f)
        animator.interpolator = EasingInterpolator(Ease.BACK_IN)
        animator.start()
    }

    private fun animateNextInc() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                binding.quantityTv.text = Editable.Factory.getInstance().newEditable(currentValue.toString())

                val animator = ObjectAnimator.ofFloat(binding.quantityTv, translation, 200f, 0f)
                animator.interpolator = EasingInterpolator(Ease.BACK_OUT)
                animator.start()
            }, DURATION
        )
    }

    private fun animateDec() {
        val animator = ObjectAnimator.ofFloat(binding.quantityTv, translation, 0f, -200f)
        animator.interpolator = EasingInterpolator(Ease.BACK_IN)
        animator.start()
    }

    private fun animateNextDec() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                binding.quantityTv.text = Editable.Factory.getInstance().newEditable(currentValue.toString())

                val animator = ObjectAnimator.ofFloat(binding.quantityTv, translation, -200f, 0f)
                animator.interpolator = EasingInterpolator(Ease.BACK_OUT)
                animator.start()
            }, DURATION
        )
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

    companion object {
        private const val DURATION = 300L
    }
}