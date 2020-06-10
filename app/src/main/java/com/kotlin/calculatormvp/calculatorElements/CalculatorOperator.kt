package com.kotlin.calculatormvp.calculatorElements


enum class CalculatorOperator(val value: String) :
    CalculatorElement {

    ADD("+") {
        override fun elementPriority(): Int = 1
        override fun getElementValue(): String = "+"
        override fun isOperator(): Boolean = true
        override fun action(leftOperand: Number, rightOperand: Number): Number =
            leftOperand.toFloat() + rightOperand.toFloat()
    },
    SUB("-") {
        override fun elementPriority(): Int = 1
        override fun getElementValue(): String = "-"
        override fun isOperator(): Boolean = true
        override fun action(leftOperand: Number, rightOperand: Number): Number =
            leftOperand.toFloat() - rightOperand.toFloat()
    },
    MUL("*") {
        override fun elementPriority(): Int = 2
        override fun getElementValue(): String = "*"
        override fun isOperator(): Boolean = true
        override fun action(leftOperand: Number, rightOperand: Number): Number =
            leftOperand.toFloat() * rightOperand.toFloat()
    },
    DIV("/") {
        override fun elementPriority(): Int = 2
        override fun getElementValue(): String = "/"
        override fun isOperator(): Boolean = true
        override fun action(leftOperand: Number, rightOperand: Number): Number =
            leftOperand.toFloat() / rightOperand.toFloat()
    };

    abstract fun action(leftOperand: Number, rightOperand: Number): Number
}
