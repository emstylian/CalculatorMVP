package com.kotlin.calculatormvp.calculatorElements


enum class CalculatorExtras : CalculatorElement {
    LEFT_PARENTHESIS {
        override fun elementPriority(): Int = 4
        override fun getElementValue(): String = "("
    },
    RIGHT_PARENTHESIS {
        override fun elementPriority(): Int = -1
        override fun getElementValue(): String = ")"
    };
//    LOG {
//        override fun elementPriority(): Int = 3
//        override fun getElementValue(): String = "log"
//    },
//    COS {
//        override fun elementPriority(): Int = 3
//        override fun getElementValue(): String = "cos"
//    },
//    SIN {
//        override fun elementPriority(): Int = 3
//        override fun getElementValue(): String = "sin"
//    },
//    TAN {
//        override fun elementPriority(): Int = 3
//        override fun getElementValue(): String = "tan"
//    };

    override fun isOperator(): Boolean = true
}