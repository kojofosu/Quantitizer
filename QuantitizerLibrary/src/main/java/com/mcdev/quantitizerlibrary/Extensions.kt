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

//var DURATION = 300L
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


fun View.enterAnimationSwing(translation: String, startPosition: Float, endPosition: Float, duration: Long) {
    val animator2 = ObjectAnimator.ofFloat(this, translation, startPosition, endPosition)
    animator2.interpolator = EasingInterpolator(Ease.BACK_IN)
    animator2.duration = duration
    animator2.start()
}

fun View.exitAnimationSwing(translation: String, startPosition: Float, endPosition: Float, duration: Long) {
    Handler(Looper.getMainLooper()).postDelayed(
        {
            val animator2 = ObjectAnimator.ofFloat(this, translation, startPosition, endPosition)
            animator2.interpolator = EasingInterpolator(Ease.BACK_OUT)
            animator2.start()
        }, duration
    )
}

fun View.enterAnimationSlide(translation: String, startPosition: Float, endPosition: Float, duration: Long) {
    val animator2 = ObjectAnimator.ofFloat(this, translation, startPosition, endPosition)
    animator2.interpolator = EasingInterpolator(Ease.BACK_IN)
    animator2.duration = duration
    animator2.start()
}

fun View.exitAnimationSlide(translation: String, startPosition: Float, endPosition: Float, duration: Long) {
    Handler(Looper.getMainLooper()).postDelayed(
        {
            val animator2 = ObjectAnimator.ofFloat(this, translation, startPosition, endPosition)
            animator2.interpolator = EasingInterpolator(Ease.BACK_OUT)
            animator2.start()
        }, duration
    )
}

fun QuantitizerListener.activateOnIncrease(duration: Long){
   Handler(Looper.getMainLooper()).postDelayed(
        {
            this.onIncrease()
        }, duration
    )
}

fun QuantitizerListener.activateOnDecrease(duration: Long){
    Handler(Looper.getMainLooper()).postDelayed(
        {
            this.onDecrease()
        }, duration
    )
}

fun EditText.textAnimSwing(translation: String, startPosition: Float, endPosition: Float, text: String , duration: Long){
    //enter
    enterAnimationSwing(translation, endPosition, startPosition, duration) // first play enter animation. End and Start positions are inverted for proper animation
    //exit
    Handler(Looper.getMainLooper()).postDelayed(
        {
            this.setText(text) // update current text

            val animator2 = ObjectAnimator.ofFloat(this, translation, startPosition, endPosition)
            animator2.interpolator = EasingInterpolator(Ease.BACK_OUT)
            animator2.start()
        }, duration
    )
}

fun EditText.textAnimSlideInLTR(translation: String, startPosition: Float, endPosition: Float, text: String, duration: Long){
    //enter
    enterAnimationSwing(translation, endPosition, startPosition, duration) // first play enter animation. End and Start positions are inverted for proper animation
    //exit
    Handler(Looper.getMainLooper()).postDelayed(
        {
            this.setText(text) // update current text

            val animator2 = ObjectAnimator.ofFloat(this, translation, -startPosition, endPosition)
            animator2.interpolator = EasingInterpolator(Ease.BACK_OUT)
            animator2.start()
        }, duration
    )
}

fun EditText.textAnimSlideInRTL(translation: String, startPosition: Float, endPosition: Float, text: String , duration: Long){
    //enter
    enterAnimationSwing(translation, endPosition, startPosition, duration) // first play enter animation. End and Start positions are inverted for proper animation
    //exit
    Handler(Looper.getMainLooper()).postDelayed(
        {
            this.setText(text) // update current text

            val animator2 = ObjectAnimator.ofFloat(this, translation, -startPosition, endPosition)
            animator2.interpolator = EasingInterpolator(Ease.BACK_OUT)
            animator2.start()
        }, duration
    )
}

fun EditText.textAnimFallIn(translation: String, startPosition: Float, endPosition: Float, text: String , duration: Long){
    //enter
    enterAnimationSwing(translation, endPosition, startPosition, duration) // first play enter animation. End and Start positions are inverted for proper animation
    //exit
    Handler(Looper.getMainLooper()).postDelayed(
        {
            this.setText(text) // update current text

            val animator2 = ObjectAnimator.ofFloat(this, translation, -startPosition, endPosition)
            animator2.interpolator = EasingInterpolator(Ease.BACK_OUT)
            animator2.start()
        }, duration
    )
}