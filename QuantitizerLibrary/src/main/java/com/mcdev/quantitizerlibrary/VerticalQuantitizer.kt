package com.mcdev.quantitizerlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mcdev.quantitizerlibrary.databinding.VerticalQuantitizerBinding


@SuppressLint("CustomViewStyleable")
class VerticalQuantitizer @JvmOverloads constructor(context: Context,
                                                    attributeSet: AttributeSet? = null,
                                                    defStyle: Int = 0):
    ConstraintLayout(context, attributeSet, defStyle){

    private var listener :QuantitizerListener? = null
    private val binding = VerticalQuantitizerBinding.inflate(LayoutInflater.from(context), this, true)

    private var currentValue: Int = 0

    private var _animationDuration = 300L
    private var _minValue:Int = 0
    private var _maxValue:Int = Int.MAX_VALUE
    private var _animateButtons = true
    private var _animationStyle = AnimationStyle.SWING
    private var _isReadOnly: Boolean = false

    var minValue: Int
        get() = _minValue
        set(value,) {

            if (value >= currentValue) {
                binding.quantityTv.text =
                    Editable.Factory.getInstance().newEditable(value.toString())
                currentValue = value
                _minValue = value
            } else {
                _minValue = value
                currentValue = value
            }
        }

    var maxValue: Int
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

    var buttonAnimationEnabled: Boolean
        get() = _animateButtons
        set(value) {
            _animateButtons = value
        }

    var textAnimationStyle: AnimationStyle
        get() = _animationStyle
        set(value) {
            _animationStyle = value
        }

    var animationDuration: Long
        get() = _animationDuration
        set(value) {
            _animationDuration = value
        }

    var isReadOnly: Boolean
        get() = _isReadOnly
        set(value) {
            isReadOnly(value)
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
            hideKeyboard()

            
            if (minValue >= currentValue) {
                //do nothing
            } else {
                doDec()

                //listener
                listener?.activateOnDecrease(_animationDuration)
            }

        }

        /*increase*/
        binding.increaseIb.setOnClickListener {
            hideKeyboard()
            if (maxValue <= currentValue) {
                //do  nothing
            } else {
                doInc()


                //listener
                listener?.activateOnIncrease(_animationDuration)
            }

        }

        /*make edit text cursor visible when clicked*/
        binding.quantityTv.setOnClickListener {
            if (_isReadOnly.not()) {
                binding.quantityTv.isCursorVisible = true
            }
        }

        binding.quantityTv.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentValue = if (s.toString().isNotEmpty() || s.toString() != "") {
                    val value = Integer.parseInt(s.toString())
                    listener?.onValueChanged(value)
                    value
                }else{
                    0
                }
            }

            override fun afterTextChanged(s: Editable?) {
                val value = s.toString().toIntOrNull()
                if (s.toString().isEmpty()) {
                    //do nothing
                }else if (value!! < minValue) {
                    binding.quantityTv.text = Editable.Factory.getInstance().newEditable(minValue.toString())
                    currentValue = minValue
                    Toast.makeText(context, "Min value is $minValue", Toast.LENGTH_SHORT).show()
                }else if (value > maxValue) {
                    binding.quantityTv.text = Editable.Factory.getInstance().newEditable(minValue.toString())
                    currentValue = minValue
                    Toast.makeText(context, "Max value is $maxValue", Toast.LENGTH_SHORT).show()

                }
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

        binding.quantityTv.isCursorVisible = false
        val increasedValue: Int = currentValue.inc()
        currentValue = increasedValue
        animateInc()

    }

    private fun doDec() {

        binding.quantityTv.isCursorVisible = false
        val decreasedValue: Int = currentValue.dec()
        currentValue = decreasedValue
        animateDec()

    }

    private fun animateInc() {
        if (_animateButtons) {
            animatePlusButton()
        }

        //animate and set current value for edit text
        when (_animationStyle) {
            AnimationStyle.SLIDE_IN_REVERSE -> {
                binding.quantityTv.textAnimSlideInRTL( translation_Y, -150f, 0f, currentValue.toString(), _animationDuration) // text
            }
            AnimationStyle.SLIDE_IN -> {
                binding.quantityTv.textAnimSlideInLTR( translation_Y, 150f, 0f, currentValue.toString(), _animationDuration) // text

            }
            AnimationStyle.FALL_IN -> {
                binding.quantityTv.textAnimFallIn( translation_X, -60f, 0f, currentValue.toString(), _animationDuration) // text

            }
            else -> {
                binding.quantityTv.textAnimSwing( translation_Y, -150f, 0f, currentValue.toString(), _animationDuration) // text
            }
        }
    }

    private fun animateDec() {
        if (_animateButtons) {
            animateMinusButton()
        }

        //animate and set current value for edit text
        when (_animationStyle) {
            AnimationStyle.SLIDE_IN_REVERSE -> {
                binding.quantityTv.textAnimSlideInRTL( translation_Y, 150f, 0f, currentValue.toString(), _animationDuration ) // text

            }
            AnimationStyle.SLIDE_IN -> {
                binding.quantityTv.textAnimSlideInLTR( translation_Y, -150f, 0f, currentValue.toString() , _animationDuration) // text

            }
            AnimationStyle.FALL_IN -> {
                binding.quantityTv.textAnimFallIn( translation_X, 60f, 0f, currentValue.toString() , _animationDuration) // text

            }
            else -> {
                binding.quantityTv.textAnimSwing( translation_Y, 150f, 0f, currentValue.toString() , _animationDuration) // text
            }
        }
    }

    private fun animatePlusButton() {
        //enter animation
        binding.increaseIb.enterAnimationSwing( translation_Y, 0f, -20f , _animationDuration) // view

        //exit animation
        binding.increaseIb.exitAnimationSwing( translation_Y, -20f, 0f , _animationDuration) // view
    }

    private fun animateMinusButton() {
        //enter animation
        binding.decreaseIb.enterAnimationSwing( translation_Y, 0f, 20f , _animationDuration) // view

        //exit animation
        binding.decreaseIb.exitAnimationSwing( translation_Y, 20f, 0f , _animationDuration) // view
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
    fun changeTextSizeVertical(size: Float) {
        binding.quantityTv.textSize = size
    }
    fun changeShapeAndColorDecrease(color: Int, background: Int) {
        val drawable = ContextCompat.getDrawable(context, background)
        drawable?.let {
            it.setTint(color)
            binding.decreaseIb.background = it
        }
    }
    fun changeShapeAndColorIncrease(color: Int, background: Int) {
        val drawable = ContextCompat.getDrawable(context, background)
        drawable?.let {
            it.setTint(color)
            binding.increaseIb.background = it
        }
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

    fun setValueTextColor(@ColorRes colorRes: Int) {
        binding.quantityTv.setTextColor(resources.getColor(colorRes, context.theme))
    }

    fun setValueTextColor(colorString: String) {
        binding.quantityTv.setTextColor(Color.parseColor(colorString))
    }

    fun setValueTextColor(colors: ColorStateList) {
        binding.quantityTv.setTextColor(colors)
    }

    fun setValueTextColorInt(@ColorInt colorInt: Int) {
        binding.quantityTv.setTextColor(colorInt)
    }

    fun setPlusIcon(@DrawableRes icon: Int) {
        binding.increaseIb.setImageResource(icon)
    }

    fun setMinusIcon(@DrawableRes icon: Int) {
        binding.decreaseIb.setImageResource(icon)
    }

    private fun isReadOnly(isReadOnly: Boolean): Boolean {
        return if (isReadOnly) {//if user wants read only, then set edittext enabled to false
            binding.quantityTv.apply {
                isFocusableInTouchMode = false
                isCursorVisible = false
                inputType = InputType.TYPE_NULL
            }
            true
        } else {//else set enabled to true
            binding.quantityTv.apply {
                isFocusableInTouchMode = true
                isCursorVisible = true
            }
            false
        }
    }

    fun setQuantitizerListener(listener : QuantitizerListener) {
        this.listener = listener
    }
}