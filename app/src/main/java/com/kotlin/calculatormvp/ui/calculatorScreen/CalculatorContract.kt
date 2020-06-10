package com.kotlin.calculatormvp.ui.calculatorScreen

import com.kotlin.calculatormvp.calculatorElements.CalculatorElement
import com.kotlin.calculatormvp.ui.base.BaseContract

class CalculatorContract {
    interface View : BaseContract.View {
        fun showCalculator(elements: ArrayList<CalculatorElement>)
        fun drawExpression(expressionStr: String)
        fun drawHistory(historyList : MutableList<String>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun createCalculator()
        fun addElementToExpressionList(element: CalculatorElement)
        fun clearExpressionPressed()
        fun backspacePressed()
        fun computePressed()
    }
}