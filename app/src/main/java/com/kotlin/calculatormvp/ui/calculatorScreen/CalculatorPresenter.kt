package com.kotlin.calculatormvp.ui.calculatorScreen

import com.kotlin.calculatormvp.calculatorElements.*
import java.math.RoundingMode
import java.text.DecimalFormat


class CalculatorPresenter : CalculatorContract.Presenter {
    private lateinit var view: CalculatorContract.View

    private var expressionList = arrayListOf<CalculatorElement>()

    private var historyList = arrayListOf<String>()

    private val simpleCalculatorElements = arrayListOf<CalculatorElement>(
        CalculatorOperand.NUMBER_7,
        CalculatorOperand.NUMBER_8,
        CalculatorOperand.NUMBER_9,
        CalculatorOperator.ADD,
        CalculatorOperator.SUB,
        CalculatorOperand.NUMBER_4,
        CalculatorOperand.NUMBER_5,
        CalculatorOperand.NUMBER_6,
        CalculatorOperator.MUL,
        CalculatorOperator.DIV,
        CalculatorOperand.NUMBER_1,
        CalculatorOperand.NUMBER_2,
        CalculatorOperand.NUMBER_3,
        CalculatorExtras.LEFT_PARENTHESIS,
        CalculatorExtras.RIGHT_PARENTHESIS,
        CalculatorOperand.DECIMAL_POINT,
        CalculatorOperand.NUMBER_0,
        CalculatorOtherElement.EQUALS,
        CalculatorOtherElement.BACKSPACE,
        CalculatorOtherElement.CLEAR
    )

    /* implementation of CalculatorContract.Presenter interface below */
    override fun createCalculator() {
        view.showCalculator(simpleCalculatorElements)
    }

    override fun addElementToExpressionList(element: CalculatorElement) {
        validateInput(element)
        view.drawExpression(generateOperandAsString(expressionList))
    }

    private fun validateInput(element: CalculatorElement) {
        // check if user gives two operators in a row
        if (element is CalculatorOperator || element is CalculatorOtherElement) {
            if (!expressionList.isEmpty() && expressionList.last() is CalculatorOperator) {

                expressionList.remove(expressionList.last()) // will be replaced in addElementToExpressionList
            }
        }

        //Check first input
        if (expressionList.size == 0) {
            if (element.getElementValue() == ".")
                return
            // if it's a negative sign add it as Negative Operand , not as Minus operator
            if (element.getElementValue() == "-") {
                expressionList.add(CalculatorOperand.NEGATIVE)
                return
                // if it's any other operator we shouldn't add it to the list at all
            } else if (element.isOperator() && element.getElementValue() != "(") {
                return
            }
        }

        expressionList.add(element)
    }

    override fun clearExpressionPressed() {
        if (!expressionList.isEmpty()) {
            expressionList.clear()
            view.drawExpression(generateOperandAsString(expressionList))
        }
    }

    override fun backspacePressed() {
        if (!expressionList.isEmpty()) {
            expressionList.remove(expressionList.last())
            view.drawExpression(generateOperandAsString(expressionList))
        }

    }

    override fun computePressed() {
        if (expressionList.isEmpty())
            return
        if (expressionList.last() is CalculatorOperator)
            expressionList.remove(expressionList.last())

        val expressionListAsString = generateOperandAsString(expressionList)

        val resultExtras = computeExtras(expressionList, getMaxPriority())
        val result = compute(resultExtras, getMaxPriority())
        var calculatorOutput: String

        val isResultValid = resultIsValid(result)

        if (isResultValid) {
            calculatorOutput = generateOperandAsString(result)
        } else {
            calculatorOutput = "Error"
            expressionList.clear()
        }

        view.drawExpression(calculatorOutput)
        if (isResultValid) {
            historyList.add(
                expressionListAsString + CalculatorOtherElement.EQUALS.value + calculatorOutput
            )
        } else {
            historyList.add(
                expressionListAsString + CalculatorOtherElement.EQUALS.value + "\n" + calculatorOutput
            )
        }
        view.drawHistory(historyList)
    }

    override fun attach(view: CalculatorContract.View) {
        this.view = view
    }


