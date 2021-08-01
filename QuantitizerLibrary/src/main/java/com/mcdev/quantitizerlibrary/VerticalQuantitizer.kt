package com.mcdev.quantitizerlibrary

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.constraintlayout.widget.ConstraintLayout
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator
import com.mcdev.quantitizerlibrary.databinding.VerticalQuantitizerBinding


@SuppressLint("CustomViewStyleable")
class VerticalQuantitizer @JvmOverloads constructor(context: Context,
                                                    attributeSet: AttributeSet? = null,
                                                    defStyle: Int = 0):
    ConstraintLayout(context, attributeSet, defStyle){

    private val binding = VerticalQuantitizerBinding.inflate(LayoutInflater.from(context), this, true)
    private val translation = "translationY"
    private var currentValue: Int = 0

    private var _minValue:Int = 0
    private var _maxValue:Int? = null

    var minValue: Int
        get() = _minValue
        set(value) {
            _minValue = value
        }

    var maxValue: Int?
        get() = _maxValue
        set(value) {
            _maxValue = value
        }

    var value: Int
        get() = currentValue
        set(value) {
            currentValue = value
            binding.quantityTv.text = Editable.Factory.getInstance().newEditable(value.toString())
        }

    init {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attributeSet, R.styleable.Quantitizer, defStyle, 0
        )

        minValue = a.getInteger(
            R.styleable.Quantitizer_minValue, 0
        )

        maxValue = a.getInteger(
            R.styleable.Quantitizer_maxValue, Int.MAX_VALUE
        )

        value = a.getInteger(
            R.styleable.Quantitizer_value, 0
        )

        /*TypedArrays are heavyweight objects that should be recycled immediately
         after all the attributes you need have been extracted.*/
        a.recycle()


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

        binding.quantityTv.addTextChangedListener(object: TextWatcher {
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


    private fun wobble(view: View): View {
        val anim: Animation = TranslateAnimation(-0f, 0f, -20f, 20f)
        anim.duration = 50L
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = 3
        view.startAnimation(anim)
        return view
    }

    private fun doInc() {
        if (currentValue >= maxValue!!) {
            wobble(binding.quantityTv)
        } else {
            binding.quantityTv.isCursorVisible = false
            animateInc()
            val increasedValue: Int = currentValue.inc()
            currentValue = increasedValue
            animateNextInc()
        }
    }

    private fun doDec() {
        if (currentValue <= minValue) {
            wobble(binding.quantityTv)
        } else {
            binding.quantityTv.isCursorVisible = false
            animateDec()
            val decreasedValue: Int = currentValue.dec()
            currentValue = decreasedValue
            animateNextDec()
        }
    }

    private fun animateInc() {
        val animator = ObjectAnimator.ofFloat(binding.quantityTv, translation, 0f, -200f)
        animator.interpolator = EasingInterpolator(Ease.BACK_IN)
        animator.start()

        val animator2 = ObjectAnimator.ofFloat(binding.increaseIb, translation, 0f, -20f)
        animator2.interpolator = EasingInterpolator(Ease.BACK_IN)
        animator2.start()
    }

    private fun animateNextInc() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                binding.quantityTv.text = Editable.Factory.getInstance().newEditable(currentValue.toString())

                val animator = ObjectAnimator.ofFloat(binding.quantityTv, translation, -200f, 0f)
                animator.interpolator = EasingInterpolator(Ease.BACK_OUT)
                animator.start()

                val animator2 = ObjectAnimator.ofFloat(binding.increaseIb, translation, -20f, 0f)
                animator2.interpolator = EasingInterpolator(Ease.BACK_OUT)
                animator2.start()
            }, DURATION
        )
    }

    private fun animateDec() {
        val animator = ObjectAnimator.ofFloat(binding.quantityTv, translation, 0f, 200f)
        animator.interpolator = EasingInterpolator(Ease.BACK_IN)
        animator.start()

        val animator2 = ObjectAnimator.ofFloat(binding.decreaseIb, translation, 0f, 20f)
        animator2.interpolator = EasingInterpolator(Ease.BACK_IN)
        animator2.start()
    }

    private fun animateNextDec() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                binding.quantityTv.text = Editable.Factory.getInstance().newEditable(currentValue.toString())

                val animator = ObjectAnimator.ofFloat(binding.quantityTv, translation, 200f, 0f)
                animator.interpolator = EasingInterpolator(Ease.BACK_OUT)
                animator.start()

                val animator2 = ObjectAnimator.ofFloat(binding.decreaseIb, translation, 20f, 0f)
                animator2.interpolator = EasingInterpolator(Ease.BACK_OUT)
                animator2.start()
            }, DURATION
        )
    }

    fun setIconWidthAndHeight(width: Int, height: Int) {
        val density = Resources.getSystem().displayMetrics.density

        binding.decreaseIb.requestLayout()
        binding.decreaseIb.layoutParams.width = width * density.toInt()
        binding.decreaseIb.layoutParams.height = height * density.toInt()

        binding.increaseIb.requestLayout()
        binding.increaseIb.layoutParams.width  = width * density.toInt()
        binding.increaseIb.layoutParams.height  = height * density.toInt()
    }

    companion object {
        private const val DURATION = 300L
    }

}