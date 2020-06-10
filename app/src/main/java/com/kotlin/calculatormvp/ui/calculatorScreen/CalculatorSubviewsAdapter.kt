package com.kotlin.calculatormvp.ui.calculatorScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.kotlin.calculatormvp.R
import com.kotlin.calculatormvp.calculatorElements.CalculatorElement
import com.kotlin.calculatormvp.calculatorElements.CalculatorOperand
import kotlinx.android.synthetic.main.calculator_element_slot.view.*


class CalculatorSubviewsAdapter(
    private val calculatorElements: ArrayList<CalculatorElement>,
    private val activity: Activity
) : BaseAdapter() {
    @SuppressLint("ViewHolder")
    override fun getView(index: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = View.inflate(activity, R.layout.calculator_element_slot, null)
        setColors(view.calc_element_val_tv, index)
        view.calc_element_val_tv.text = calculatorElements.get(index).getElementValue()
        return view
    }

    private fun setColors(tv: TextView, position: Int) {
        if (!(calculatorElements.get(position) is CalculatorOperand) || (calculatorElements.get(position) == CalculatorOperand.DECIMAL_POINT))
            tv.calc_element_val_tv.setTextColor(
                ContextCompat.getColor(
                    activity,
                    R.color.custom_material_orange
                )
            )
    }

    override fun getItem(index: Int): CalculatorElement {
        return calculatorElements.get(index)
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return calculatorElements.size
    }

    fun getAdapterList(): ArrayList<CalculatorElement> {
        return calculatorElements
    }

}