    /* end of implementation of interface */
    private fun getMaxPriority(): Int {
        val maxPriority = simpleCalculatorElements.map { it.elementPriority() }.max()
        if (maxPriority != null)
            return maxPriority
        else
            return 2
    }

    private fun computeExtras(
        expressionList: ArrayList<CalculatorElement>,
        priority: Int
    ): ArrayList<CalculatorElement> {
        val currentOperator = expressionList.findLast { it.elementPriority() == priority }
        if (currentOperator != null) {
            if (currentOperator is CalculatorExtras) {
                val evaluatedList = computeExpressionExtras(expressionList, currentOperator)
                computeExtras(evaluatedList, priority)

            }
        } else {
            var mutablePriority = priority
            mutablePriority--
            if (mutablePriority != 2) {
                computeExtras(expressionList, mutablePriority)
            }
        }
        return expressionList
    }

    private fun compute(
        expressionList: ArrayList<CalculatorElement>,
        priority: Int
    ): ArrayList<CalculatorElement> {
        val expressionListWithoutDoubleMinus = replaceSubToMinus(replaceDoubleMinus(expressionList))

        val currentOperator = expressionList.findLast { it.elementPriority() == priority }
        if (currentOperator != null) {

            if (currentOperator is CalculatorOperator) {
                if (checkIfHasCompute(expressionListWithoutDoubleMinus, currentOperator)) {
                    val evaluatedList =
                        computeExpression(expressionListWithoutDoubleMinus, currentOperator)
                    compute(evaluatedList, priority)
                }
            }
        } else {
            var mutablePriority = priority
            mutablePriority--
            if (mutablePriority != 0) {
                compute(expressionListWithoutDoubleMinus, mutablePriority)
            }
        }
        return expressionListWithoutDoubleMinus
    }

    private fun checkIfHasCompute(
        expressionList: ArrayList<CalculatorElement>,
        operator: CalculatorOperator
    ): Boolean {
        var cnt = 0
        expressionList.forEach {
            if (it.isOperator())
                cnt++
        }

        if ((cnt == 1) && expressionList.indexOf(operator) == 0)
            return false

        return true

    }

    private fun computeExpressionExtras(
        expressionList: ArrayList<CalculatorElement>,
        operator: CalculatorExtras
    ): ArrayList<CalculatorElement> {

        val operatorIndex = expressionList.indexOfLast { it == operator }

        if (operator == CalculatorExtras.LEFT_PARENTHESIS) {
            val expressionListFromParenthesis =
                expressionList.subList(operatorIndex + 1, expressionList.lastIndex + 1)
            val rightParenthesis =
                expressionListFromParenthesis.firstOrNull { it == CalculatorExtras.RIGHT_PARENTHESIS }
            if (rightParenthesis != null) {
                val indexOfRightParenthesis =
                    expressionListFromParenthesis.indexOfFirst { it == rightParenthesis }
                val expressionListWithoutParenthesis = expressionListFromParenthesis.subList(
                    0,
                    indexOfRightParenthesis
                )

                val innerResult =
                    compute(ArrayList(expressionListWithoutParenthesis), operator.elementPriority())

                expressionList.subList(operatorIndex, indexOfRightParenthesis + operatorIndex + 2)
                    .clear()
                expressionList.addAll(operatorIndex, innerResult)
                return expressionList
            }
        }

        return arrayListOf()
    }

    private fun computeExpression(
        expressionList: ArrayList<CalculatorElement>,
        operator: CalculatorOperator
    ): ArrayList<CalculatorElement> {

//        Log.d("ListIterations", "before expressionsList : " + expressionList)
        val operatorIndex = expressionList.indexOf(operator)
        val left = getLeftOperandRange(operatorIndex, expressionList)
        val right = getRightOperandRange(operatorIndex, expressionList)

        // create left array  in rangeStart..list.size - 1 step 1
        var leftOperandsList = arrayListOf<CalculatorElement>()
        for (index in left.first..left.last step 1)
            leftOperandsList.add(expressionList[index])


        // create right array
        var rightOperandsList = arrayListOf<CalculatorElement>()
        for (index in right.first..right.last step 1)
            rightOperandsList.add(expressionList[index])

        val res = evaluateExpression(leftOperandsList, rightOperandsList, operator)

        /* after evaluating an expression, remove this expression from the list! from left.start to right.last /
        / to index is exclusive, so we have to add 1 index position */
        expressionList.subList(left.start, right.last + 1).clear()
        expressionList.addAll(left.start, generateEnumList(res))

        return replaceDoubleMinus(expressionList)
    }


