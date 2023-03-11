package com.mcdev.quantitizer

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer
import com.mcdev.quantitizerlibrary.QuantitizerListener


@BindingAdapter(value = ["value"])
fun setPikerNumber(view: HorizontalQuantitizer, value: Int?) {
    if (view.value != value){
        value?.let { view.value = it }
    }
}

@InverseBindingAdapter(attribute = "value", event = "pikerNumberChangeEvent")
fun getPikerNumber(view: HorizontalQuantitizer): Int? {
    return view.value
}

@BindingAdapter("pikerNumberChangeEvent")
fun setPikerListener(view: HorizontalQuantitizer, attChange: InverseBindingListener) {
    view.setQuantitizerListener(object : QuantitizerListener {
        override fun onDecrease() {
            attChange.onChange()
        }

        override fun onValueChanged(value: Int) {
            attChange.onChange()
        }

        override fun onIncrease() {
            attChange.onChange()
        }
    })
}
