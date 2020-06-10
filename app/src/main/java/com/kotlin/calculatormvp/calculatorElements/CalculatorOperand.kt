package com.kotlin.calculatormvp.calculatorElements


enum class CalculatorOperand(val value: String) :
    CalculatorElement {
    NUMBER_0("0") {
        override fun elementPriority(): Int = 0
        override fun getElementValue(): String = "0"
    },
    NUMBER_1("1") {
        override fun elementPriority(): Int = 0
        override fun getElementValue(): String = "1"
    },
    NUMBER_2("2") {
        override fun elementPriority(): Int = 0
        override fun getElementValue(): String = "2"
    },
    NUMBER_3("3") {
        override fun elementPriority(): Int = 0
        override fun getElementValue(): String = "3"
    },
    NUMBER_4("4") {
        override fun elementPriority(): Int = 0
        override fun getElementValue(): String = "4"
    },
    NUMBER_5("5") {
        override fun elementPriority(): Int = 0
        override fun getElementValue(): String = "5"
    },
    NUMBER_6("6") {
        override fun elementPriority(): Int = 0
        override fun getElementValue(): String = "6"
    },
    NUMBER_7("7") {
        override fun elementPriority(): Int = 0
        override fun getElementValue(): String = "7"
    },
    NUMBER_8("8") {
        override fun elementPriority(): Int = 0
        override fun getElementValue(): String = "8"
    },
    NUMBER_9("9") {
        override fun elementPriority(): Int = 0
        override fun getElementValue(): String = "9"
    },
    DECIMAL_POINT(".") {
        override fun elementPriority(): Int = -1
        override fun getElementValue(): String = "."
    },
    NEGATIVE("_") {
        override fun elementPriority(): Int = -1
        override fun getElementValue(): String = "-"
    };

    override fun isOperator(): Boolean = false


    companion object {
        fun from(findValue: String): CalculatorOperand {
            when(findValue){
                "0" -> return NUMBER_0
                "1" -> return NUMBER_1
                "2" -> return NUMBER_2
                "3" -> return NUMBER_3
                "4" -> return NUMBER_4
                "5" -> return NUMBER_5
                "6" -> return NUMBER_6
                "7" -> return NUMBER_7
                "8" -> return NUMBER_8
                "9" -> return NUMBER_9
                "." -> return DECIMAL_POINT
                "-" -> return NEGATIVE
            }
            return NUMBER_0
        }
    }
}

