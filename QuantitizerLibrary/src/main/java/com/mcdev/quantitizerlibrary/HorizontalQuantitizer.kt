package com.mcdev.quantitizerlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.mcdev.quantitizerlibrary.databinding.HorizontalQuantitizerBinding


@SuppressLint("CustomViewStyleable")
class HorizontalQuantitizer @JvmOverloads constructor(context: Context,
                                                      attributeSet: AttributeSet? = null,
                                                      defStyle: Int = 0):
    ConstraintLayout(context, attributeSet, defStyle){

    private var listener :QuantitizerListener? = null
    private val translation = "translationX"
    private val binding = HorizontalQuantitizerBinding.inflate(LayoutInflater.from(context), this, true)
    private var currentValue: Int = 0

    private var _animationDuration = 300L
    private var _minValue:Int = 0
    private var _maxValue:Int? = null
    private var _animateButtons: Boolean = true
    private var _animationStyle: AnimationStyle = AnimationStyle.SWING

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

    init {
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

        /*decrease*/
        binding.decreaseIb.setOnClickListener {
            hideKeyboard()
            doDec()

            //listener
            listener?.activateOnDecrease(_animationDuration)
        }

        /*increase*/
        binding.increaseIb.setOnClickListener {
            hideKeyboard()
            doInc()

            //listener
            listener?.activateOnIncrease(_animationDuration)
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

        /*TypedArrays are heavyweight objects that should be recycled immediately
         after all the attributes you need have been extracted.*/
        a.recycle()
    }

    private fun wobble(view: View): View {
        val anim: Animation = TranslateAnimation(-20F, 20F, 0f, 0f)
        anim.duration = 50L
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = 3
        view.startAnimation(anim)
        return view
    }

    private fun doInc() {
        if (_animateButtons) {
            animatePlusButton()
        }


        if (currentValue >= maxValue!!) {
            //Do nothing
//            wobble(binding.quantityTv)
        } else {
            binding.quantityTv.isCursorVisible = false // hide cursor if it's visible
            val increasedValue: Int = currentValue.inc()
            currentValue = increasedValue
            animateInc()
        }
    }

    private fun doDec() {
        if (_animateButtons) {
            animateMinusButton()
        }

        if (currentValue <= minValue) {
            //Do nothing
//            wobble(binding.quantityTv)
        } else {
            binding.quantityTv.isCursorVisible = false  // hide cursor if it's visible
            val decreasedValue: Int = currentValue.dec()
            currentValue = decreasedValue
            animateDec()
        }
    }

    private fun animateInc() {
        //animate and set current value for edit text
        when (_animationStyle) {
            AnimationStyle.SLIDE_IN_REVERSE -> {
                binding.quantityTv.textAnimSlideInRTL(
                    translation_X,
                    -200f,
                    0f,
                    currentValue.toString(),
                    _animationDuration
                ) // text
            }
            AnimationStyle.SLIDE_IN -> {
                binding.quantityTv.textAnimSlideInLTR(
                    translation_X,
                    200f,
                    0f,
                    currentValue.toString(),
                    _animationDuration
                )
            }
            AnimationStyle.FALL_IN -> {
                binding.quantityTv.textAnimFallIn(
                    translation_Y,
                    60f,
                    0f,
                    currentValue.toString(),
                    _animationDuration
                )
            }
            else -> {
                binding.quantityTv.textAnimSwing(
                    translation_X,
                    200f,
                    0f,
                    currentValue.toString(),
                    _animationDuration
                )
            }
        }

    }

    private fun animateDec() {
        //animate and set current value for edit text
        when (_animationStyle) {
            AnimationStyle.SLIDE_IN_REVERSE -> {
                binding.quantityTv.textAnimSlideInRTL(
                    translation_X,
                    200f,
                    0f,
                    currentValue.toString(),
                    _animationDuration
                ) // text
            }
            AnimationStyle.SLIDE_IN -> {
                binding.quantityTv.textAnimSlideInLTR(
                    translation_X,
                    -200f,
                    0f,
                    currentValue.toString(),
                    _animationDuration
                )
            }
            AnimationStyle.FALL_IN -> {
                binding.quantityTv.textAnimFallIn(
                    translation_Y,
                    -60f,
                    0f,
                    currentValue.toString(),
                    _animationDuration
                )
            }
            else -> {
                binding.quantityTv.textAnimSwing(
                    translation_X,
                    -200f,
                    0f,
                    currentValue.toString(),
                    _animationDuration
                )
            }
        }
    }

    private fun animatePlusButton() {
        //enter animation
        binding.increaseIb.enterAnimationSwing( translation_X, 0f, 20f , _animationDuration) // view

        //exit animation
        binding.increaseIb.exitAnimationSwing( translation_X, 20f, 0f , _animationDuration) // view
    }

    private fun animateMinusButton() {
        //enter animation
        binding.decreaseIb.enterAnimationSwing( translation_X, 0f, -20f , _animationDuration) // view

        //exit animation
        binding.decreaseIb.exitAnimationSwing( translation_X, -20f, 0f , _animationDuration) // view
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

    fun setQuantitizerListener(listener : QuantitizerListener) {
        this.listener = listener
    }
}