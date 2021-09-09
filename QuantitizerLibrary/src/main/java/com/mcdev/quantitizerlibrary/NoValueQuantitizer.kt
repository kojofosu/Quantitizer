package com.mcdev.quantitizerlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.mcdev.quantitizerlibrary.databinding.NoValueQuantitizerBinding

@SuppressLint("CustomViewStyleable")
class NoValueQuantitizer @JvmOverloads constructor(context: Context,
                                                   attributeSet: AttributeSet? = null,
                                                   defStyle: Int = 0):
    ConstraintLayout(context, attributeSet, defStyle){

    private var listener :QuantitizerListener? = null
    private val translation = "translationX"
    private val binding = NoValueQuantitizerBinding.inflate(LayoutInflater.from(context), this, true)

    private var _animationDuration = 300L
    private var _animateButtons = true

    var buttonAnimationEnabled: Boolean
        get() = _animateButtons
        set(value) {
            _animateButtons = value
        }

    var animationDuration: Long
        get() = _animationDuration
        set(value) {
            _animationDuration = value
        }

    init {
        val a = context.obtainStyledAttributes(
            attributeSet, R.styleable.Quantitizer, defStyle, 0
        )

        /*decrease*/
        binding.decreaseIb.setOnClickListener {
            hideKeyboard()
            animateDec()

            //listener
            listener?.activateOnDecrease(_animationDuration)
        }

        /*increase*/
        binding.increaseIb.setOnClickListener {
            hideKeyboard()
            animateInc()

            //listener
            listener?.activateOnIncrease(_animationDuration)
        }

        /*TypedArrays are heavyweight objects that should be recycled immediately
         after all the attributes you need have been extracted.*/
        a.recycle()
    }

    private fun animateInc() {
        if (_animateButtons) {
            animatePlusButton()
        }
    }

    private fun animateDec() {
        if (_animateButtons) {
            animateMinusButton()
        }
    }

    private fun animatePlusButton() {
        binding.increaseIb.enterAnimationSwing( translation, 0f, 20f, _animationDuration)
        binding.increaseIb.exitAnimationSwing( translation, 20f, 0f, _animationDuration)
    }

    private fun animateMinusButton() {
        binding.decreaseIb.enterAnimationSwing( translation, 0f, -20f, _animationDuration)
        binding.decreaseIb.exitAnimationSwing( translation, -20f, 0f, _animationDuration)
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

    fun setPlusIconBackgroundColor(@ColorRes colorRes: Int) {
        binding.increaseIb.backgroundTintList = resources.getColorStateList(colorRes, context.theme)
    }

    fun setPlusIconBackgroundColor(colorString: String) {
        binding.increaseIb.backgroundTintList = ColorStateList.valueOf(Color.parseColor(colorString))
    }

    fun setPlusIconBackgroundColor(colorStateList: ColorStateList) {
        binding.increaseIb.backgroundTintList = colorStateList
    }

    fun setPlusIconBackgroundColorInt(@ColorInt colorInt: Int) {
        binding.increaseIb.backgroundTintList = ColorStateList.valueOf(colorInt)
    }

    fun setMinusIconBackgroundColor(@ColorRes colorRes: Int) {
        binding.decreaseIb.backgroundTintList = resources.getColorStateList(colorRes, context.theme)
    }

    fun setMinusIconBackgroundColor(colorString: String) {
        binding.decreaseIb.backgroundTintList = ColorStateList.valueOf(Color.parseColor(colorString))
    }

    fun setMinusIconBackgroundColor(colorStateList: ColorStateList) {
        binding.decreaseIb.backgroundTintList = colorStateList
    }

    fun setMinusIconBackgroundColorInt(@ColorInt colorInt: Int) {
        binding.decreaseIb.backgroundTintList = ColorStateList.valueOf(colorInt)
    }

    fun setPlusIconColor(@ColorRes colorRes: Int) {
        binding.increaseIb.imageTintList = resources.getColorStateList(colorRes, context.theme)
    }

    fun setPlusIconColor(colorString: String) {
        binding.increaseIb.imageTintList = ColorStateList.valueOf(Color.parseColor(colorString))
    }

    fun setPlusIconColorInt(colorStateList: ColorStateList) {
        binding.increaseIb.imageTintList = colorStateList
    }

    fun setPlusIconColorInt(@ColorInt colorInt: Int) {
        binding.increaseIb.imageTintList = ColorStateList.valueOf(colorInt)
    }

    fun setMinusIconColor(@ColorRes colorRes: Int) {
        binding.decreaseIb.imageTintList = resources.getColorStateList(colorRes, context.theme)
    }

    fun setMinusIconColor(colorString: String) {
        binding.decreaseIb.imageTintList = ColorStateList.valueOf(Color.parseColor(colorString))
    }

    fun setMinusIconColor(colorStateList: ColorStateList) {
        binding.decreaseIb.imageTintList = colorStateList
    }

    fun setMinusIconColorInt(@ColorInt colorInt: Int) {
        binding.decreaseIb.imageTintList = ColorStateList.valueOf(colorInt)
    }

    fun setValueBackgroundColor(@ColorRes colorRes: Int) {
        binding.bgBg.backgroundTintList = resources.getColorStateList(colorRes, context.theme)
    }

    fun setValueBackgroundColor(colorString: String) {
        binding.bgBg.backgroundTintList = ColorStateList.valueOf(Color.parseColor(colorString))
    }

    fun setValueBackgroundColor(colorStateList: ColorStateList) {
        binding.bgBg.backgroundTintList = colorStateList
    }

    fun setValueBackgroundColorInt(@ColorInt colorInt: Int) {
        binding.bgBg.backgroundTintList = ColorStateList.valueOf(colorInt)
    }

    fun setPlusIcon(@DrawableRes icon: Int) {
        binding.increaseIb.setImageResource(icon)
    }

    fun setMinusIcon(@DrawableRes icon: Int) {
        binding.decreaseIb.setImageResource(icon)
    }

    fun setQuantitizerListener(listener : QuantitizerListener) {
        this.listener = listener
    }
}