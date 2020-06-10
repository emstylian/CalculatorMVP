package com.kotlin.calculatormvp.calculatorElements


enum class CalculatorOtherElement(val value: String) :
    CalculatorElement {
    CLEAR("C") {
        override var action: () -> Unit = { }
            get() = field
            set(value) {
                field = value
            }

        override fun elementPriority(): Int = -1
        override fun getElementValue(): String = "C"

    },
    BACKSPACE("<-") {
        override var action: () -> Unit = { }
            get() = field
            set(value) {
                field = value
            }

        override fun elementPriority(): Int = -1
        override fun getElementValue(): String = "<-"
    },
    EQUALS("=") {
        override var action: () -> Unit = { }
            get() = field
            set(value) {
                field = value
            }

        override fun elementPriority(): Int = -1
        override fun getElementValue(): String = "="
    },
    EURO("€") {
        override var action: () -> Unit = { }
            get() = field
            set(value) {
                field = value
            }

        override fun elementPriority(): Int = -1
        override fun getElementValue(): String = "€"
    };

    override fun isOperator(): Boolean = false
    abstract var action: () -> Unit
}

