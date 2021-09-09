package com.mcdev.quantitizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mcdev.quantitizerlibrary.AnimationStyle
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer
import com.mcdev.quantitizerlibrary.NoValueQuantitizer
import com.mcdev.quantitizerlibrary.VerticalQuantitizer

class ButtonsOnlyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buttons_only)

        val hq = findViewById<HorizontalQuantitizer>(R.id.h_quant)
        val vq = findViewById<VerticalQuantitizer>(R.id.v_qaunt)
        val nvq = findViewById<NoValueQuantitizer>(R.id.nv_quant)

        hq.apply {
            value = 1
            buttonAnimationEnabled = false
            textAnimationStyle = AnimationStyle.SLIDE_IN
            animationDuration = 400L
//            setPlusIconBackgroundColor("#C19A6B")
//            setMinusIconBackgroundColor("#C19A6B")
//            setMinusIconColor("#ffffff")
//            setPlusIconColor("#ffffff")
//            setValueBackgroundColor("#47716E")
//            setValueTextColor("#FFFFFF")
        }

        vq.apply {
            value = 1
            buttonAnimationEnabled = false
            textAnimationStyle = AnimationStyle.FALL_IN
//            setPlusIconBackgroundColor("#C19A6B")
//            setMinusIconBackgroundColor("#C19A6B")
//            setMinusIconColor("#ffffff")
//            setPlusIconColor("#ffffff")
//            setValueBackgroundColor("#47716E")
//            setValueTextColor("#FFFFFF")
        }

        nvq.apply {
            buttonAnimationEnabled = false
//            setPlusIconBackgroundColor("#C19A6B")
//            setMinusIconBackgroundColor("#C19A6B")
//            setMinusIconColor("#ffffff")
//            setPlusIconColor("#ffffff")
//            setValueBackgroundColor("#47716E")
//            setValueTextColor("#FFFFFF")
        }
    }
}