    private fun generateEnumList(number: Number): MutableList<CalculatorElement> {
        // convert num to string
        var numberToString: String
        if (number is Float) {
            val formatter = DecimalFormat("#.##")
            formatter.minimumFractionDigits = 0
            formatter.roundingMode = RoundingMode.HALF_EVEN
            numberToString = formatter.format(number)
        } else {
            numberToString = number.toString()
        }

        val numberToCharArray = numberToString.toCharArray()
        var calculatorElements = mutableListOf<CalculatorElement>()

        for (character in numberToCharArray) {
            val enumElement = CalculatorOperand.from(character.toString())
            calculatorElements.add(enumElement)
        }
        return calculatorElements
    }

    private fun replaceSubToMinus(expressionList: ArrayList<CalculatorElement>): ArrayList<CalculatorElement> {
        if (expressionList.first() == CalculatorOperator.SUB) {
            expressionList.set(0, CalculatorOperand.NEGATIVE)
        }
        return expressionList
    }

    private fun replaceDoubleMinus(expressionList: ArrayList<CalculatorElement>): ArrayList<CalculatorElement> {
        var doubleMinusIndex = -1
        expressionList.forEachIndexed { index, calculatorElement ->
            if (calculatorElement.getElementValue() == "-") {
                if (index < expressionList.size - 1) {
                    val nextElement = expressionList.get(index + 1)
                    if (nextElement.getElementValue() == "-") {
                        doubleMinusIndex = index
                    }
                }
            }
        }

        if (doubleMinusIndex >= 0) {
            expressionList.set(doubleMinusIndex, CalculatorOperator.ADD)
            expressionList.removeAt(doubleMinusIndex + 1)
        }
        return expressionList
    }

    private fun evaluateExpression(
        leftOperand: MutableList<CalculatorElement>,
        rightOperant: MutableList<CalculatorElement>,
        operator: CalculatorOperator
    ): Number {

        val leftNumber = generateOperandAsNumber(leftOperand)
        val rightNumber = generateOperandAsNumber(rightOperant)

        return operator.action(leftNumber, rightNumber)

    }

    private fun generateOperandAsNumber(operand: MutableList<CalculatorElement>): Number {
        var operandStr = generateOperandAsString(operand)
        if (operandStr.contains(CalculatorOperand.DECIMAL_POINT.value)) {
            return (operandStr).toFloat()
        } else {
            return operandStr.toInt()
        }

    }

    private fun generateOperandAsString(operand: MutableList<CalculatorElement>): String {

        var stringBuilder: StringBuilder = StringBuilder("")
        for (element in operand) {
            stringBuilder.append(element.getElementValue())
        }
        return stringBuilder.toString()

    }

    private fun getLeftOperandRange(
        startIndex: Int,
        list: ArrayList<CalculatorElement>
    ): IntRange {

        val rangeStart = startIndex - 1
        for (index in rangeStart downTo 0 step 1) {
            if (list[index].isOperator()) {
                return IntRange(index + 1, rangeStart)
            }
        }

        return IntRange(0, rangeStart)
    }


    private fun getRightOperandRange(
        startIndex: Int,
        list: ArrayList<CalculatorElement>
    ): IntRange {

        val rangeStart = startIndex + 1
        for (index in rangeStart..list.size - 1 step 1) {
            if (list[index].isOperator()) {
                return IntRange(rangeStart, index - 1)
            }
        }

        return IntRange(rangeStart, list.size - 1)
    }

    private fun resultIsValid(list: ArrayList<CalculatorElement>): Boolean {
        list.forEach {              // for negative results
            if (it.isOperator() && it.getElementValue() != "-")
                return false
        }
        return true
    }

}