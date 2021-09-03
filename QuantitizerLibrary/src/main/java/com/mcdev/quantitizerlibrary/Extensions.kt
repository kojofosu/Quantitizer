package com.mcdev.quantitizerlibrary

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator

const val DURATION = 300L
const val translation_X = "translationX"
const val translation_Y = "translationY"

/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View.hideKeyboard(): Boolean {
    return try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
        false
    }
}


fun View.enterAnimation(translation: String, startPosition: Float, endPosition: Float) {
    val animator2 = ObjectAnimator.ofFloat(this, translation, startPosition, endPosition)
    animator2.interpolator = EasingInterpolator(Ease.BACK_IN)
    animator2.start()
}

fun View.exitAnimation( translation: String, startPosition: Float, endPosition: Float) {
    Handler(Looper.getMainLooper()).postDelayed(
        {
            val animator2 = ObjectAnimator.ofFloat(this, translation, startPosition, endPosition)
            animator2.interpolator = EasingInterpolator(Ease.BACK_OUT)
            animator2.start()
        }, DURATION
    )
}

fun QuantitizerListener.activateOnIncrease(){
   Handler(Looper.getMainLooper()).postDelayed(
        {
            this.onIncrease()
        }, DURATION
    )
}

fun QuantitizerListener.activateOnDecrease(){
    Handler(Looper.getMainLooper()).postDelayed(
        {
            this.onDecrease()
        }, DURATION
    )
}

fun EditText.updateText(translation: String, startPosition: Float, endPosition: Float, text: String ){
    //enter
    enterAnimation(translation, endPosition, startPosition) // first play enter animation. End and Start positions are inverted for proper animation
    //exit
    Handler(Looper.getMainLooper()).postDelayed(
        {
            this.setText(text) // update current text

            val animator2 = ObjectAnimator.ofFloat(this, translation, startPosition, endPosition)
            animator2.interpolator = EasingInterpolator(Ease.BACK_OUT)
            animator2.start()
        }, DURATION
    )
}