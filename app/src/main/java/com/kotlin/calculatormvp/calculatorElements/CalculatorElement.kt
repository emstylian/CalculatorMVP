package com.kotlin.calculatormvp.calculatorElements

interface CalculatorElement {
    fun elementPriority(): Int
    fun getElementValue(): String
    fun isOperator(): Boolean
}