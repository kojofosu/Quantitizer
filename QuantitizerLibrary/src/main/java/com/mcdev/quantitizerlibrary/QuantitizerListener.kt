package com.mcdev.quantitizerlibrary

interface QuantitizerListener {
    fun onIncrease()
    fun onDecrease()
    fun onValueChanged(value: Int)
}